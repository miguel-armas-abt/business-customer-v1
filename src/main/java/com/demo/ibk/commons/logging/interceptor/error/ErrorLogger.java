package com.demo.ibk.commons.logging.interceptor.error;

import static com.demo.ibk.commons.logging.injector.utils.HeaderFilter.extractTraceHeaders;

import com.demo.ibk.commons.logging.injector.ThreadContextInjector;
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
    ThreadContextInjector.populateFromTraceHeaders(extractTraceHeaders(request));

    log.error(message, exception);

    ThreadContext.clearAll();
  }
}
