# App端Controller架构说明

## 概述

为了区分 Web 端和 App 端的接口调用，我们实现了双 Controller 架构：
- **Service 层**：共用同一套业务逻辑
- **Controller 层**：Web 端和 App 端分别使用不同的 Controller

## 架构设计

### 1. 双 Controller 架构

```
┌─────────────────────────────────────────────────────────────┐
│                        请求来源                                │
├──────────────────────────┬──────────────────────────────────┤
│         Web 端            │           App 端                  │
│  (X-User-Id header)      │    (Authorization Bearer token)   │
└──────────┬───────────────┴──────────────┬───────────────────┘
           │                              │
           ▼                              ▼
    ┌──────────────┐              ┌──────────────────┐
    │ TaskController│              │ AppTaskController│
    │ @RequireUserId│              │ @RequireAppToken │
    └──────┬────────┘              └────────┬─────────┘
           │                                │
           │        ┌───────────────────────┘
           │        │
           ▼        ▼
     ┌─────────────────────┐
     │  HotelTaskService   │
     │   (业务逻辑层)        │
     └─────────────────────┘
```

### 2. 组件说明

#### Web 端
- **Controller**: `TaskController`, `NotificationController` 等
- **注解**: `@RequireUserId`
- **AOP**: `UserIdCheckAspect`
- **上下文**: `UserContext`
- **请求头**: `X-User-Id: {userId}`
- **路径**: `/task/*`, `/notification/*`

#### App 端
- **Controller**: `AppTaskController`, `AppNotificationController`
- **注解**: `@RequireAppToken`
- **AOP**: `AppTokenCheckAspect`
- **上下文**: `AppContext`
- **请求头**: `Authorization: Bearer {token}`
- **路径**: `/app/task/*`, `/app/notification/*`

## 实现细节

### 1. 注解定义

#### @RequireAppToken
```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAppToken {
}
```

### 2. AOP 切面

#### AppTokenCheckAspect
```java
@Aspect
@Component
public class AppTokenCheckAspect {
    
    @Around("@within(...RequireAppToken) || @annotation(...RequireAppToken)")
    public Object checkAppToken(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 从 Authorization header 获取 Bearer token
        // 2. 验证 token 是否存在于 auth_tokens 表
        // 3. 验证用户是否存在且未被禁用
        // 4. 将用户信息存入 AppContext
        // 5. 执行目标方法
        // 6. 清理 ThreadLocal
    }
}
```

**校验流程**：
1. 检查 `Authorization: Bearer {token}` header 是否存在
2. 从数据库查询 token 是否有效
3. 获取 token 关联的用户信息
4. 检查用户状态（是否被禁用）
5. 将用户信息存入 `AppContext` ThreadLocal
6. 执行目标方法
7. 清理 ThreadLocal（防止内存泄漏）

### 3. 上下文管理

#### AppContext
```java
public class AppContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    
    // getter/setter/clear 方法
}
```

**使用方式**：
```java
Long userId = AppContext.getUserId();
String username = AppContext.getUsername();
String token = AppContext.getToken();
```

### 4. Controller 实现

#### AppTaskController
```java
@RestController
@RequireAppToken  // 类级别注解，所有方法都需要 token 校验
@CrossOrigin
@RequestMapping("/app/task")
public class AppTaskController {
    
    @Resource
    private HotelTaskService taskService;
    
    @PostMapping("/list")
    public ResponseEntity<?> getTaskList(@RequestBody TaskListRequest request) {
        // 从 AppContext 获取 userId，调用 Service
        return taskService.getTaskList(AppContext.getUserId(), request);
    }
}
```

## App 端接口列表

### 工单接口 (`/app/task/*`)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/app/task/list` | POST | 获取工单列表 |
| `/app/task/detail` | POST | 获取工单详情 |
| `/app/task/claim` | POST | 认领工单 |
| `/app/task/change-status` | POST | 变更工单状态 |
| `/app/task/create` | POST | 创建工单 |
| `/app/task/update` | POST | 更新工单 |
| `/app/task/delete` | POST | 删除工单 |
| `/app/task/add-executor` | POST | 添加执行人 |
| `/app/task/transfer-executor` | POST | 转移执行人 |
| `/app/task/reminder` | POST | 发送提醒 |
| `/app/task/total-count` | POST | 获取工单总数 |
| `/app/task/sla` | POST | 获取工单SLA |

