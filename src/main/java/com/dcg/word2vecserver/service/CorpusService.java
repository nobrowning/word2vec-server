package com.dcg.word2vecserver.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author duchenguang
 * @date 2019/6/14.
 */
public interface CorpusService {


  List<String> tokenize(String sentence);

}
