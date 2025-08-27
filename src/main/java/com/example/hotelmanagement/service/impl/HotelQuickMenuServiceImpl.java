package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.QuickMenu;
import com.example.hotelmanagement.dao.repository.QuickMenuRepository;
import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.service.HotelQuickMenuService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelQuickMenuServiceImpl implements HotelQuickMenuService {

    @Resource
    private QuickMenuRepository quickMenuRepository;

    @Override
    public ResponseEntity<?> create(QuickMenuCreateRequest request) {
        QuickMenu entity = new QuickMenu();
        entity.setIcon(request.getIcon());
        entity.setContent(request.getContent());
        QuickMenu saved = quickMenuRepository.save(entity);
        return ResponseEntity.ok(ApiResponse.success(saved.getId()));
    }

    @Override
    public ResponseEntity<?> update(QuickMenuUpdateRequest request) {
        if (request.getId() == null) {
            return ResponseEntity.ok(ApiResponse.error("缺少ID"));
        }
        Optional<QuickMenu> opt = quickMenuRepository.findById(request.getId());
        if (opt.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error("记录不存在"));
        }
        QuickMenu entity = opt.get();
        if (request.getIcon() != null) {
            entity.setIcon(request.getIcon());
        }
        if (request.getContent() != null) {
            entity.setContent(request.getContent());
        }
        quickMenuRepository.save(entity);
        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @Override
    public ResponseEntity<?> delete(QuickMenuDeleteRequest request) {
        if (request.getId() == null) {
            return ResponseEntity.ok(ApiResponse.error("缺少ID"));
        }
        boolean exists = quickMenuRepository.existsById(request.getId());
        if (!exists) {
            return ResponseEntity.ok(ApiResponse.error("记录不存在"));
        }
        quickMenuRepository.deleteById(request.getId());
        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @Override
    public ResponseEntity<?> list(QuickMenuListRequest request) {
        List<QuickMenu> all = quickMenuRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success(all));
    }
}


