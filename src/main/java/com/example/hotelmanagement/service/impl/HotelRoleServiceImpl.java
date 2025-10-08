package com.example.hotelmanagement.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.hotelmanagement.dao.entity.HotelDepartment;
import com.example.hotelmanagement.dao.entity.HotelMenu;
import com.example.hotelmanagement.dao.entity.HotelRole;
import com.example.hotelmanagement.dao.entity.HotelRoleMenu;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.entity.HotelUserDepartment;
import com.example.hotelmanagement.dao.entity.HotelUserRole;
import com.example.hotelmanagement.dao.repository.HotelDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelMenuRepository;
import com.example.hotelmanagement.dao.repository.HotelRoleMenuRepository;
import com.example.hotelmanagement.dao.repository.HotelRoleRepository;
import com.example.hotelmanagement.dao.repository.HotelUserDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRoleRepository;
import com.example.hotelmanagement.model.bo.MenuInfoBO;
import com.example.hotelmanagement.model.bo.RoleListItemBO;
import com.example.hotelmanagement.model.request.RoleCreateRequest;
import com.example.hotelmanagement.model.request.RoleDeleteRequest;
import com.example.hotelmanagement.model.request.RoleDetailRequest;
import com.example.hotelmanagement.model.request.RoleListRequest;
import com.example.hotelmanagement.model.request.RoleUpdateMenuRequest;
import com.example.hotelmanagement.model.request.RoleUpdateRequest;
import com.example.hotelmanagement.model.request.RoleUpdateUserRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.RoleDetailResponse;
import com.example.hotelmanagement.model.response.RoleListResponse;
import com.example.hotelmanagement.service.HotelRoleService;

import jakarta.annotation.Resource;

@Service
public class HotelRoleServiceImpl implements HotelRoleService {

    @Resource
    private HotelRoleRepository roleRepository;

    @Resource
    private HotelUserRoleRepository userRoleRepository;

    @Resource
    private HotelRoleMenuRepository roleMenuRepository;

    @Resource
    private HotelMenuRepository menuRepository;
    
    @Resource
    private HotelUserRepository userRepository;
    
    @Resource
    private HotelUserDepartmentRepository userDepartmentRepository;
    
    @Resource
    private HotelDepartmentRepository departmentRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createRole(RoleCreateRequest request) {
        try {
            if (!StringUtils.hasText(request.getName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "创建角色失败", "角色名称不能为空"));
            }
            if (roleRepository.findByName(request.getName()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "创建角色失败", "角色名称已存在"));
            }

            HotelRole role = new HotelRole();
            role.setName(request.getName());
            role.setDescription(request.getDescription());
            role.setMemberCount(request.getUserIdList() == null ? 0 : request.getUserIdList().size());
            role.setCreateTime(new Timestamp(System.currentTimeMillis()));
            role.setUpdateTime(new Timestamp(System.currentTimeMillis()));

            HotelRole saved = roleRepository.save(role);

            // 绑定用户
            if (request.getUserIdList() != null && !request.getUserIdList().isEmpty()) {
                List<HotelUserRole> relations = request.getUserIdList().stream()
                        .distinct()
                        .map(uid -> {
                            HotelUserRole ur = new HotelUserRole();
                            ur.setUserId(uid);
                            ur.setRoleId(saved.getId());
                            ur.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            ur.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            return ur;
                        }).toList();
                userRoleRepository.saveAll(relations);
            }

            // 绑定菜单
            if (request.getMenuIdList() != null && !request.getMenuIdList().isEmpty()) {
                List<HotelRoleMenu> roleMenus = request.getMenuIdList().stream()
                        .distinct()
                        .map(mid -> {
                            HotelRoleMenu rm = new HotelRoleMenu();
                            rm.setRoleId(saved.getId());
                            rm.setMenuId(mid);
                            rm.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            rm.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            return rm;
                        }).toList();
                roleMenuRepository.saveAll(roleMenus);
            }

            return ResponseEntity.ok(ApiResponse.success(saved.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "创建角色失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRole(RoleUpdateRequest request) {
        try {
            Optional<HotelRole> opt = roleRepository.findById(request.getRoleId());
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "更新角色失败", "角色不存在"));
            }
            HotelRole role = opt.get();

            if (StringUtils.hasText(request.getName()) && !request.getName().equals(role.getName())) {
                if (roleRepository.findByName(request.getName()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ApiResponse.error(400, "更新角色失败", "角色名称已存在"));
                }
                role.setName(request.getName());
            }
            if (request.getDescription() != null) {
                role.setDescription(request.getDescription());
            }

