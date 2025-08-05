package com.example.hotelmanagement.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatwootAdditionalAttributes implements Serializable {
    private Long roomId;
    private String roomName;
    private Long checkInTime;
    private Long leaveTime;
}
