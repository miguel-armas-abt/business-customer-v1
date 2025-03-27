package com.demo.poc.encryption.strategies;

import com.demo.poc.commons.custom.properties.crypto.CryptographyTemplate;

public interface EncryptionStrategy {

    String encrypt(String value, CryptographyTemplate feature);
    String decrypt(String cipherMessage, CryptographyTemplate feature);

    boolean supports(EncryptionMethod encryptionMethod);
}
