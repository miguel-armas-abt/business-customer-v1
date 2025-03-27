package com.demo.poc.customer.repository.cryptography.wrapper.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecryptionRequestWrapper implements Serializable {

    private String cipherMessage;
}
