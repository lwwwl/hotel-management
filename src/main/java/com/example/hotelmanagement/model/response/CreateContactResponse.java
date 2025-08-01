package com.example.hotelmanagement.model.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateContactResponse implements Serializable {
    private Long contactId;
    private String sourceId;
}
