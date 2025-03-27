package com.demo.poc.commons.core.logging;

import java.util.Map;
import com.demo.poc.commons.core.logging.enums.LoggingType;
import com.demo.poc.commons.core.logging.constants.RestLoggingConstant;
import com.demo.poc.commons.core.logging.utils.HeaderExtractor;
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

  public static void populateFromHeaders(Map<String, String> traceHeaders) {
    traceHeaders.forEach(ThreadContextInjector::putInContext);
  }

  public static void populateFromRestClientRequest(String method, String uri, Map<String, String> headers, String body) {
    populateFromRestRequest(LoggingType.REST_CLIENT_REQ.getCode(), method, uri, headers, body);
    log.info(LoggingType.REST_CLIENT_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public static void populateFromRestClientResponse(Map<String, String> headers, String uri, String body, String httpCode) {
    populateFromRestResponse(LoggingType.REST_CLIENT_RES.getCode(), uri, headers, body, httpCode);
    log.info(LoggingType.REST_CLIENT_RES.getMessage());
    ThreadContext.clearAll();
  }

  public static void populateFromRestServerRequest(String method, String uri, Map<String, String> headers, String body) {
    populateFromRestRequest(LoggingType.REST_SERVER_REQ.getCode(), method, uri, headers, body);
    log.info(LoggingType.REST_SERVER_REQ.getMessage());
    ThreadContext.clearAll();
  }

  public static void populateFromRestServerResponse(Map<String, String> headers, String uri, String body, String httpCode) {
    populateFromRestResponse(LoggingType.REST_SERVER_RES.getCode(), uri, headers, body, httpCode);
    log.info(LoggingType.REST_SERVER_RES.getMessage());
    ThreadContext.clearAll();
  }

  private static void populateFromRestRequest(String prefix, String method, String uri, Map<String, String> headers, String body) {
    populateFromHeaders(HeaderExtractor.extractTraceHeaders(headers));
    putInContext(prefix + RestLoggingConstant.METHOD, method);
    putInContext(prefix + RestLoggingConstant.URI, uri);
    putInContext(prefix + RestLoggingConstant.HEADERS, HeaderExtractor.getHeadersAsString(headers));
    putInContext(prefix + RestLoggingConstant.BODY, body);
  }

  private static void populateFromRestResponse(String prefix, String uri, Map<String, String> headers, String body, String httpCode) {
    populateFromHeaders(HeaderExtractor.extractTraceHeaders(headers));
    putInContext(prefix + RestLoggingConstant.URI, uri);
    putInContext(prefix + RestLoggingConstant.HEADERS, HeaderExtractor.getHeadersAsString(headers));
    putInContext(prefix + RestLoggingConstant.BODY, body);
    putInContext(prefix + RestLoggingConstant.STATUS, httpCode);
  }
}
