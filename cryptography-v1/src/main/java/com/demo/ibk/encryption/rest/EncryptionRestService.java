package com.demo.ibk.encryption.rest;

import com.demo.ibk.encryption.dto.request.DecryptionRequestDto;
import com.demo.ibk.encryption.dto.request.EncryptionRequestDto;
import com.demo.ibk.encryption.dto.response.DecryptionResponseDto;
import com.demo.ibk.encryption.dto.response.EncryptionResponseDto;
import com.demo.ibk.encryption.service.EncryptionService;
import com.demo.ibk.encryption.strategies.EncryptionMethod;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/ibk/v1/cryptography", produces = MediaType.APPLICATION_JSON_VALUE)
public class EncryptionRestService {

  private final EncryptionService service;

  @PostMapping("encrypt")
  public ResponseEntity<EncryptionResponseDto> encrypt(@Valid @RequestBody EncryptionRequestDto encryptionRequest,
                                                       @RequestHeader(name = "encryptionMethod") String encryptionMethod,
                                                       @RequestHeader(name = "feature") String feature) {
    EncryptionMethod method = EncryptionMethod.parse(encryptionMethod);
    return ResponseEntity.ok(service.encrypt(method, feature, encryptionRequest.getValue()));
  }

  @PostMapping("decrypt")
  public ResponseEntity<DecryptionResponseDto> decrypt(@Valid @RequestBody DecryptionRequestDto decryptionRequest,
                                                       @RequestHeader(name = "encryptionMethod") String encryptionMethod,
                                                       @RequestHeader(name = "feature") String feature) {
    EncryptionMethod method = EncryptionMethod.parse(encryptionMethod);
    return ResponseEntity.ok(service.decrypt(method, feature, decryptionRequest.getCipherMessage()));
  }

}
