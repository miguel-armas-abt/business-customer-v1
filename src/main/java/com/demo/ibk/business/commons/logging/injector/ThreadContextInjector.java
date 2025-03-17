package com.demo.ibk.business.commons.logging.injector;

import static com.demo.ibk.business.commons.logging.enums.LoggerType.REST_SERVER_REQ;
import static com.demo.ibk.business.commons.logging.enums.LoggerType.REST_SERVER_RES;
import static com.demo.ibk.business.commons.logging.injector.ThreadContextUtils.*;

import java.util.Map;
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
    populateFromRestRequest(REST_SERVER_REQ.getCode(), method, uri, headers, body);
    log.info(REST_SERVER_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public static void populateFromRestServerResponse(Map<String, String> headers, String body, String httpCode) {
    populateFromRestResponse(REST_SERVER_RES.getCode(), headers, body, httpCode);
    log.info(REST_SERVER_RES.getMessage());
    ThreadContext.clearAll();
  }

  private static void populateFromRestRequest(String prefix, String method, String uri, Map<String, String> headers, String body) {
    populateFromTraceHeaders(extractTraceFieldsFromHeaders(headers));
    putInContext(prefix + METHOD, method);
    putInContext(prefix + URI, uri);
    putInContext(prefix + HEADERS, getHeadersAsString(headers));
    putInContext(prefix + BODY, body);
  }

  private static void populateFromRestResponse(String prefix, Map<String, String> headers, String body, String httpCode) {
    populateFromTraceHeaders(extractTraceFieldsFromHeaders(headers));
    putInContext(prefix + HEADERS, getHeadersAsString(headers));
    putInContext(prefix + BODY, body);
    putInContext(prefix + STATUS, httpCode);
  }
}
