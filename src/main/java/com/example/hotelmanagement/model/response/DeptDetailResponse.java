package com.example.hotelmanagement.model.response;

import java.util.List;
import com.example.hotelmanagement.model.response.DeptUserInfo;

public class DeptDetailResponse {
    private Long deptId;
    private String deptName;
    private Long leaderId;
    private String leaderName;
    private List<DeptUserInfo> memberList;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public List<DeptUserInfo> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<DeptUserInfo> memberList) {
        this.memberList = memberList;
    }
} 