package com.dcg.word2vecserver.service.impl;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;
import com.dcg.word2vecserver.commons.SearchTreeNode;
import com.dcg.word2vecserver.config.CorpusConfig;
import com.dcg.word2vecserver.service.CorpusService;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author duchenguang
 * @date 19-6-19
 */
@Service
public class CorpusServiceImpl implements CorpusService {

  private Map<String, SearchTreeNode> phraseSearchTree;

  private Logger logger = LoggerFactory.getLogger(CorpusServiceImpl.class);

  private Set<String> stopWordSet;

  private TokenizerFactory tokenizerFactory;

  @Autowired
  public CorpusServiceImpl(CorpusConfig corpusConfig) {
    this.tokenizerFactory = new DefaultTokenizerFactory();
    logger.info("build phrase search tree");
    try {
      FileReader fileReader = new FileReader(corpusConfig.getSearchTreePath());
      JSONReader reader = new JSONReader(fileReader);
      this.phraseSearchTree = reader.readObject(new TypeReference<Map<String, SearchTreeNode>>(String.class, SearchTreeNode.class) {});
    } catch (FileNotFoundException e) {
      logger.error(String.format("phrase search tree not found - %s", corpusConfig.getSearchTreePath()));
      e.printStackTrace();
    }
    logger.info("load phrase to words dict");
    this.stopWordSet = new HashSet<>();
    try {
      FileReader fileReader = new FileReader(corpusConfig.getStopWordsPath());
      BufferedReader bf = new BufferedReader(fileReader);
      String line;
      while((line = bf.readLine()) != null) {
        this.stopWordSet.add(line.trim());
      }
    } catch (Exception e) {
      logger.error("load phrase to words dict error");
      e.printStackTrace();
    }
  }

  private List<String> combinePhraseToken(List<String> tokens) {
    int index = 0;
    final int sequenceLength = tokens.size();
    List<String> returnTokens = new ArrayList<>(sequenceLength);
    while(index < sequenceLength - 1){
      StringBuilder phrase = new StringBuilder();
      phrase.append(tokens.get(index)).append(" ").append(tokens.get(index + 1));
      if (this.phraseSearchTree.keySet().contains(phrase.toString())) {
        int searchIndex = index + 1;
        SearchTreeNode childNode = this.phraseSearchTree.get(phrase.toString());
        Map<String, SearchTreeNode> childDict = childNode.getChildDict();
        while(searchIndex < sequenceLength - 1){
          String searchWord = tokens.get(searchIndex + 1);
          if (childDict == null || !childDict.keySet().contains(searchWord))
            break;
          phrase.append(" ").append(searchWord);
          childNode = childDict.get(searchWord);
          childDict = childNode.getChildDict();
          searchIndex += 1;
        }
        if (childDict == null || childDict.isEmpty()) {
          returnTokens.add(phrase.toString().replaceAll(" ", "_"));
          index = searchIndex;
        } else {
          returnTokens.add(tokens.get(index));
        }
      }else
        returnTokens.add(tokens.get(index));
      index += 1;
    }
    if (index == sequenceLength - 1)
      returnTokens.add(tokens.get(index));
    return returnTokens;
  }

  private List<String> getTokens(String sentence) {
    SnowballStemmer stemmer = new englishStemmer();
    return this.tokenizerFactory.create(sentence).getTokens().stream()
        .filter(token -> !this.stopWordSet.contains(token))
        .map(token -> {
          stemmer.setCurrent(token);
          stemmer.stem();
          return stemmer.getCurrent();
        }).collect(Collectors.toList());
  }

  @Override
  public List<String> tokenize(String sentence) {
    return combinePhraseToken(this.getTokens(sentence));
  }
}
