package com.example.hotelmanagement.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class IdentifierHashGenerator {

    public static void main(String[] args) {
        String identifier = "user@example.com"; // 客户端唯一标识
        String hmacToken = "your_inbox_hmac_token_here"; // inbox 的 HMAC token

        String hash = generateIdentifierHash(identifier, hmacToken);
        System.out.println("identifier_hash: " + hash);
    }

    public static String generateIdentifierHash(String identifier, String hmacToken) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(hmacToken.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);

            byte[] hmacBytes = mac.doFinal(identifier.getBytes());

            // Chatwoot expects the hash in Base64 encoded form
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate identifier_hash", e);
        }
    }
}
