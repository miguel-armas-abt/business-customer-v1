package com.demo.ibk.encryption.strategies.rsa;

import com.demo.ibk.commons.errors.exceptions.RsaNoSuchPrivateKeyException;
import com.demo.ibk.commons.errors.exceptions.RsaNoSuchPublicKeyException;
import com.demo.ibk.commons.properties.custom.CryptographyTemplate;
import com.demo.ibk.encryption.strategies.EncryptionMethod;
import com.demo.ibk.encryption.strategies.EncryptionStrategy;
import com.demo.ibk.encryption.strategies.rsa.keys.KeyFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

import static com.demo.ibk.encryption.strategies.EncryptionMethod.RSA;

@Component
@RequiredArgsConstructor
public class RSAEncryptionStrategy implements EncryptionStrategy {

    private final KeyFileReader keyReader;
    private final RSAEncryptor rsaEncryptor;

    @Override
    public String encrypt(String value, CryptographyTemplate feature) {
        return rsaEncryptor.encrypt(searchPublicKey(feature), value);
    }

    @Override
    public String decrypt(String cipherMessage, CryptographyTemplate feature) {
        return rsaEncryptor.decrypt(searchPrivateKey(feature), cipherMessage);
    }

    @Override
    public boolean supports(EncryptionMethod encryptionMethod) {
        return RSA.equals(encryptionMethod);
    }

    private PublicKey searchPublicKey(CryptographyTemplate feature) {
        String base64Key = Optional.ofNullable(feature.getRsa().getPublicKey())
            .orElseThrow(RsaNoSuchPublicKeyException::new);
        return keyReader.getPublicKey(base64Key);
    }

    private PrivateKey searchPrivateKey(CryptographyTemplate feature) {
        String base64Key = Optional.ofNullable(feature.getRsa().getPrivateKey())
            .orElseThrow(RsaNoSuchPrivateKeyException::new);
        return keyReader.getPrivateKey(base64Key);
    }
}
