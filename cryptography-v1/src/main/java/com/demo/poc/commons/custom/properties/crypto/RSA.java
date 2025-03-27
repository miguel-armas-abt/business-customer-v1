package com.demo.poc.commons.custom.properties.crypto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RSA implements Serializable {

    private String privateKey;
    private String publicKey;
}
