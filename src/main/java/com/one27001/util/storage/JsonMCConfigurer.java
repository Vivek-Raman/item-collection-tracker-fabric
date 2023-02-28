package com.one27001.util.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMCConfigurer {
  @Builder.Default private String basePath = null;
  @Builder.Default private Logger logger = LogManager.getLogger("[JsonMCTemplate]");
  @Builder.Default private Gson gson = new Gson();
}
