package com.demo.poc.encryption.rest;

import com.demo.poc.encryption.dto.request.DecryptionRequestDto;
import com.demo.poc.encryption.dto.request.EncryptionRequestDto;
import com.demo.poc.encryption.dto.response.DecryptionResponseDto;
import com.demo.poc.encryption.dto.response.EncryptionResponseDto;
import com.demo.poc.encryption.service.EncryptionService;
import com.demo.poc.encryption.strategies.EncryptionMethod;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.poc.encryption.constants.EncryptionConstant.ENCRYPTION_METHOD_PARAM;
import static com.demo.poc.encryption.constants.EncryptionConstant.FEATURE_PARAM;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/poc/cryptography/v1/encryption", produces = MediaType.APPLICATION_JSON_VALUE)
public class EncryptionRestService {

  private final EncryptionService service;

  @PostMapping("encrypt")
  public ResponseEntity<EncryptionResponseDto> encrypt(@Valid @RequestBody EncryptionRequestDto encryptionRequest,
                                                       @RequestHeader(name = ENCRYPTION_METHOD_PARAM) String encryptionMethod,
                                                       @RequestHeader(name = FEATURE_PARAM) String feature) {
    EncryptionMethod method = EncryptionMethod.parse(encryptionMethod);
    return ResponseEntity.ok(service.encrypt(method, feature, encryptionRequest.getValue()));
  }

  @PostMapping("decrypt")
  public ResponseEntity<DecryptionResponseDto> decrypt(@Valid @RequestBody DecryptionRequestDto decryptionRequest,
                                                       @RequestHeader(name = ENCRYPTION_METHOD_PARAM) String encryptionMethod,
                                                       @RequestHeader(name = FEATURE_PARAM) String feature) {
    EncryptionMethod method = EncryptionMethod.parse(encryptionMethod);
    return ResponseEntity.ok(service.decrypt(method, feature, decryptionRequest.getCipherMessage()));
  }

}
