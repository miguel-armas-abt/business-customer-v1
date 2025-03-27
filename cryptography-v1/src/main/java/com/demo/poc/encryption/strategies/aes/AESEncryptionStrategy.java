package com.demo.poc.encryption.strategies.aes;

import com.demo.poc.commons.custom.exceptions.AesNoSuchKeyException;
import com.demo.poc.commons.custom.properties.crypto.CryptographyTemplate;
import com.demo.poc.encryption.strategies.EncryptionMethod;
import com.demo.poc.encryption.strategies.EncryptionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.demo.poc.encryption.strategies.EncryptionMethod.AES;

@Component
@RequiredArgsConstructor
public class AESEncryptionStrategy implements EncryptionStrategy {

    @Override
    public String encrypt(String value, CryptographyTemplate feature) {
        return AESEncryptor.encrypt(searchKey(feature), value);
    }

    @Override
    public String decrypt(String cipherMessage, CryptographyTemplate feature) {
        return AESEncryptor.decrypt(searchKey(feature), cipherMessage);
    }

    @Override
    public boolean supports(EncryptionMethod encryptionMethod) {
        return AES.equals(encryptionMethod);
    }

    private String searchKey(CryptographyTemplate feature) {
        return Optional.ofNullable(feature.getAes())
            .orElseThrow(AesNoSuchKeyException::new);
    }
}
