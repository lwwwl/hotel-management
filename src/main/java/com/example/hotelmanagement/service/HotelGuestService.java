package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.request.HotelGuestCreateRequest;
import com.example.hotelmanagement.model.request.HotelGuestUpdateRequest;
import com.example.hotelmanagement.model.request.HotelGuestDeleteRequest;
import com.example.hotelmanagement.model.request.HotelGuestDetailRequest;
import org.springframework.http.ResponseEntity;

public interface HotelGuestService {
    ResponseEntity<?> createGuest(HotelGuestCreateRequest request);

    ResponseEntity<?> updateGuest(HotelGuestUpdateRequest request);

    ResponseEntity<?> deleteGuest(HotelGuestDeleteRequest request);

    ResponseEntity<?> getGuestById(HotelGuestDetailRequest request);
}
