package com.demo.ibk.business.commons.logging.injector;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadContextInjector {

  public static void putInContext(String key, String value) {
    ThreadContext.put(key, StringUtils.defaultString(value));
  }

  public static void populateFromTraceHeaders(Map<String, String> traceHeaders) {
    traceHeaders.forEach(ThreadContextInjector::putInContext);
  }
}