            role.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(role);
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "更新角色失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRoleUser(RoleUpdateUserRequest request) {
        try {
            Optional<HotelRole> opt = roleRepository.findById(request.getRoleId());
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "更新角色用户失败", "角色不存在"));
            }
            HotelRole role = opt.get();

            // 更新用户关联
            List<HotelUserRole> existing = userRoleRepository.findByRoleId(role.getId());
            userRoleRepository.deleteAll(existing);

            if (request.getUserIdList() != null && !request.getUserIdList().isEmpty()) {
                List<HotelUserRole> relations = request.getUserIdList().stream()
                        .distinct()
                        .map(uid -> {
                            HotelUserRole ur = new HotelUserRole();
                            ur.setUserId(uid);
                            ur.setRoleId(role.getId());
                            ur.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            ur.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            return ur;
                        }).toList();
                userRoleRepository.saveAll(relations);
                role.setMemberCount(relations.size());
            } else {
                role.setMemberCount(0);
            }

            role.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(role);
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "更新角色用户失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRoleMenu(RoleUpdateMenuRequest request) {
        try {
            Optional<HotelRole> opt = roleRepository.findById(request.getRoleId());
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "更新角色菜单失败", "角色不存在"));
            }
            HotelRole role = opt.get();

            // 更新菜单关联
            List<HotelRoleMenu> existMenus = roleMenuRepository.findByRoleId(role.getId());
            roleMenuRepository.deleteAll(existMenus);

            if (request.getMenuIdList() != null && !request.getMenuIdList().isEmpty()) {
                List<HotelRoleMenu> roleMenus = request.getMenuIdList().stream()
                        .distinct()
                        .map(mid -> {
                            HotelRoleMenu rm = new HotelRoleMenu();
                            rm.setRoleId(role.getId());
                            rm.setMenuId(mid);
                            rm.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            rm.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            return rm;
                        }).toList();
                roleMenuRepository.saveAll(roleMenus);
            }

            role.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(role);
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "更新角色菜单失败", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteRole(RoleDeleteRequest request) {
        try {
            Long roleId = request.getRoleId();
            if (roleId == null || !roleRepository.existsById(roleId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "删除角色失败", "角色不存在"));
            }

            // 删除关联
            List<HotelUserRole> userRoles = userRoleRepository.findByRoleId(roleId);
            if (!userRoles.isEmpty()) {
                userRoleRepository.deleteAll(userRoles);
            }
            List<HotelRoleMenu> roleMenus = roleMenuRepository.findByRoleId(roleId);
            if (!roleMenus.isEmpty()) {
                roleMenuRepository.deleteAll(roleMenus);
            }

            roleRepository.deleteById(roleId);
            return ResponseEntity.ok(ApiResponse.success(true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "删除角色失败", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> listRoles(RoleListRequest request) {
        try {
            List<HotelRole> roles;
            if (StringUtils.hasText(request.getKeyword())) {
                roles = roleRepository.searchByNameOrDescription(request.getKeyword());
            } else {
                roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
            }

            // 预取各角色的菜单与成员数量（成员数量直接取 memberCount，菜单查询关联）
            List<Long> roleIds = roles.stream().map(HotelRole::getId).toList();
            Map<Long, List<Long>> roleIdToMenuIds = new HashMap<>();
            if (!roleIds.isEmpty()) {
                List<HotelRoleMenu> allMenus = roleMenuRepository.findAll().stream()
                        .filter(rm -> roleIds.contains(rm.getRoleId()))
                        .toList();
                roleIdToMenuIds = allMenus.stream()
                        .collect(Collectors.groupingBy(HotelRoleMenu::getRoleId,
                                Collectors.mapping(HotelRoleMenu::getMenuId, Collectors.toList())));
            }

            // 获取所有菜单信息
            List<Long> allMenuIds = roleIdToMenuIds.values().stream()
                    .flatMap(List::stream)
                    .distinct()
                    .toList();
            Map<Long, HotelMenu> menuMap = new HashMap<>();
            if (!allMenuIds.isEmpty()) {
                List<HotelMenu> menus = menuRepository.findAllById(allMenuIds);
                menus.forEach(menu -> menuMap.put(menu.getId(), menu));
            }

            List<RoleListItemBO> roleItems = new ArrayList<>();
            for (HotelRole role : roles) {
                RoleListItemBO item = new RoleListItemBO();
                item.setRoleId(role.getId());
                item.setName(role.getName());
                item.setDescription(role.getDescription());
                item.setMemberCount(role.getMemberCount());
                
                // 构建菜单信息列表
                List<Long> menuIds = roleIdToMenuIds.getOrDefault(role.getId(), Collections.emptyList());
                List<MenuInfoBO> menuInfos = menuIds.stream()
                        .map(menuId -> {
                            MenuInfoBO menuInfo = new MenuInfoBO();
                            HotelMenu menu = menuMap.get(menuId);
                            if (menu != null) {
                                menuInfo.setMenuId(menu.getId());
                                menuInfo.setMenuName(menu.getName());
                            }
                            return menuInfo;
                        })
                        .toList();
                item.setMenus(menuInfos);
                roleItems.add(item);
            }

            RoleListResponse response = new RoleListResponse();
            response.setRoles(roleItems);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取角色列表失败", e.getMessage()));
        }
    }
    
    @Override
    public ResponseEntity<?> getRoleDetail(RoleDetailRequest request) {
        try {
            if (request.getRoleId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "获取角色详情失败", "角色ID不能为空"));
            }
            
            // 查找角色基本信息
            Optional<HotelRole> roleOpt = roleRepository.findById(request.getRoleId());
            if (!roleOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "获取角色详情失败", "角色不存在"));
            }
            
            HotelRole role = roleOpt.get();
            
            // 构建角色基本信息
            RoleDetailResponse.RoleBasicInfo roleInfo = new RoleDetailResponse.RoleBasicInfo();
            roleInfo.setRoleId(role.getId());
            roleInfo.setName(role.getName());
            roleInfo.setDescription(role.getDescription());
            roleInfo.setMemberCount(role.getMemberCount());
            roleInfo.setCreateTime(role.getCreateTime().getTime());
            roleInfo.setUpdateTime(role.getUpdateTime().getTime());
            
            // 获取角色关联的菜单信息
            List<HotelRoleMenu> roleMenus = roleMenuRepository.findAll().stream()
                    .filter(rm -> rm.getRoleId().equals(role.getId()))
                    .toList();
            
            List<Long> menuIds = roleMenus.stream()
                    .map(HotelRoleMenu::getMenuId)
                    .toList();
            
            List<RoleDetailResponse.MenuDetailInfo> menus = new ArrayList<>();
            if (!menuIds.isEmpty()) {
                List<HotelMenu> menuList = menuRepository.findAllById(menuIds);
                menus = menuList.stream()
                        .map(menu -> {
                            RoleDetailResponse.MenuDetailInfo menuInfo = new RoleDetailResponse.MenuDetailInfo();
                            menuInfo.setMenuId(menu.getId());
                            menuInfo.setMenuName(menu.getName());
                            menuInfo.setIcon(menu.getIcon());
                            menuInfo.setSortOrder(menu.getSortOrder());
                            return menuInfo;
                        })
                        .toList();
            }
            
            // 获取角色关联的用户信息（包含部门信息）
            List<HotelUserRole> userRoles = userRoleRepository.findAll().stream()
                    .filter(ur -> ur.getRoleId().equals(role.getId()))
                    .toList();
            
            List<Long> userIds = userRoles.stream()
                    .map(HotelUserRole::getUserId)
                    .toList();
            
            List<RoleDetailResponse.UserDetailInfo> users = new ArrayList<>();
            if (!userIds.isEmpty()) {
                List<HotelUser> userList = userRepository.findAllById(userIds);
                
                // 获取所有用户的部门关联信息
                List<HotelUserDepartment> userDepts = userDepartmentRepository.findAll().stream()
                        .filter(ud -> userIds.contains(ud.getUserId()))
                        .toList();
                
                // 获取所有部门信息
                List<Long> deptIds = userDepts.stream()
                        .map(HotelUserDepartment::getDeptId)
                        .distinct()
                        .toList();
                
                Map<Long, HotelDepartment> deptMap = new HashMap<>();
                if (!deptIds.isEmpty()) {
                    List<HotelDepartment> deptList = departmentRepository.findAllById(deptIds);
                    deptList.forEach(dept -> deptMap.put(dept.getId(), dept));
                }
                
                // 构建用户部门映射
                Map<Long, List<HotelUserDepartment>> userDeptMap = userDepts.stream()
                        .collect(Collectors.groupingBy(HotelUserDepartment::getUserId));
                
                users = userList.stream()
                        .map(user -> {
                            RoleDetailResponse.UserDetailInfo userInfo = new RoleDetailResponse.UserDetailInfo();
                            userInfo.setUserId(user.getId());
                            userInfo.setUsername(user.getUsername());
                            userInfo.setDisplayName(user.getDisplayName());
                            userInfo.setEmployeeNumber(user.getEmployeeNumber());
                            userInfo.setActive(user.getActive());
                            
                            // 构建部门信息
                            List<HotelUserDepartment> userDeptList = userDeptMap.getOrDefault(user.getId(), Collections.emptyList());
                            List<RoleDetailResponse.UserDetailInfo.DepartmentInfo> departments = userDeptList.stream()
                                    .map(ud -> {
                                        RoleDetailResponse.UserDetailInfo.DepartmentInfo deptInfo = 
                                                new RoleDetailResponse.UserDetailInfo.DepartmentInfo();
                                        HotelDepartment dept = deptMap.get(ud.getDeptId());
                                        if (dept != null) {
                                            deptInfo.setDeptId(dept.getId());
                                            deptInfo.setDeptName(dept.getName());
                                        }
                                        return deptInfo;
                                    })
                                    .filter(deptInfo -> deptInfo.getDeptId() != null)
                                    .toList();
                            userInfo.setDepartments(departments);
                            
                            return userInfo;
                        })
                        .toList();
            }
            
            // 构建响应对象
            RoleDetailResponse response = new RoleDetailResponse();
            response.setRoleInfo(roleInfo);
            response.setMenus(menus);
            response.setUsers(users);
            
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "获取角色详情失败", e.getMessage()));
        }
    }
}


