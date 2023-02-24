package com.one27001.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

public class MyLogger {
  private static final String PREFIX = "one27001-item-collection-tracker";
  private static final Logger logger = LogManager.getLogger("item-collection-tracker", new MyMessageFactory(PREFIX));

  public static Logger get() {
    return logger;
  }
}

class MyMessageFactory extends AbstractMessageFactory {
  private ParameterizedMessageFactory factory = new ParameterizedMessageFactory();
  private final String prefix;

  public MyMessageFactory(String prefix) {
    this.prefix = prefix;
  }

  private String attachPrefix(String message) {
    return "[" + prefix + "] " + message;
  }

  @Override
  public Message newMessage(CharSequence message) {
    return factory.newMessage(attachPrefix(message.toString()));
  }

  @Override
  public Message newMessage(Object message) {
    return factory.newMessage(attachPrefix(message.toString()));
  }

  @Override
  public Message newMessage(String message) {
    return factory.newMessage(attachPrefix(message));
  }

  @Override
  public Message newMessage(String message, Object p0) {
    return factory.newMessage(attachPrefix(message), p0);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1) {
    return factory.newMessage(attachPrefix(message), p0, p1);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3, p4);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3, p4, p5);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5,
      Object p6) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3, p4, p5, p6);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
      Object p7) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3, p4, p5, p6, p7);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
      Object p7, Object p8) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3, p4, p5, p6, p7, p8);
  }

  @Override
  public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
      Object p7, Object p8, Object p9) {
    return factory.newMessage(attachPrefix(message), p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
  }

  @Override
  public Message newMessage(String message, Object... params) {
    return factory.newMessage(attachPrefix(message), params);
  }
}