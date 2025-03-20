package com.demo.ibk.commons.properties;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "configuration")
public class ApplicationProperties {

  private Map<String, String> errorMessages;
}
