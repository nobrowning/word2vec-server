package com.dcg.word2vecserver;

import com.dcg.word2vecserver.config.CorpusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ CorpusConfig.class })
@SpringBootApplication
public class Word2vecServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(Word2vecServerApplication.class, args);
  }

}
