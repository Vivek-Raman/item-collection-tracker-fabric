package com.one27001.util.storage;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonMCHelper {
  protected Gson gson;
  protected Logger log;

  /// Persistence START

  protected <T extends BaseJsonMCEntity> String getJsonFileNameForEntity(Class<T> entityClazz) {
    return this.toKebabCase(entityClazz.getSimpleName()).concat(".json");
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

  protected <T> T readJsonString(String toDeserialize, Type type) {
    return gson.fromJson(toDeserialize, type);
  }

  protected <T extends BaseJsonMCEntity> List<T> deserializeToList(String content, Class<T> clazz) {
    List<T> list = this.readJsonString(content, TypeToken.getParameterized(List.class, clazz).getType());
    if (Objects.isNull(list)) list = new ArrayList<>();
    return list;
  }

  protected <T extends BaseJsonMCEntity> Map<String, T> deserializeToMap(String content, Class<T> clazz) {
    Map<String, T> map = new LinkedHashMap<>();
    List<T> list = this.deserializeToList(content, clazz);
    if (Objects.isNull(list) || list.isEmpty()) return map;

    for (T entity : list) {
      map.put(entity.getId(), entity);
    }
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

  protected void validateConfigPath(Path configPath) throws Exception {
    this.validateBasePath(configPath);
    if (!configPath.toString().contains("config")) {
      this.invalidate(String.format("Path %s is not a valid config path! Try FabricLoader.getInstance().getConfigDir().resolve(<modid>).toString()",
        configPath.toString()));
    }
  }

  protected void validateID(String id) {
    if (StringUtils.isBlank(id)) {
      this.invalidate(String.format("Parameter id: \"{}\" cannot be blank", id));
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
