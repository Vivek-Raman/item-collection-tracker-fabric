package com.one27001.util.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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

  public <T extends BaseJsonMCEntity> T save(T toSave) throws Exception {
    return this.save(toSave, true);
  }

  public <T extends BaseJsonMCEntity> T save(T toSave, boolean update) throws Exception {
    String docId = toSave.getId();
    boolean didWrite = false;

    Path filePath = this.basePath.resolve(this.getJsonFileNameForEntity(toSave));
    this.prepareDocumentToSave(toSave);

    log.debug("Saving document {} with ID {} to path {}", toSave, docId, filePath);

    if (!Files.exists(filePath)) {
      // crate file for first document of this type
      log.debug("File not found: {}, creating new file", filePath);
      filePath = Files.createFile(filePath);
    }

    Map<String, T> currentDataMap = this.deserializeToMap(Files.readString(filePath));
    if (StringUtils.isNotBlank(docId) && currentDataMap.containsKey(docId)) {
      if (update) {
        // update
        T currentDoc = currentDataMap.get(docId);
        this.validateVersion(currentDoc, toSave);
        currentDataMap.put(docId, toSave);
        didWrite = true;
      }
    } else {
      // insert
      if (StringUtils.isBlank(docId)) docId = this.generateID();
      currentDataMap.put(docId, toSave);
      didWrite = true;
    }

    if (didWrite) {
      // write back to file
      log.debug("Writing {}", filePath.toString());
      Files.writeString(filePath, this.getJsonString(currentDataMap.values()));
    }

    return toSave;
  }
}
