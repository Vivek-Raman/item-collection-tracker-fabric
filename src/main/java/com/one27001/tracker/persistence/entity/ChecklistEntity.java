package com.one27001.tracker.persistence.entity;

import com.one27001.tracker.model.Checklist;
import com.one27001.util.storage.BaseJsonMCEntity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChecklistEntity extends BaseJsonMCEntity {
  private Checklist checklist;

  @Builder
  public ChecklistEntity(String id, Checklist checklist) {
    this.setId(id);
    this.setChecklist(checklist);
  }
}
