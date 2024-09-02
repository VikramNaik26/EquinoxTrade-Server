package com.vikram.EquinoxTrade.utils;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * JsonNodeUtil
 */
public class JsonNodeUtil {

  public static String getTextSafely(JsonNode node, String field) {
    return node.has(field) ? node.get(field).asText() : null;
  }

  public static String getNestedTextSafely(JsonNode node, String... fields) {
    for (String field : fields) {
      if (node == null || !node.has(field))
        return null;
      node = node.get(field);
    }
    return node != null ? node.asText() : null;
  }

  public static Double getDoubleSafely(JsonNode node, String field) {
    return node.has(field) && !node.get(field).isNull() ? node.get(field).asDouble() : null;
  }

  public static Double getNestedDoubleSafely(JsonNode node, String... fields) {
    for (String field : fields) {
      if (node == null || !node.has(field))
        return null;
      node = node.get(field);
    }
    return node != null && !node.isNull() ? node.asDouble() : null;
  }

  public static Integer getIntSafely(JsonNode node, String field) {
    return node.has(field) && !node.get(field).isNull() ? node.get(field).asInt() : null;
  }

  public static Long getLongSafely(JsonNode node, String field) {
    return node.has(field) && !node.get(field).isNull() ? node.get(field).asLong() : null;
  }

  public static Long getNestedLongSafely(JsonNode node, String... fields) {
    for (String field : fields) {
      if (node == null || !node.has(field))
        return null;
      node = node.get(field);
    }
    return node != null && !node.isNull() ? node.asLong() : null;
  }

}
