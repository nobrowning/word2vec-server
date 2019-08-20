package com.dcg.word2vecserver.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.dcg.word2vecserver.commons.SearchTreeNode;
import com.dcg.word2vecserver.config.CorpusConfig;
import com.dcg.word2vecserver.service.CorpusService;
import com.dcg.word2vecserver.service.Word2vecService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileReader;

/**
 * @author duchenguang
 * @date 19-6-19
 */
@Component
public class CorpusTestRunner implements CommandLineRunner {

  @Resource
  private CorpusService corpusService;

  @Resource
  private Word2vecService word2vecService;

  @Override
  public void run(String... args) throws Exception {
    System.out.println(corpusService.tokenize("uk clinical guidelines"));
    System.out.println(corpusService.tokenize("uk clinical"));
  }
}
