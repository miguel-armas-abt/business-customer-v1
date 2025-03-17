package com.demo.ibk.commons.logging.injector;

import static com.demo.ibk.commons.logging.injector.constants.LoggingConstant.*;
import static com.demo.ibk.commons.logging.injector.utils.HeaderFilter.extractTraceHeaders;
import static com.demo.ibk.commons.logging.injector.utils.HeaderFilter.getHeadersAsString;

import java.util.Map;
import com.demo.ibk.commons.logging.injector.enums.LoggerType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadContextInjector {

  public static void putInContext(String key, String value) {
    ThreadContext.put(key, StringUtils.defaultString(value));
  }

  public static void populateFromTraceHeaders(Map<String, String> traceHeaders) {
    traceHeaders.forEach(ThreadContextInjector::putInContext);
  }

  public static void populateFromRestServerRequest(String method, String uri, Map<String, String> headers, String body) {
    populateFromRestRequest(LoggerType.REST_SERVER_REQ.getCode(), method, uri, headers, body);
    log.info(LoggerType.REST_SERVER_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public static void populateFromRestServerResponse(Map<String, String> headers, String body, String httpCode) {
    populateFromRestResponse(LoggerType.REST_SERVER_RES.getCode(), headers, body, httpCode);
    log.info(LoggerType.REST_SERVER_RES.getMessage());
    ThreadContext.clearAll();
  }

  private static void populateFromRestRequest(String prefix, String method, String uri, Map<String, String> headers, String body) {
    populateFromTraceHeaders(extractTraceHeaders(headers));
    putInContext(prefix + METHOD, method);
    putInContext(prefix + URI, uri);
    putInContext(prefix + HEADERS, getHeadersAsString(headers));
    putInContext(prefix + BODY, body);
  }

  private static void populateFromRestResponse(String prefix, Map<String, String> headers, String body, String httpCode) {
    populateFromTraceHeaders(extractTraceHeaders(headers));
    putInContext(prefix + HEADERS, getHeadersAsString(headers));
    putInContext(prefix + BODY, body);
    putInContext(prefix + STATUS, httpCode);
  }
}
