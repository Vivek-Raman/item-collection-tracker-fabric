package com.one27001.util.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EntityMetadata extends BaseJsonMCEntity {
  public static final String DEFAULT_ID = "default";

  private Long activeSchemaVersion = 1L;
}
