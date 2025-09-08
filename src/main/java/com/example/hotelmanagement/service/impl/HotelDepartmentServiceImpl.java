package com.example.hotelmanagement.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.HotelDepartment;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.entity.HotelUserDepartment;
import com.example.hotelmanagement.dao.repository.HotelDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelUserDepartmentRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.model.bo.DeptListItemBO;
import com.example.hotelmanagement.model.request.DeptAddUserRequest;
import com.example.hotelmanagement.model.request.DeptCreateRequest;
import com.example.hotelmanagement.model.request.DeptDeleteRequest;
import com.example.hotelmanagement.model.request.DeptDetailRequest;
import com.example.hotelmanagement.model.request.DeptListRequest;
import com.example.hotelmanagement.model.request.DeptRemoveUserRequest;
import com.example.hotelmanagement.model.request.DeptSelectListRequest;
import com.example.hotelmanagement.model.request.DeptUpdateRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.DeptDetailResponse;
import com.example.hotelmanagement.model.response.DeptInfo;
import com.example.hotelmanagement.model.response.DeptListResponse;
import com.example.hotelmanagement.model.response.DeptSelectListResponse;
import com.example.hotelmanagement.model.response.DeptUserInfo;
import com.example.hotelmanagement.service.HotelDepartmentService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class HotelDepartmentServiceImpl implements HotelDepartmentService {
    @Resource
    private HotelDepartmentRepository departmentRepository;
    @Resource
    private HotelUserDepartmentRepository userDepartmentRepository;
    @Resource
    private HotelUserRepository userRepository;

    private static final long ALL_DEPARTMENT_ID = -1L;

    @Override
    public ResponseEntity<?> getDeptSelectList(DeptSelectListRequest request) {
        List<HotelDepartment> departments = departmentRepository.findAll();
        List<DeptInfo> deptList = departments.stream().map(d -> {
            DeptInfo info = new DeptInfo();
            info.setDeptId(d.getId());
            info.setDeptName(d.getName());
            return info;
        }).collect(Collectors.toList());
        DeptSelectListResponse response = new DeptSelectListResponse();

        DeptInfo allDept = new DeptInfo();
        allDept.setDeptId(ALL_DEPARTMENT_ID);
        allDept.setDeptName("全部部门");
        deptList.add(0, allDept);
        response.setDeptList(deptList);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Override
    public ResponseEntity<?> getDeptList(DeptListRequest request) {
        List<HotelDepartment> departments = departmentRepository.findAll();
        List<Long> leaderIds = departments.stream().map(HotelDepartment::getLeaderUserId).distinct().collect(Collectors.toList());
        // 批量查领导
        List<HotelUser> leaders = userRepository.findAllById(leaderIds);
        Map<Long, HotelUser> leaderMap = leaders.stream().collect(Collectors.toMap(HotelUser::getId, u -> u));
        List<DeptListItemBO> deptList = new ArrayList<>();
        for (HotelDepartment d : departments) {
            DeptListItemBO item = new DeptListItemBO();
            item.setDeptId(d.getId());
            item.setDeptName(d.getName());
            item.setLeaderId(d.getLeaderUserId());
            HotelUser leader = leaderMap.get(d.getLeaderUserId());
            item.setLeaderName(leader != null && leader.getDisplayName() != null ? leader.getDisplayName() : "");
            item.setMemberCount(d.getMemberCount());
            deptList.add(item);
        }
        DeptListResponse response = new DeptListResponse();
        response.setDeptList(deptList);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Override
    public ResponseEntity<?> getDeptDetail(DeptDetailRequest request) {
        Optional<HotelDepartment> deptOpt = departmentRepository.findById(request.getDeptId());
        if (!deptOpt.isPresent()) {
            return ResponseEntity.badRequest().body("部门不存在");
        }
        HotelDepartment dept = deptOpt.get();
        DeptDetailResponse response = new DeptDetailResponse();
        response.setDeptId(dept.getId());
        response.setDeptName(dept.getName());
        response.setLeaderId(dept.getLeaderUserId());
        Optional<HotelUser> leaderOpt = userRepository.findById(dept.getLeaderUserId());
        response.setLeaderName(leaderOpt.map(u -> u.getDisplayName() == null ? "" : u.getDisplayName()).orElse(""));
        List<HotelUserDepartment> userDepts = userDepartmentRepository.findByDeptId(dept.getId());
        List<Long> userIds = userDepts.stream().map(HotelUserDepartment::getUserId).collect(Collectors.toList());
        List<HotelUser> users = userIds.isEmpty() ? new ArrayList<>() : userRepository.findAllById(userIds);
        Map<Long, HotelUser> userMap = users.stream().collect(Collectors.toMap(HotelUser::getId, u -> u));
        List<DeptUserInfo> memberList = new ArrayList<>();
        for (Long userId : userIds) {
            HotelUser user = userMap.get(userId);
            if (user != null) {
                DeptUserInfo info = new DeptUserInfo();
                info.setUserId(user.getId());
                info.setName(user.getDisplayName());
                info.setUsername(user.getUsername());
                memberList.add(info);
            }
        }
        response.setMemberList(memberList);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Override
    public ResponseEntity<?> createDept(DeptCreateRequest request) {
        HotelDepartment dept = new HotelDepartment();
        dept.setName(request.getDeptName());
        dept.setLeaderUserId(request.getLeaderId());
        dept.setMemberCount(0);
        departmentRepository.save(dept);
        // leader添加到部门
        HotelUserDepartment ud = new HotelUserDepartment();
        ud.setDeptId(dept.getId());
        ud.setUserId(request.getLeaderId());
        userDepartmentRepository.save(ud);
        // 部门人数更新
        dept.setMemberCount(dept.getMemberCount() + 1);
        departmentRepository.save(dept);
        return ResponseEntity.ok(ApiResponse.success("创建成功"));
    }

    @Override
    public ResponseEntity<?> updateDept(DeptUpdateRequest request) {
        Optional<HotelDepartment> deptOpt = departmentRepository.findById(request.getDeptId());
        if (!deptOpt.isPresent()) {
            return ResponseEntity.badRequest().body("部门不存在");
        }
        HotelDepartment dept = deptOpt.get();
        if (StringUtils.hasText(request.getDeptName())) {
            dept.setName(request.getDeptName());
        }
        if (request.getLeaderId() != null && !Objects.equals(request.getLeaderId(), dept.getLeaderUserId())) {
            addDeptUser(List.of(request.getLeaderId()), dept);
            dept.setLeaderUserId(request.getLeaderId());
        }
        departmentRepository.save(dept);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteDept(DeptDeleteRequest request) {
        departmentRepository.deleteById(request.getDeptId());
        userDepartmentRepository.deleteByDeptId(request.getDeptId());
        return ResponseEntity.ok(ApiResponse.success("删除成功"));
    }

    @Override
    public ResponseEntity<?> addDeptUser(DeptAddUserRequest request) {
        Long deptId = request.getDeptId();
        Optional<HotelDepartment> deptOpt = departmentRepository.findById(deptId);
        if (!deptOpt.isPresent()) {
            return ResponseEntity.badRequest().body("部门不存在");
        }
        HotelDepartment dept = deptOpt.get();
        List<Long> userIds = request.getUserIdList();
        addDeptUser(userIds, dept);
        return ResponseEntity.ok(ApiResponse.success("添加成功"));
    }

    private void addDeptUser(List<Long> userIds, HotelDepartment dept) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        for (Long userId : userIds) {
            // 检查是否已存在
            List<HotelUserDepartment> exists = userDepartmentRepository.findByDeptId(dept.getId()).stream()
                    .filter(ud -> ud.getUserId().equals(userId)).toList();
            if (exists.isEmpty()) {
                HotelUserDepartment ud = new HotelUserDepartment();
                ud.setDeptId(dept.getId());
                ud.setUserId(userId);
                ud.setCreateTime(new Timestamp(System.currentTimeMillis()));
                ud.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userDepartmentRepository.save(ud);
                // 部门人数更新
                dept.setMemberCount(dept.getMemberCount() + 1);
                departmentRepository.save(dept);
            }
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeDeptUser(DeptRemoveUserRequest request) {
        // 获取部门所有成员，过滤出存在的待移除成员
        List<HotelUserDepartment> allUserDepts = userDepartmentRepository.findByDeptId(request.getDeptId());
        List<Long> userIds = allUserDepts.stream().map(HotelUserDepartment::getUserId).toList();
        List<Long> removeUserIds = request.getUserIdList().stream().filter(userId -> userIds.contains(userId)).collect(Collectors.toList());
        if (removeUserIds.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error(400, "没有待移除成员", ""));
        }
        // 移除成员
        userDepartmentRepository.deleteByDeptIdAndUserIdIn(request.getDeptId(), removeUserIds);
        // 部门人数更新
        HotelDepartment dept = departmentRepository.findById(request.getDeptId()).get();
        dept.setMemberCount(dept.getMemberCount() - removeUserIds.size());
        departmentRepository.save(dept);
        return ResponseEntity.ok(ApiResponse.success("删除成功"));
    }
} 