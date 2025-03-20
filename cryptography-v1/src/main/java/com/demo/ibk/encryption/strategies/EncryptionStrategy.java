package com.demo.ibk.encryption.strategies;

import com.demo.ibk.commons.properties.custom.CryptographyTemplate;

public interface EncryptionStrategy {

    String encrypt(String value, CryptographyTemplate feature);
    String decrypt(String cipherMessage, CryptographyTemplate feature);

    boolean supports(EncryptionMethod encryptionMethod);
}
