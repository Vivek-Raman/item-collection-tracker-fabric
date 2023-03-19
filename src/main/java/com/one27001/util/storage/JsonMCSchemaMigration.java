package com.one27001.util.storage;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public abstract class JsonMCSchemaMigration {
  private static Map<Long, JsonMCSchemaMigration> migrations = new TreeMap<>();

  public JsonMCSchemaMigration() {
    if (this.schemaVersion() <= 0) {
      throw new IllegalArgumentException(String.format(
        "Schema version must be greater than zero (0), current value is: %l.", this.schemaVersion()));
    }
    if (JsonMCSchemaMigration.migrations.containsKey(this.schemaVersion())) {
      throw new RuntimeException(String.format("Schema version %l already exists!", this.schemaVersion()));
    }

    JsonMCSchemaMigration.migrations.put(this.schemaVersion(), this);
  }

  /**
   * Call this method at the initialization entrypoint, so that the
   * migrations are executed and ready for the mod right away.
   * Consider this a feature flag: if this method isn't called, no migrations will occur.
   *
   * @param jsonMCTemplate used to determine active schema from metadata, and carry out
   *                       the migration. Do not pass a config template here.
   * @throws Exception
   */
  public static void runMigrations(JsonMCTemplate jsonMCTemplate) throws Exception {
    EntityMetadata metadata = jsonMCTemplate.getConfigTemplate()
      .findByID(EntityMetadata.DEFAULT_ID, EntityMetadata.class);

    for (Entry<Long, JsonMCSchemaMigration> entry : JsonMCSchemaMigration.migrations.entrySet()) {
      long version = entry.getKey();
      JsonMCSchemaMigration migration = entry.getValue();

      // determine current schema version
      long activeSchema = metadata.getActiveSchemaVersion();

      // skip migrations before active schema version
      if (activeSchema >= version) {
        continue;
      }

      try {
        // run migration
        migration.migrate(jsonMCTemplate);
      } catch (Exception e) {
        migration.rollback(jsonMCTemplate, e);
        throw e;
      }

      // set active schema to migration's schema
      metadata.setActiveSchemaVersion(version);
      metadata = jsonMCTemplate.getConfigTemplate().save(metadata, EntityMetadata.class);
    }
  }

  /**
   * The version of the schema. Whenever a new schema change is required,
   * increment the value returned by this method's implementation.
   * Returned value must always be greater than zero, and two classes
   * may not return the same value.
   *
   * @return schema version
   */
  public abstract long schemaVersion();

  /**
   * Used to determine if the migration is necessary.
   * If false, then the migration is skipped.
   * If true, then the migration will be executed, providing that
   * the schema version is greater than the active schema.
   *
   * @return whether migration is required
   */
  public boolean shouldMigrate() {
    return true;
  }

  /**
   * Define the migration process. The jsonMCTemplate is passed on
   * and can be used to run the migration script.
   *
   * @param jsonMCTemplate
   */
  public abstract void migrate(JsonMCTemplate jsonMCTemplate);


  /**
   * Define the rollback process to be executed in case of a failure
   * in the migration process. The exception is passed on as well.
   *
   * @param jsonMCTemplate
   */
  public abstract void rollback(JsonMCTemplate jsonMCTemplate, Throwable throwable);
}
