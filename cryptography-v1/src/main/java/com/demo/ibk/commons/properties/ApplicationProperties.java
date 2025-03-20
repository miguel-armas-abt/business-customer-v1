package com.demo.ibk.commons.properties;

import com.demo.ibk.commons.properties.custom.CryptographyTemplate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "configuration")
public class ApplicationProperties {

  private Map<String, String> errorMessages;

  private Map<String, CryptographyTemplate> cryptography;

}
