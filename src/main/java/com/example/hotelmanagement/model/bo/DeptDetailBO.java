package com.example.hotelmanagement.model.bo;

import java.util.List;
import com.example.hotelmanagement.model.response.DeptUserInfo;
import lombok.Data;

@Data
public class DeptDetailBO {
    private Long deptId;
    private String deptName;
    private Long leaderId;
    private String leaderName;
    private List<DeptUserInfo> memberList;
} 