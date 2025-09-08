package com.example.hotelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.QuickMenuCreateRequest;
import com.example.hotelmanagement.model.request.QuickMenuDeleteRequest;
import com.example.hotelmanagement.model.request.QuickMenuListRequest;
import com.example.hotelmanagement.model.request.QuickMenuSaveOrderRequest;
import com.example.hotelmanagement.model.request.QuickMenuUpdateRequest;
import com.example.hotelmanagement.service.HotelQuickMenuService;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/quickMenu")
public class QuickMenuController {

    @Autowired
    private HotelQuickMenuService quickMenuService;

    /** 创建快捷菜单 */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuickMenuCreateRequest request) {
        return quickMenuService.create(request);
    }

    /** 更新快捷菜单 */
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody QuickMenuUpdateRequest request) {
        return quickMenuService.update(request);
    }

    /** 删除快捷菜单 */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody QuickMenuDeleteRequest request) {
        return quickMenuService.delete(request);
    }

    /** 列表，返回全量快捷菜单 */
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody QuickMenuListRequest request) {
        return quickMenuService.list(request);
    }

    /** 保存排序 */
    @PostMapping("/save-order")
    public ResponseEntity<?> saveOrder(@RequestBody QuickMenuSaveOrderRequest request) {
        return quickMenuService.saveOrder(request);
    }
}


