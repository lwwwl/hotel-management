package com.example.hotelmanagement.model.response;

import java.util.List;
import com.example.hotelmanagement.model.response.DeptInfo;

public class DeptSelectListResponse {
    private List<DeptInfo> deptList;

    public List<DeptInfo> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptInfo> deptList) {
        this.deptList = deptList;
    }
} 