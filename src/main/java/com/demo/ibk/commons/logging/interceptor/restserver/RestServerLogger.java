//package com.demo.ibk.commons.logging.interceptor.restserver;
//
//import static com.demo.ibk.commons.logging.interceptor.restserver.RestServerLogger.RequestLoggerUtil.*;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import com.demo.ibk.commons.logging.injector.ThreadContextInjector;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//@WebFilter(urlPatterns = "/*")
//@RequiredArgsConstructor
//public class RestServerLogger implements Filter {
//
//  @Override
//  public void init(FilterConfig filterConfig) {
//  }
//
//  @Override
//  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//    HttpServletRequest httpRequest = (HttpServletRequest) request;
//    BufferingHttpServletResponse bufferingResponse = new BufferingHttpServletResponse((HttpServletResponse) response);
//    generateTraceOfRequest(httpRequest);
//    chain.doFilter(request, bufferingResponse);
//    String responseBody = bufferingResponse.getCachedBody();
//    generateTraceOfResponse(bufferingResponse, responseBody);
//    response.getOutputStream().write(responseBody.getBytes(StandardCharsets.UTF_8));
//  }
//
//  @Override
//  public void destroy() {
//  }
//
//  private void generateTraceOfRequest(HttpServletRequest request) {
//    ThreadContextInjector.populateFromRestServerRequest(
//        request.getMethod(),
//        extractRequestURL(request),
//        extractRequestHeadersAsMap(request),
//        extractRequestBody(request));
//  }
//
//  private void generateTraceOfResponse(HttpServletResponse response, String responseBody) {
//    ThreadContextInjector.populateFromRestServerResponse(
//        ResponseLoggerUtil.extractResponseHeadersAsMap(response),
//        responseBody,
//        String.valueOf(response.getStatus()));
//  }
//
//  @NoArgsConstructor(access = AccessLevel.PRIVATE)
//  public static class RequestLoggerUtil {
//    public static Map<String, String> extractRequestHeadersAsMap(HttpServletRequest request) {
//      return Optional.ofNullable(request.getHeaderNames())
//          .map(Collections::list)
//          .orElseGet(ArrayList::new)
//          .stream()
//          .collect(Collectors.toMap(headerName -> headerName, request::getHeader));
//    }
//
//    public static String extractRequestBody(HttpServletRequest request) {
//      try {
//        return new BufferingHttpServletRequest(request).getRequestBody();
//      } catch (IOException exception) {
//        log.error("Error reading request body: {}", exception.getClass(), exception);
//        return StringUtils.EMPTY;
//      }
//    }
//
//    public static String extractRequestURL(HttpServletRequest request) {
//      String url = Optional.of(request)
//          .map(HttpServletRequest::getRequestURL)
//          .map(StringBuffer::toString)
//          .orElse(StringUtils.EMPTY);
//
//      String queryString = request.getQueryString();
//      if (StringUtils.isNotBlank(queryString))
//        url += "?" + queryString;
//
//      return url;
//    }
//  }
//
//  @NoArgsConstructor(access = AccessLevel.PRIVATE)
//  private static class ResponseLoggerUtil {
//
//    public static Map<String, String> extractResponseHeadersAsMap(HttpServletResponse response) {
//      Map<String, String> headers = new HashMap<>();
//      Collection<String> headerNames = response.getHeaderNames();
//      headerNames.forEach(headerName -> headers.put(headerName, response.getHeader(headerName)));
//      return headers;
//    }
//
//  }
//}
