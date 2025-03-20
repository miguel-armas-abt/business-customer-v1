package com.demo.ibk.commons.properties.custom;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CryptographyTemplate implements Serializable {

    private String aes;
    private RSA rsa;
}
