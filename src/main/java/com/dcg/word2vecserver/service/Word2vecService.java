package com.dcg.word2vecserver.service;

import java.util.Collection;

/**
 * @author duchenguang
 * @date 2019/6/14.
 */
public interface  Word2vecService {


  Collection<String> similarWords(String word);

}
