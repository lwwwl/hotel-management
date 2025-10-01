package com.example.hotelmanagement;

import com.example.hotelmanagement.service.HotelUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserLdapSyncTest {

    @Autowired
    private HotelUserService hotelUserService;

    @Test
    public void syncAllUsersToLdap() {
        System.out.println("Starting full synchronization of users to LDAP...");
        hotelUserService.syncAllUsersToLdap();
        System.out.println("Full synchronization of users to LDAP completed.");
    }
}
