package com.one27001.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Logger;

public class ClassRegistry {
  private static ClassRegistry instance;
  private static boolean initialized = false;
  private Map<String, Registerable> classes;

  public ClassRegistry() {
    classes = new HashMap<>();
  }

  public static void init(Logger log) {
    if (initialized || Objects.nonNull(instance)) {
      log.error("ClassRegistry is already initialized!");
    }

    instance = new ClassRegistry();
    initialized = true;
  }

  public static void register(Registerable toRegister) {
    ClassRegistry.instance.classes.put(toRegister.getClass().getName(), toRegister);
    toRegister.init();
  }

  @SuppressWarnings("unchecked")
  public static <T extends Registerable> T supply(Class<T> clazz) {
    return (T) ClassRegistry.instance.classes.get(clazz.getName());
  }
}
