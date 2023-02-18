package com.one27001.util;

import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Logger;

public class ClassRegistry {
  private static ClassRegistry instance;
  private Map<Class, Object> classes;

  public static void init(Logger log) {
    if (Objects.nonNull(instance)) {
      log.error("ClassRegistry is already initialized!");
    }

    instance = new ClassRegistry();
  }

  public static void register(Object toRegister) {
    Objects.requireNonNull(ClassRegistry.instance).classes.put(toRegister.getClass(), toRegister);
  }

  public static void supply(Class clazz) {
    Objects.requireNonNull(ClassRegistry.instance).classes.get(clazz);
  }
}
