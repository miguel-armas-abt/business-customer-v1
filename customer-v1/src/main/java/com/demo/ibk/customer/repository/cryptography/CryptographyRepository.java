package com.demo.ibk.customer.repository.cryptography;

import com.demo.ibk.commons.errors.exceptions.CryptographyResponseNullException;
import com.demo.ibk.commons.properties.ApplicationProperties;
import com.demo.ibk.commons.properties.base.restclient.RestClient;
import com.demo.ibk.customer.repository.cryptography.wrapper.request.DecryptionRequestWrapper;
import com.demo.ibk.customer.repository.cryptography.wrapper.request.EncryptionRequestWrapper;
import com.demo.ibk.customer.repository.cryptography.wrapper.response.DecryptionResponseWrapper;
import com.demo.ibk.customer.repository.cryptography.wrapper.response.EncryptionResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CryptographyRepository {

    private static final String SERVICE_NAME = "cryptography-v1";

    private final RestTemplate restTemplate;
    private final ApplicationProperties properties;

    public String encrypt(String value) {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        EncryptionRequestWrapper encryptionRequest = EncryptionRequestWrapper.builder().value(value).build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(restClient.getHeaders());

        return Optional.ofNullable(restTemplate.exchange(
            restClient.getEndpoint() + "/encrypt",
            HttpMethod.POST,
            new HttpEntity<>(encryptionRequest, httpHeaders),
            EncryptionResponseWrapper.class)
            .getBody())
            .orElseThrow(CryptographyResponseNullException::new)
            .getCipherMessage();
    }

    public String decrypt(String cipherMessage) {
        RestClient restClient = properties.getRestClients().get(SERVICE_NAME);

        DecryptionRequestWrapper decryptionRequest = DecryptionRequestWrapper.builder().cipherMessage(cipherMessage).build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(restClient.getHeaders());

        return Optional.ofNullable(restTemplate.exchange(
                restClient.getEndpoint() + "/decrypt",
                HttpMethod.POST,
                new HttpEntity<>(decryptionRequest, httpHeaders),
                DecryptionResponseWrapper.class)
            .getBody())
            .orElseThrow(CryptographyResponseNullException::new)
            .getValue();
    }
}
