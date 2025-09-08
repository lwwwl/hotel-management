package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.QuickMenuCreateRequest;
import com.example.hotelmanagement.model.request.QuickMenuDeleteRequest;
import com.example.hotelmanagement.model.request.QuickMenuListRequest;
import com.example.hotelmanagement.model.request.QuickMenuSaveOrderRequest;
import com.example.hotelmanagement.model.request.QuickMenuUpdateRequest;

public interface HotelQuickMenuService {

    /** 创建快捷菜单 */
    ResponseEntity<?> create(QuickMenuCreateRequest request);

    /** 更新快捷菜单 */
    ResponseEntity<?> update(QuickMenuUpdateRequest request);

    /** 删除快捷菜单 */
    ResponseEntity<?> delete(QuickMenuDeleteRequest request);

    /** 列出所有快捷菜单 */
    ResponseEntity<?> list(QuickMenuListRequest request);

    /** 保存排序 */
    ResponseEntity<?> saveOrder(QuickMenuSaveOrderRequest request);
}


