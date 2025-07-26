package com.example.hotelmanagement.model.request;

import java.util.List;

public class DeptRemoveUserRequest {
    private Long deptId;
    private List<Long> userIdList;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }
} 