### 通知接口 (`/app/notification/*`)

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| `/app/notification/list` | POST | 获取通知列表 |

## Flutter App 端实现

### API 服务配置

```dart
class TaskApiService {
  static const baseUrl = 'https://kefu.5ok.co';
  
  /// 获取请求头（包含 Authorization token）
  static Future<Map<String, String>> get headers async {
    final authService = AuthService();
    final token = await authService.getToken();
    
    return {
      'Content-Type': 'application/json',
      if (token != null && token.isNotEmpty)
        'Authorization': 'Bearer $token',
    };
  }
  
  /// 获取工单列表
  static Future<ApiResponse<List<TaskListColumnBO>>> getTaskList(...) async {
    final requestHeaders = await headers;
    final response = await http.post(
      Uri.parse('$baseUrl/app/task/list'),
      headers: requestHeaders,
      body: jsonEncode(request.toJson()),
    );
    // ...
  }
}
```

## 错误处理

### Token 无效或过期
```json
{
  "timestamp": 1698765432000,
  "statusCode": 500,
  "message": "Token无效或已过期",
  "data": null,
  "error": null
}
```
HTTP Status: `401 Unauthorized`

### 用户不存在
```json
{
  "timestamp": 1698765432000,
  "statusCode": 500,
  "message": "用户不存在",
  "data": null,
  "error": null
}
```
HTTP Status: `401 Unauthorized`

### 用户已被禁用
```json
{
  "timestamp": 1698765432000,
  "statusCode": 500,
  "message": "该账号已被禁用",
  "data": null,
  "error": null
}
```
HTTP Status: `403 Forbidden`

### 未提供认证信息
```json
{
  "timestamp": 1698765432000,
  "statusCode": 500,
  "message": "未提供有效的认证信息",
  "data": null,
  "error": null
}
```
HTTP Status: `401 Unauthorized`

## 日志记录

每个 App 端接口调用都会记录以下信息：
```
App端获取工单列表 - userId: 1, username: admin
App端认领工单 - userId: 1, taskId: 123
App端变更工单状态 - userId: 1, taskId: 123, newStatus: in_progress
```

## 优势

1. **清晰的职责分离**
   - Web 端和 App 端使用不同的认证方式
   - 各自独立的 Controller，互不干扰

2. **代码复用**
   - Service 层完全共用
   - 业务逻辑只需维护一份

3. **安全性**
   - App 端使用 Bearer Token 认证
   - Web 端使用 X-User-Id header（通过 Nginx + Authelia）
   - Token 校验在 AOP 层统一处理

4. **可维护性**
   - 统一的 AOP 切面处理认证
   - ThreadLocal 自动清理，防止内存泄漏
   - 完整的日志记录

5. **易于扩展**
   - 新增接口只需在对应 Controller 添加方法
   - 自动获得 token 校验和日志记录能力

## 测试建议

### 1. Token 校验测试
- 不带 token 访问
- 带无效 token 访问
- 带有效 token 访问
- token 对应的用户被禁用

### 2. 功能测试
- 获取工单列表
- 获取工单详情
- 认领工单
- 变更工单状态
- 获取通知列表

### 3. 并发测试
- 同一用户多设备同时访问
- 多用户并发访问

## 注意事项

1. **ThreadLocal 清理**
   - AOP 切面在 finally 块中必须调用 `AppContext.clear()`
   - 防止内存泄漏和数据污染

2. **Token 安全**
   - Token 通过 HTTPS 传输
   - Token 存储在设备的安全存储中
   - Token 可通过登出接口撤销

3. **错误处理**
   - 统一返回 `ResponseResult` 格式
   - 使用合适的 HTTP 状态码
   - 记录详细的错误日志

4. **性能考虑**
   - 每次请求都会查询数据库验证 token
   - 可考虑添加 Redis 缓存优化性能
   - 索引已在 `auth_tokens` 表上创建

## 未来优化方向

1. **Token 缓存**
   - 使用 Redis 缓存 token 信息
   - 减少数据库查询

2. **Token 刷新**
   - 实现 token 自动刷新机制
   - 支持短期 access token + 长期 refresh token

3. **请求限流**
   - 针对 App 端接口添加限流策略
   - 防止恶意请求

4. **监控告警**
   - 添加接口调用监控
   - 异常 token 使用告警

---

**文档版本**: 1.0  
**最后更新**: 2025-11-01

