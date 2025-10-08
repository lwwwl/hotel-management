package com.example.hotelmanagement.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.HotelDepartment;
import com.example.hotelmanagement.dao.entity.HotelMenu;
import com.example.hotelmanagement.dao.entity.HotelRole;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.repository.HotelDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelMenuRepository;
import com.example.hotelmanagement.dao.repository.HotelRoleMenuRepository;
import com.example.hotelmanagement.dao.repository.HotelRoleRepository;
import com.example.hotelmanagement.dao.repository.HotelUserDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRoleRepository;
import com.example.hotelmanagement.model.bo.DeptInfoBO;
import com.example.hotelmanagement.model.bo.RoleInfoBO;
import com.example.hotelmanagement.model.bo.RoutersInfoBO;
import com.example.hotelmanagement.model.bo.UserInfoBO;
import com.example.hotelmanagement.model.bo.UserWebInfoBO;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.WebService;

import jakarta.annotation.Resource;

@Service
public class WebServiceImpl implements WebService {

    @Resource
    private HotelUserRepository userRepository;
    @Resource
    private HotelUserDepartmentRepository userDeptRepository;
    @Resource
    private HotelDepartmentRepository departmentRepository;
    @Resource
    private HotelUserRoleRepository userRoleRepository;
    @Resource
    private HotelRoleMenuRepository roleMenuRepository;
    @Resource
    private HotelMenuRepository menuRepository;
    @Resource
    private HotelRoleRepository roleRepository;

    @Override
    public ResponseEntity<?> getUserInfo(Long userId) {
        UserWebInfoBO result = new UserWebInfoBO();

        if (userId == null) {
            return ResponseEntity.ok(ApiResponse.success(result));
        }

        Optional<HotelUser> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(result));
        }

        HotelUser user = userOpt.get();

        UserInfoBO userInfo = new UserInfoBO();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setDisplayName(user.getDisplayName());
        userInfo.setEmployeeNumber(user.getEmployeeNumber());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());

        List<Long> deptIds = userDeptRepository.findDeptIdsByUserId(user.getId());
        if (!deptIds.isEmpty()) {
            Long deptId = deptIds.get(0);
            Optional<HotelDepartment> deptOpt = departmentRepository.findById(deptId);
            if (deptOpt.isPresent()) {
                HotelDepartment dept = deptOpt.get();
                DeptInfoBO deptInfo = new DeptInfoBO();
                deptInfo.setDeptId(dept.getId());
                deptInfo.setDeptName(dept.getName());
                userInfo.setDept(deptInfo);
            }
        }

        // 角色ID列表
        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(user.getId());
        if (roleIds != null && !roleIds.isEmpty()) {
            List<HotelRole> roles = roleRepository.findAllById(roleIds);
            List<RoleInfoBO> roleInfos = roles.stream().map(role -> {
                RoleInfoBO roleInfo = new RoleInfoBO();
                roleInfo.setId(role.getId());
                roleInfo.setName(role.getName());
                roleInfo.setDescription(role.getDescription());
                return roleInfo;
            }).collect(Collectors.toList());
            result.setRoles(roleInfos);
        } else {
            result.setRoles(Collections.emptyList());
        }

        // 权限列表
        Set<String> permissionSet = new HashSet<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            List<Long> menuIds = roleMenuRepository.findMenuIdsByRoleIds(roleIds);
            if (menuIds != null && !menuIds.isEmpty()) {
                List<HotelMenu> menus = menuRepository.findAllById(menuIds);
                for (HotelMenu menu : menus) {
                    if (menu.getPerms() != null && !menu.getPerms().isEmpty()) {
                        permissionSet.add(menu.getPerms());
                    }
                }
            }
        }

        result.setPermissions(new ArrayList<>(permissionSet));
        result.setUser(userInfo);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Override
    public ResponseEntity<?> getRouters() {
        // 顶级菜单（parentId=0 且可见）
        List<HotelMenu> parents = menuRepository.findAllActiveTopLevelMenus();
        List<RoutersInfoBO> result = parents.stream()
                .map(this::convertMenuToRouter)
                .collect(Collectors.toList());

        // 一次性查询所有子菜单，避免循环查库
        List<Long> parentIds = parents.stream().map(HotelMenu::getId).collect(Collectors.toList());
        List<HotelMenu> allChildren = parentIds.isEmpty() ? Collections.emptyList() :
                menuRepository.findByParentIdInOrderBySortOrderAsc(parentIds);
        // 以 parentId 进行分组
        Map<Long, List<RoutersInfoBO>> groupedChildren = allChildren.stream()
                .collect(Collectors.groupingBy(HotelMenu::getParentId,
                        Collectors.mapping(this::convertMenuToRouter, Collectors.toList())));
        // 组装
        for (int i = 0; i < parents.size(); i++) {
            HotelMenu parent = parents.get(i);
            RoutersInfoBO parentRouter = result.get(i);
            parentRouter.setChildren(groupedChildren.getOrDefault(parent.getId(), new ArrayList<>()));
        }

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    private RoutersInfoBO convertMenuToRouter(HotelMenu menu) {
        RoutersInfoBO router = new RoutersInfoBO();
        router.setPath(menu.getPath());
        router.setName(menu.getName());
        router.setComponent(menu.getComponent());
        router.setPerms(menu.getPerms());
        router.setIcon(menu.getIcon());
        router.setSortOrder(menu.getSortOrder());
        router.setActive(menu.getActive());
        router.setChildren(new ArrayList<>());
        return router;
    }
}
