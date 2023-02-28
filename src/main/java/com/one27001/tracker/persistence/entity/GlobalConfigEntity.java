package com.one27001.tracker.persistence.entity;

import org.apache.commons.beanutils.BeanUtils;

import com.one27001.tracker.model.GlobalConfig;

import io.jsondb.annotation.Document;

@Document(collection = GlobalConfigEntity.COLLECTION_NAME, schemaVersion = "1.0")
public class GlobalConfigEntity extends GlobalConfig {
  static final String COLLECTION_NAME = "global_config";

  public static Object fromGlobalConfig(GlobalConfig toPersist) {
    GlobalConfigEntity entity = new GlobalConfigEntity();
    try {
      BeanUtils.copyProperties(entity, toPersist);
    } catch (Exception e) {
      // TODO: log this error
      return null;
    }
    return entity;
  }
}
