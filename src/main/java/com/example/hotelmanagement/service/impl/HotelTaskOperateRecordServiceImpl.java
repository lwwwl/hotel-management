package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.HotelTaskOperateRecord;
import com.example.hotelmanagement.dao.entity.HotelUser;
import com.example.hotelmanagement.dao.repository.HotelTaskOperateRecordRepository;
import com.example.hotelmanagement.dao.repository.HotelUserRepository;
import com.example.hotelmanagement.enums.TaskOperateTypeEnum;
import com.example.hotelmanagement.model.bo.TaskOperateRecordBO;
import com.example.hotelmanagement.model.request.TaskOperateRecordRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.HotelTaskOperateRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HotelTaskOperateRecordServiceImpl implements HotelTaskOperateRecordService {

    @Autowired
    HotelTaskOperateRecordRepository hotelTaskOperateRecordRepository;

    @Autowired
    HotelUserRepository hotelUserRepository;


    @Override
    public ResponseEntity<?> queryByTaskId(TaskOperateRecordRequest request) {
        List<HotelTaskOperateRecord> records = hotelTaskOperateRecordRepository
                .findAllByTaskIdOrderByCreateTimeDesc(request.getTaskId());
        if (CollectionUtils.isEmpty(records)) {
            return ResponseEntity.ok(ApiResponse.success(Collections.emptyList()));
        }

        List<Long> userIds = records.stream()
                .map(HotelTaskOperateRecord::getOperatorUserId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, HotelUser> userMap = hotelUserRepository.findAllByIdIn(userIds)
                .stream()
                .collect(Collectors.toMap(HotelUser::getId, Function.identity()));

        List<TaskOperateRecordBO> boList = records.stream().map(record -> {
            TaskOperateRecordBO bo = new TaskOperateRecordBO();
            BeanUtils.copyProperties(record, bo);
            bo.setOperateContent(generateOperateContent(record.getOperateType()));
            if (Objects.nonNull(record.getOperatorUserId())) {
                HotelUser user = userMap.get(record.getOperatorUserId());
                if (Objects.nonNull(user)) {
                    bo.setOperatorUserDisplayName(user.getDisplayName());
                }
            }
            return bo;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(boList));
    }

    private String generateOperateContent(String operateType) {
        TaskOperateTypeEnum typeEnum = TaskOperateTypeEnum.getByCode(operateType);
        if (typeEnum != null) {
            return typeEnum.getDisplayName();
        }
        // Fallback for unknown types
        return operateType;
    }
}
