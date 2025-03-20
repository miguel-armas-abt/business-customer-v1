package com.demo.ibk.encryption.service;

import com.demo.ibk.encryption.dto.response.DecryptionResponseDto;
import com.demo.ibk.encryption.dto.response.EncryptionResponseDto;
import com.demo.ibk.encryption.strategies.EncryptionMethod;

public interface EncryptionService {

  EncryptionResponseDto encrypt(EncryptionMethod encryptionMethod,
                                String feature,
                                String valueToEncrypt);

  DecryptionResponseDto decrypt(EncryptionMethod encryptionMethod,
                                String feature,
                                String cipherMessage);
}
