package com.demo.ibk.business.customer.infrastructure.exception.builder;

import com.demo.ibk.business.customer.infrastructure.exception.model.ApiExceptionDetail;
import org.apache.commons.lang3.StringUtils;
import java.util.Optional;

public class ApiExceptionDetailBuilder {

  private String title;
  private String component;
  private final ApiExceptionBuilder builder;

  public ApiExceptionDetailBuilder(ApiExceptionBuilder builder) {
    this.builder = builder;
  }

  public ApiExceptionDetailBuilder withTitle(String title) {
    Optional.ofNullable(title)
        .filter(StringUtils::isNotBlank)
        .ifPresent(actualTitle -> this.title = actualTitle);
    return this;
  }

  public ApiExceptionDetailBuilder withComponent(String component) {
    Optional.ofNullable(component)
        .filter(StringUtils::isNotBlank)
        .ifPresent(actualComponent -> this.component = actualComponent);
    return this;
  }

  public ApiExceptionBuilder push() {
    if (StringUtils.isNotBlank(this.component)
        || StringUtils.isNotBlank(this.title)) {

      this.builder.getDetails().add(new ApiExceptionDetail(this.component, this.title));
      this.component = null;
      this.title = null;
    }
    return this.builder;
  }

}
