package com.demo.ibk.business.commons.logging.error;

import static com.demo.ibk.business.commons.logging.injector.ThreadContextUtils.extractTraceFieldsFromHeaders;

import com.demo.ibk.business.commons.logging.injector.ThreadContextInjector;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorLogger {

  public static void generateTrace(Throwable exception, WebRequest request) {
    String message = exception.getMessage();
    ThreadContextInjector.populateFromTraceHeaders(extractTraceFieldsFromHeaders(request));

    log.error(message, exception);

    ThreadContext.clearAll();
  }
}
