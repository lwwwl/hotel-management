package com.example.hotelmanagement.model.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeptInfoBO implements Serializable {
    private Long deptId;
    private String deptName;

    // 显式提供setter方法，避免Lombok未生效导致的编译问题
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
