package com.dcg.word2vecserver.service.impl;

import com.dcg.word2vecserver.config.CorpusConfig;
import com.dcg.word2vecserver.service.CorpusService;
import com.dcg.word2vecserver.service.Word2vecService;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author duchenguang
 * @date 2019/6/14.
 */
@Service
public class Word2vecServiceImpl implements Word2vecService {

  private Word2Vec model;

  private Map<String, String> phraseWordDict;

  private CorpusService corpusService;

  @Autowired
  public Word2vecServiceImpl(CorpusConfig corpusConfig, CorpusService corpusService) {
    this.corpusService = corpusService;
    this.model = WordVectorSerializer.readWord2VecModel(corpusConfig.getWord2vecModelPath());
    this.phraseWordDict = new HashMap<>();
    try {
      FileReader dictReader = new FileReader(corpusConfig.getPhraseDictPath());
      BufferedReader bf = new BufferedReader(dictReader);
      String line;
      while((line = bf.readLine()) != null) {
        String[] phraseWordPair = line.split("\t");
        this.phraseWordDict.put(phraseWordPair[0], phraseWordPair[1]);
      }
    }catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Collection<String> similarWords(String word) {
    List<String> tokens = corpusService.tokenize(word);
    String unionToken = String.join("_", tokens);
    INDArray vector = Nd4j.zeros(1,300);
    for (String token : tokens) {
      if (model.hasWord(token)) {
        INDArray matrix = this.model.getWordVectorMatrix(token);
        System.out.println(token);
        vector.addi(matrix);
      }
    }

    return model.wordsNearest(vector, 30).stream()
        .filter(w -> !w.equals(unionToken))
        .map(this.phraseWordDict::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
