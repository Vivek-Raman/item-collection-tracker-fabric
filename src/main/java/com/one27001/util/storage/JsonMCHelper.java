package com.one27001.util.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonMCHelper {
  protected Gson gson;
  protected Logger log;

  /// Persistence START

  protected <T extends BaseJsonMCEntity> String getJsonFileNameForEntity(T entity) {
    return this.toKebabCase(entity.getClass().getSimpleName()).concat(".json");
  }

  protected <T extends BaseJsonMCEntity> T prepareDocumentToSave(T toSave) {
    toSave.setVersion(toSave.getVersion() + 1);
    return toSave;
  }

  protected <T extends BaseJsonMCEntity> void validateVersion(T oldDoc, T newDoc) {
    if (oldDoc.getVersion() >= newDoc.getVersion()) {
      this.invalidate(String.format(
        "Error updating document with ID %s: Cannot save oldDoc %s over newDoc %s due to unexpected versions.",
          oldDoc.getId(), this.getJsonString(oldDoc), this.getJsonString(newDoc)));
    }
  }

  protected String generateID() {
    return UUID.randomUUID().toString();
  }

  /// Persistence END

  /// Serialization START

  protected String getJsonString(Object toSerialize) {
    return gson.toJson(toSerialize);
  }

  protected <T> T readJsonString(String toDeserialize, TypeToken<T> targetType) {
    return gson.fromJson(toDeserialize, targetType.getType());
  }

  protected <T extends BaseJsonMCEntity> Map<String, T> deserializeToMap(String content) {
    List<T> list = this.readJsonString(content, new TypeToken<ArrayList<T>>() {});
    if (Objects.isNull(list) || list.isEmpty()) return new LinkedHashMap<>();
    Map<String, T> map = list.stream().collect(Collectors.toMap(BaseJsonMCEntity::getId, Function.identity()));
    return map;
  }

  /// Serialization END

  /// Validation START

  protected void validateBasePath(Path basePath) throws Exception {
    Objects.requireNonNull(basePath);

    if (!Files.exists(basePath)) {
      log.warn("Base path {} does not exist, creating directory.", basePath.toString());
      Files.createDirectory(basePath);
    }

    if (!Files.isDirectory(basePath)) {
      this.invalidate(String.format("Path %s is not a directory.", basePath.toString()));
    }

    if (!Files.isWritable(basePath)) {
      this.invalidate(String.format("Path %s is not writable.", basePath.toString()));
    }
  }

  protected void invalidate(String errorMessage) {
    throw new IllegalArgumentException(errorMessage);
  }

  /// Validation END

  /// Formatting START

  protected String toKebabCase(String camelCase) {
    // TODO: implement
    return camelCase;
  }

  /// Formatting END
}
