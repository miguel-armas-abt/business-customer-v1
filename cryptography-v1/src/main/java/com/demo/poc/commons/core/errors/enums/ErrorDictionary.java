package com.demo.poc.commons.core.errors.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorDictionary {

    //system=00
    INVALID_INPUT_FIELD("02.00.01", "Non-valid input field"),

    //custom=01
    NO_SUCH_ENCRYPTION_METHOD("02.01.01", "No such encryption method"),
    AES_NO_SUCH_KEY("02.01.02", "No such AES key"),
    AES_UNEXPECTED_ENCRYPTION("02.01.03", "Unexpected AES encryption error"),
    AES_UNEXPECTED_DECRYPTION("02.01.04", "Unexpected AES decryption error"),
    AES_INVALID_IV_LENGTH("02.01.05", "Invalid IV length"),
    RSA_UNEXPECTED_PUBLIC_KEY_READING("02.01.06", "Unexpected RSA public key reading error"),
    RSA_UNEXPECTED_PRIVATE_KEY_READING("02.01.07", "Unexpected RSA private key reading error"),
    RSA_UNEXPECTED_ENCRYPTION("02.01.08", "Unexpected RSA encryption error"),
    RSA_UNEXPECTED_DECRYPTION("02.01.09", "Unexpected RSA decryption error"),
    RSA_UNEXPECTED_CIPHER("02.01.10", "Unexpected Cipher creation error"),
    RSA_NO_SUCH_PRIVATE_KEY("02.01.11", "No such RSA private key"),
    RSA_NO_SUCH_PUBLIC_KEY("02.01.12", "No such RSA public key"),
    NO_SUCH_ENCRYPTION_STRATEGY("02.01.13", "No such encryption strategy"),
    NO_SUCH_CRYPTOGRAPHY_TEMPLATE("02.01.14", "No such cryptography template by feature");

    private final String code;
    private final String message;
}
