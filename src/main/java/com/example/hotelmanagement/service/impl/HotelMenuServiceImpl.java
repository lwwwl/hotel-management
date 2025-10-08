package com.example.hotelmanagement.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dao.entity.HotelMenu;
import com.example.hotelmanagement.dao.repository.HotelMenuRepository;
import com.example.hotelmanagement.model.request.MenuListRequest;
import com.example.hotelmanagement.model.response.ApiResponse;
import com.example.hotelmanagement.model.response.MenuListResponse;
import com.example.hotelmanagement.model.response.MenuListResponse.MenuItem;
import com.example.hotelmanagement.service.HotelMenuService;

import jakarta.annotation.Resource;

@Service
public class HotelMenuServiceImpl implements HotelMenuService {

    @Resource
    private HotelMenuRepository menuRepository;

    @Override
    public ResponseEntity<?> listMenus(MenuListRequest request) {
        List<HotelMenu> all = menuRepository.findAll();
        // 按 sortOrder 升序，null 放后面
        List<MenuItem> items = all.stream()
                .sorted(Comparator.comparing(HotelMenu::getSortOrder, Comparator.nullsLast(Integer::compareTo)))
                .map(menu -> {
                    MenuItem item = new MenuItem();
                    item.setMenuId(menu.getId());
                    item.setMenuName(menu.getName());
                    item.setIcon(menu.getIcon());
                    item.setSortOrder(menu.getSortOrder() == null ? 0 : menu.getSortOrder());
                    item.setParentId(menu.getParentId());
                    item.setPath(menu.getPath());
                    item.setType(menu.getType() == null ? 0 : menu.getType().intValue());
                    item.setVisible(Boolean.TRUE.equals(menu.getActive()));
                    return item;
                })
                .collect(Collectors.toList());

        MenuListResponse response = new MenuListResponse();
        response.setMenus(items);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}


