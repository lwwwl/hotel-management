package com.example.hotelmanagement.util;

public class GuestContext {
    private static final ThreadLocal<Long> GUEST_ID = new ThreadLocal<>();

    public static void setGuestId(Long guestId) {
        GUEST_ID.set(guestId);
    }

    public static Long getGuestId() {
        return GUEST_ID.get();
    }

    public static void clear() {
        GUEST_ID.remove();
    }
}
