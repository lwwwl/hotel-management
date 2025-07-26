package com.example.hotelmanagement.model.response;

import java.util.List;
import com.example.hotelmanagement.model.bo.DeptListItemBO;

public class DeptListResponse {
    private List<DeptListItemBO> deptList;

    public List<DeptListItemBO> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptListItemBO> deptList) {
        this.deptList = deptList;
    }
} 