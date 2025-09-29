package com.example.hotelmanagement.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public class TaskOperateRecordBO {
    private Long id;
    private Long taskId;
    private String operateType;
    private String operateContent;
    private Long operatorUserId;
    private String operatorUserDisplayName;
    private Date createTime;
}
