package com.demo.poc.encryption.service;

import com.demo.poc.encryption.dto.response.DecryptionResponseDto;
import com.demo.poc.encryption.dto.response.EncryptionResponseDto;
import com.demo.poc.encryption.strategies.EncryptionMethod;

public interface EncryptionService {

  EncryptionResponseDto encrypt(EncryptionMethod encryptionMethod,
                                String feature,
                                String valueToEncrypt);

  DecryptionResponseDto decrypt(EncryptionMethod encryptionMethod,
                                String feature,
                                String cipherMessage);
}
