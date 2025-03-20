package com.demo.ibk.encryption.strategies.aes;

import com.demo.ibk.commons.errors.exceptions.AesNoSuchKeyException;
import com.demo.ibk.commons.properties.custom.CryptographyTemplate;
import com.demo.ibk.encryption.strategies.EncryptionMethod;
import com.demo.ibk.encryption.strategies.EncryptionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.demo.ibk.encryption.strategies.EncryptionMethod.AES;

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
