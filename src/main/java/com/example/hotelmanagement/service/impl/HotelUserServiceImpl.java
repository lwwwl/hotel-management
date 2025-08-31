package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.HotelDepartment;
import com.example.hotelmanagement.dao.entity.HotelRole;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.entity.HotelUserDepartment;
import com.example.hotelmanagement.dao.entity.HotelUserRole;
import com.example.hotelmanagement.dao.repository.HotelDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelRoleRepository;
import com.example.hotelmanagement.dao.repository.HotelUserDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRoleRepository;
import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.UserDepartmentInfo;
import com.example.hotelmanagement.model.response.UserDetailResponse;
import com.example.hotelmanagement.model.response.UserItem;
import com.example.hotelmanagement.model.response.UserListResponse;
import com.example.hotelmanagement.model.response.UserRoleInfo;
import com.example.hotelmanagement.service.HotelUserService;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelUserServiceImpl implements HotelUserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private HotelUserRepository userRepository;

    @Resource
    private HotelUserDepartmentRepository userDeptRepository;

    @Resource
    private HotelUserRoleRepository userRoleRepository;

    @Resource
    private HotelDepartmentRepository departmentRepository;

    @Resource
    private HotelRoleRepository roleRepository;

    @Override
    public ResponseEntity<?> searchUsers(UserSearchRequest request) {
        try {
            // 处理搜索参数
            String keyword = StringUtils.hasText(request.getKeyword()) ? request.getKeyword() : null;
            Long deptId = request.getDeptId();
            Short active = request.getActive();
            
            // 获取游标参数
            Timestamp lastCreateTime = request.getLastCreateTime() == null ? null : new Timestamp(request.getLastCreateTime());
            Long lastUserId = request.getLastUserId();
            int size = request.getSize() != null ? request.getSize() : 10;
            
            // 执行搜索
            List<HotelUser> users = userRepository.searchUsers(keyword, deptId, active, lastCreateTime, lastUserId, size + 1); // 多查询一条用于判断是否还有更多
            
            // 判断是否有更多数据
            boolean hasMore = users.size() > size;
            if (hasMore) {
                users = users.subList(0, size); // 如果有更多数据，只返回请求的数量
            }
            
            // 构建响应
            UserListResponse response = buildUserListResponse(users, size, hasMore);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "搜索用户失败", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getUserDetail(UserDetailRequest request) {
        try {
            Optional<HotelUser> userOptional = userRepository.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "用户不存在", "未找到指定用户"));
            }
            
            HotelUser user = userOptional.get();
            UserDetailResponse response = buildUserDetailResponse(user);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取用户详情失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> createUser(UserCreateRequest request) {
        try {
            // 检查用户名是否已存在
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "创建用户失败", "用户名已存在"));
            }
            
            // 检查工号是否已存在
            if (StringUtils.hasText(request.getEmployeeNumber()) && 
                    userRepository.findByEmployeeNumber(request.getEmployeeNumber()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "创建用户失败", "工号已存在"));
            }
            
            // 创建用户
            HotelUser user = new HotelUser();
            user.setUsername(request.getUsername());
            // 简单的密码编码 - 在生产环境中，使用适当的密码编码器
            user.setPassword(Base64.getEncoder().encodeToString(request.getPassword().getBytes()));
            user.setDisplayName(request.getDisplayName());
            user.setEmployeeNumber(request.getEmployeeNumber());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setSuperAdmin(request.getSuperAdmin() != null ? request.getSuperAdmin() : false);
            user.setActive((short) 1); // 默认激活
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            
            HotelUser savedUser = userRepository.save(user);
            
            // 关联单个部门
            if (request.getDeptId() != null) {
                // 先删除之前的部门关联（如果有）
                List<HotelUserDepartment> oldUserDepts = userDeptRepository.findByUserId(savedUser.getId());
                if (!oldUserDepts.isEmpty()) {
                    userDeptRepository.deleteAll(oldUserDepts);
                }
                
                // 创建新的部门关联
                HotelUserDepartment userDept = new HotelUserDepartment();
                userDept.setUserId(savedUser.getId());
                userDept.setDeptId(request.getDeptId());
                userDept.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userDept.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userDeptRepository.save(userDept);
            }
            
            // 关联角色
            if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
                List<HotelUserRole> userRoles = new ArrayList<>();
                for (Long roleId : request.getRoleIds()) {
                    HotelUserRole userRole = new HotelUserRole();
                    userRole.setUserId(savedUser.getId());
                    userRole.setRoleId(roleId);
                    userRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    userRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    userRoles.add(userRole);
                }
                userRoleRepository.saveAll(userRoles);
            }
            
            return ResponseEntity.ok(ApiResponse.success(savedUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "创建用户失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUser(UserUpdateRequest request) {
        try {
            Optional<HotelUser> userOptional = userRepository.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "用户不存在", "未找到指定用户"));
            }
            
            HotelUser user = userOptional.get();
            
            // 检查更新的用户名是否已被其他用户使用
            if (StringUtils.hasText(request.getUsername()) && !request.getUsername().equals(user.getUsername())) {
                Optional<HotelUser> existingUser = userRepository.findByUsername(request.getUsername());
                if (existingUser.isPresent() && !existingUser.get().getId().equals(request.getUserId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.error(400, "更新用户失败", "用户名已存在"));
                }
                user.setUsername(request.getUsername());
            }
            
            // 检查更新的工号是否已被其他用户使用
            if (StringUtils.hasText(request.getEmployeeNumber()) && 
                    !Objects.equals(request.getEmployeeNumber(), user.getEmployeeNumber())) {
                Optional<HotelUser> existingUser = userRepository.findByEmployeeNumber(request.getEmployeeNumber());
                if (existingUser.isPresent() && !existingUser.get().getId().equals(request.getUserId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.error(400, "更新用户失败", "工号已存在"));
                }
                user.setEmployeeNumber(request.getEmployeeNumber());
            }

            // 更新其他字段
            if (StringUtils.hasText(request.getDisplayName())) {
                user.setDisplayName(request.getDisplayName());
            }
            
            if (StringUtils.hasText(request.getEmail())) {
                user.setEmail(request.getEmail());
            }
            
            if (StringUtils.hasText(request.getPhone())) {
                user.setPhone(request.getPhone());
            }
            
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            
            // 更新部门关联
            if (request.getDeptId() != null) {
                // 删除现有的所有部门关联
                List<HotelUserDepartment> existingDepts = userDeptRepository.findByUserId(request.getUserId());
                userDeptRepository.deleteAll(existingDepts);
                
                // 创建新的部门关联
                HotelUserDepartment userDept = new HotelUserDepartment();
                userDept.setUserId(user.getId());
                userDept.setDeptId(request.getDeptId());
                userDept.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userDept.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userDeptRepository.save(userDept);
            }
            
            // 更新角色关联
            if (request.getRoleIds() != null) {
                // 删除现有关联
                List<HotelUserRole> existingRoles = userRoleRepository.findByUserId(request.getUserId());
                userRoleRepository.deleteAll(existingRoles);
                
                // 创建新关联
                List<HotelUserRole> userRoles = new ArrayList<>();
                for (Long roleId : request.getRoleIds()) {
                    HotelUserRole userRole = new HotelUserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(roleId);
                    userRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    userRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    userRoles.add(userRole);
                }
                userRoleRepository.saveAll(userRoles);
            }
            
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "更新用户失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteUser(UserDeleteRequest request) {
        try {
            if (!userRepository.existsById(request.getUserId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "用户不存在", "未找到指定用户"));
            }
            
            // 删除用户-部门关联
            List<HotelUserDepartment> userDepts = userDeptRepository.findByUserId(request.getUserId());
            userDeptRepository.deleteAll(userDepts);
            
            // 删除用户-角色关联
            List<HotelUserRole> userRoles = userRoleRepository.findByUserId(request.getUserId());
            userRoleRepository.deleteAll(userRoles);
            
            // 删除用户
            userRepository.deleteById(request.getUserId());
            
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "删除用户失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> toggleUserLock(UserLockRequest request) {
        try {
            Optional<HotelUser> userOptional = userRepository.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "用户不存在", "未找到指定用户"));
            }
            
            HotelUser user = userOptional.get();
            String result;
            if (user.getActive() == 1) {
                user.setActive((short) 0);
                result = "lock";
            } else {
                user.setActive((short) 1);
                result = "unlock";
            }
            userRepository.save(user);
            
            return ResponseEntity.ok(ApiResponse.success(result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "切换用户锁定状态失败", e.getMessage()));
        }
    }

    /**
     * 构建用户详情响应的辅助方法
     */
    private UserDetailResponse buildUserDetailResponse(HotelUser user) {
        UserDetailResponse response = new UserDetailResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setDisplayName(user.getDisplayName());
        response.setEmployeeNumber(user.getEmployeeNumber());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setSuperAdmin(user.getSuperAdmin());
        response.setActive(user.getActive());
        
        // 获取部门信息 - 只取第一个部门作为用户所属部门
        List<Long> deptIds = userDeptRepository.findDeptIdsByUserId(user.getId());
        if (!deptIds.isEmpty()) {
            Long deptId = deptIds.get(0); // 只取第一个部门
            Optional<HotelDepartment> deptOpt = departmentRepository.findById(deptId);
            if (deptOpt.isPresent()) {
                HotelDepartment dept = deptOpt.get();
                UserDepartmentInfo deptInfo = new UserDepartmentInfo();
                deptInfo.setDeptId(dept.getId());
                deptInfo.setDeptName(dept.getName());
                response.setDepartment(deptInfo);
            }
        }
        
        // 获取角色信息
        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(user.getId());
        List<HotelRole> roles = roleIds.isEmpty() ? 
                Collections.emptyList() : 
                roleRepository.findAllById(roleIds);
        
        List<UserRoleInfo> roleInfos = roles.stream()
                .map(role -> {
                    UserRoleInfo info = new UserRoleInfo();
                    info.setRoleId(role.getId());
                    info.setRoleName(role.getName());
                    return info;
                })
                .collect(Collectors.toList());
        response.setRoles(roleInfos);
        
        return response;
    }
    
    /**
     * 构建用户列表响应的辅助方法
     */
    private UserListResponse buildUserListResponse(List<HotelUser> users, Integer size, Boolean hasMore) {
        UserListResponse response = new UserListResponse();
        
        // 获取所有用户ID
        List<Long> userIds = users.stream()
                .map(HotelUser::getId)
                .toList();
        
        // 获取这些用户的部门关联，每个用户只会关联一个部门
        Map<Long, HotelUserDepartment> userDeptMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<HotelUserDepartment> allUserDepts = userDeptRepository.findAll().stream()
                    .filter(ud -> userIds.contains(ud.getUserId()))
                    .toList();
            
            for (HotelUserDepartment ud : allUserDepts) {
                userDeptMap.put(ud.getUserId(), ud);
            }
        }
        
        // 获取所有部门信息
        List<Long> deptIds = userDeptMap.values().stream()
                .map(HotelUserDepartment::getDeptId)
                .distinct()
                .filter(Objects::nonNull)
                .toList();
        
        Map<Long, HotelDepartment> deptMap = new HashMap<>();
        if (!deptIds.isEmpty()) {
            List<HotelDepartment> depts = departmentRepository.findAllById(deptIds);
            depts.forEach(dept -> deptMap.put(dept.getId(), dept));
        }
        
        // 构建用户项目
        List<UserItem> userItems = users.stream().map(user -> {
            UserItem item = new UserItem();
            item.setUserId(user.getId());
            item.setUsername(user.getUsername());
            item.setDisplayName(user.getDisplayName());
            item.setEmployeeNumber(user.getEmployeeNumber());
            item.setActive(user.getActive());
            
            // 添加部门信息
            HotelUserDepartment userDept = userDeptMap.get(user.getId());
            if (userDept != null) {
                HotelDepartment dept = deptMap.get(userDept.getDeptId());
                if (dept != null) {
                    UserDepartmentInfo deptInfo = new UserDepartmentInfo();
                    deptInfo.setDeptId(dept.getId());
                    deptInfo.setDeptName(dept.getName());
                    item.setDepartment(deptInfo);
                }
            }
            
            return item;
        }).toList();
        
        response.setUsers(userItems);
        response.setSize(size);
        
        // 设置游标信息，用于下一页查询
        if (!users.isEmpty()) {
            HotelUser lastUser = users.get(users.size() - 1);
            response.setLastCreateTime(lastUser.getCreateTime().getTime());
            response.setLastUserId(lastUser.getId());
            response.setHasMore(hasMore); // 如果返回的记录数等于或超过要求的大小，则可能还有更多数据
        } else {
            response.setHasMore(false);
        }
        
        return response;
    }

} 