package com.demo.ibk.business.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class JsonFileReader {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> T getAnElement(String filePath, Class<T> className) throws IOException {
    byte[] byteArray = ByteStreams.toByteArray(Objects.requireNonNull(JsonFileReader.class.getClassLoader().getResourceAsStream(filePath)));
    String arrayJson = new String(byteArray);
    return new Gson().fromJson(arrayJson, className);
  }

  public static <T> List<T> getList(String filePath, Class<T[]> clazz) throws IOException {
    byte[] byteArray = ByteStreams.toByteArray(Objects.requireNonNull(JsonFileReader.class.getClassLoader().getResourceAsStream(filePath)));
    String arrayJson = new String(byteArray);
    return Arrays.asList(new Gson().fromJson(arrayJson, clazz));
  }

  public static <T> List<T> getList2(String filePath, Class<T[]> clazz) throws IOException {
    return Arrays.asList(mapper.readValue(JsonFileReader.class.getClassLoader().getResourceAsStream(filePath), clazz));
  }

}
