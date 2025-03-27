package com.demo.poc.encryption.service.impl;

import com.demo.poc.commons.custom.exceptions.NoSuchCryptographyTemplateException;
import com.demo.poc.commons.custom.exceptions.NoSuchEncryptionStrategyException;
import com.demo.poc.commons.custom.properties.ApplicationProperties;
import com.demo.poc.commons.custom.properties.crypto.CryptographyTemplate;
import com.demo.poc.encryption.dto.response.DecryptionResponseDto;
import com.demo.poc.encryption.dto.response.EncryptionResponseDto;
import com.demo.poc.encryption.service.EncryptionService;
import com.demo.poc.encryption.strategies.EncryptionMethod;
import com.demo.poc.encryption.strategies.EncryptionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EncryptionServiceImpl implements EncryptionService {

    private final List<EncryptionStrategy> strategies;
    private final ApplicationProperties properties;

    @Override
    public EncryptionResponseDto encrypt(EncryptionMethod encryptionMethod, String feature, String valueToEncrypt) {
        String cipherMessage = selectStrategy(encryptionMethod)
            .encrypt(valueToEncrypt, searchFeature(feature));
        return EncryptionResponseDto.builder().cipherMessage(cipherMessage).build();
    }

    @Override
    public DecryptionResponseDto decrypt(EncryptionMethod encryptionMethod, String feature, String cipherMessage) {
        String value = selectStrategy(encryptionMethod).decrypt(cipherMessage, searchFeature(feature));
        return DecryptionResponseDto.builder().value(value).build();
    }

    private EncryptionStrategy selectStrategy(EncryptionMethod encryptionMethod) {
        return strategies
            .stream()
            .filter(strategy -> strategy.supports(encryptionMethod))
            .findFirst()
            .orElseThrow(NoSuchEncryptionStrategyException::new);
    }

    private CryptographyTemplate searchFeature(String feature) {
        return Optional.ofNullable(properties.getCryptography().get(feature.toLowerCase(Locale.ROOT)))
            .orElseThrow(NoSuchCryptographyTemplateException::new);
    }
}
