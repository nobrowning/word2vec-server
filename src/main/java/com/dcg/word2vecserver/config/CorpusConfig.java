package com.dcg.word2vecserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author duchenguang
 * @date 19-6-19
 */
@Data
@ConfigurationProperties(prefix = "corpus")
public class CorpusConfig {

  private String searchTreePath;

  private String stopWordsPath;

  private String word2vecModelPath;

  private String phraseDictPath;
}
