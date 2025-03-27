package com.demo.poc.commons.core.interceptor.restclient.request;

import com.demo.poc.commons.core.logging.ThreadContextInjector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClientRequestInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    generateTrace(request, body);
    return execution.execute(request, body);
  }

  private void generateTrace(HttpRequest request, byte[] body) {
    ThreadContextInjector.populateFromRestClientRequest(
        request.getMethod().toString(),
        request.getURI().toString(),
        request.getHeaders().toSingleValueMap(),
        new String(body, StandardCharsets.UTF_8)
    );
  }
}
