package com.one27001.tracker.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.one27001.tracker.model.CatalogItem;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.RegistryKey;

@RequiredArgsConstructor
public class CatalogService implements Registerable {
  private final DefaultedRegistry<Item> itemRegistry;

  /**
   * Generates the catalog.
   * @return set of all registered item IDs.
   */
  public List<CatalogItem> generateCatalog() {
    List<CatalogItem> items = new LinkedList<>();
    Set<Entry<RegistryKey<Item>, Item>> entries = itemRegistry.getEntrySet();
    entries.forEach(entry -> {
      Identifier identifier = entry.getKey().getValue();
      items.add(CatalogItem.builder()
          .itemID(identifier.toString())
          .itemGroup(entry.getValue().getGroup().getName())
          .build());
    });

    return items;
  }
}
