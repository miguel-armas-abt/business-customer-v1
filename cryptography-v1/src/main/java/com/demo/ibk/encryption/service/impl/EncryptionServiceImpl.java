package com.demo.ibk.encryption.service.impl;

import com.demo.ibk.commons.errors.exceptions.NoSuchCryptographyTemplateException;
import com.demo.ibk.commons.errors.exceptions.NoSuchEncryptionStrategyException;
import com.demo.ibk.commons.properties.ApplicationProperties;
import com.demo.ibk.commons.properties.custom.CryptographyTemplate;
import com.demo.ibk.encryption.dto.response.DecryptionResponseDto;
import com.demo.ibk.encryption.dto.response.EncryptionResponseDto;
import com.demo.ibk.encryption.service.EncryptionService;
import com.demo.ibk.encryption.strategies.EncryptionMethod;
import com.demo.ibk.encryption.strategies.EncryptionStrategy;
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
