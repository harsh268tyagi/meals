package com.assignment.meal;

import org.jasypt.util.text.AES256TextEncryptor;

public class EncryptSecret {
    public static void main(String[] args) {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("Harsh_PS");
        String encryptedApiId = encryptor.encrypt("c89f7fd0");
        String encryptedApiKey = encryptor.encrypt("b18626c81ca340a61e64ebf9ab1d4f49");
        String encryptedAccountUser = encryptor.encrypt("PS");
        String documenuApiKey = encryptor.encrypt("1f7b2c0d8e3a4b6c9f5d8e3a4b6c9f5d");

        System.out.println("Encrypted Edaman API Id: ENC(" + encryptedApiId + ")");
        System.out.println("Encrypted API Key: ENC(" + encryptedApiKey + ")");
        System.out.println("Encrypted Account User: ENC(" + encryptedAccountUser + ")");
        System.out.println("Encrypted Documenu API Id: ENC(" + documenuApiKey + ")");
    }
}

