package com.one27001.util.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

// TODO: move to a separate library and import as JAR-in-JAR
public class JsonMCTemplate extends JsonMCHelper {
  private Path basePath;

  public JsonMCTemplate(String path) throws Exception {
    this(JsonMCConfigurer.builder().basePath(path).build());
  }

  public JsonMCTemplate(JsonMCConfigurer configurer) throws Exception {
    this.log = configurer.getLogger();

    Path basePath = Path.of(configurer.getBasePath());
    this.validateBasePath(basePath);
    this.basePath = basePath;

    this.gson = configurer.getGson();
  }

  public <T extends BaseJsonMCEntity> T save(T toSave, Class<T> clazz) throws Exception {
    return this.save(toSave, true, clazz);
  }

  public <T extends BaseJsonMCEntity> T save(T toSave, boolean update, Class<T> clazz) throws Exception {
    String docId = toSave.getId();
    this.validateID(docId);

    boolean didWrite = false;
    Path filePath = this.basePath.resolve(this.getJsonFileNameForEntity(toSave.getClass()));
    this.prepareDocumentToSave(toSave);

    log.debug("Saving document {} with ID {} to path {}", toSave, docId, filePath);

    if (!Files.exists(filePath)) {
      // create file for first document of this type
      filePath = Files.createFile(filePath);
    }

    Map<String, T> currentDataMap = this.deserializeToMap(Files.readString(filePath), clazz);
    if (StringUtils.isNotBlank(docId) && currentDataMap.containsKey(docId)) {
      T currentDoc = currentDataMap.get(docId);
      if (update) {
        // update
        this.validateVersion(currentDoc, toSave);
        toSave.incrementVersion();
        currentDataMap.put(docId, toSave);
        didWrite = true;
      } else {
        this.invalidate(String.format("Document with ID: \"%s\" already exists! Doc: %s",
          docId, this.getJsonString(currentDoc)));
      }
    } else {
      // insert
      if (StringUtils.isBlank(docId)) docId = this.generateID();
      currentDataMap.put(docId, toSave);
      didWrite = true;
    }

    if (didWrite) {
      // write back to file
      Files.writeString(filePath, this.getJsonString(currentDataMap.values()));
    }

    return toSave;
  }

  public <T extends BaseJsonMCEntity> T findByID(String id, Class<T> clazz) throws Exception {
    this.validateID(id);

    Path filePath = this.basePath.resolve(this.getJsonFileNameForEntity(clazz));
    if (!Files.exists(filePath)) {
      return null;
    }

    Map<String, T> map = this.deserializeToMap(Files.readString(filePath), clazz);
    return map.get(id);
  }

  public <T extends BaseJsonMCEntity> List<T> findBy(Predicate<T> filter, Class<T> clazz) throws Exception {
    return this.findAll(clazz).stream().filter(filter).toList();
  }

  public <T extends BaseJsonMCEntity> List<T> findAll(Class<T> clazz) throws Exception {
    Path filePath = this.basePath.resolve(this.getJsonFileNameForEntity(clazz));
    if (!Files.exists(filePath)) {
      return Collections.emptyList();
    }

    return this.deserializeToList(Files.readString(filePath), clazz);
  }
}
