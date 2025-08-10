package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatwootGuestCreateMessage implements Serializable {
    private String content;
}
