package com.one27001;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("item-collection-tracker");

	@Override
	public void onInitialize() {
		for (Identifier id : Registry.ITEM.getIds()) {
      Item item = Registry.ITEM.get(id);
      LOGGER.info(item.getName());
    }

		LOGGER.info("Hello Fabric world!");
	}
}
