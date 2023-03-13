package com.one27001.tracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.one27001.tracker.model.CatalogItem;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
    List<CatalogItem> items = new ArrayList<>();
    Set<Entry<RegistryKey<Item>, Item>> entries = itemRegistry.getEntrySet();
    entries.forEach(entry -> {
      Identifier identifier = entry.getKey().getValue();
      items.add(CatalogItem.builder()
          .itemID(identifier.toString())
          .itemGroup(Optional.ofNullable(entry.getValue().getGroup()).map(ItemGroup::getName)
              .orElse(StringUtils.EMPTY))
          .stackQuantity(Long.valueOf(entry.getValue().getMaxCount()))
          .build());
    });

    return items;
  }
}
