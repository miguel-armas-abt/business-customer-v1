package com.demo.poc.commons.custom.properties.crypto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CryptographyTemplate implements Serializable {

    private String aes;
    private RSA rsa;
}
