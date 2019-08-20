package com.dcg.word2vecserver.controller;

import com.dcg.word2vecserver.commons.CommonModel;
import com.dcg.word2vecserver.service.Word2vecService;
import com.dcg.word2vecserver.view.ChordDiagramData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author duchenguang
 * @date 19-6-20
 */
@RestController
@RequestMapping("/similar-words")
public class SimilarWordsController {

  @Resource
  private Word2vecService word2vecService;

  @GetMapping("")
  public ResponseEntity<CommonModel<Collection<String>>> getSimilarWords(@RequestParam String queryWord) {
    Collection<String> similarWords = word2vecService.similarWords(queryWord);
    CommonModel<Collection<String>> model =
        new CommonModel<>(200, "success", similarWords);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }

  @GetMapping("/chord-data")
  public ResponseEntity<CommonModel<ChordDiagramData>> wordRelations(@RequestParam List<String> queryWords) {
    ChordDiagramData diagramData = new ChordDiagramData();
    for (String queryWord : queryWords) {
      word2vecService.similarWords(queryWord).forEach(word ->{
        diagramData.addNode(word);
        diagramData.addLink(queryWord, word);
      });
    }
    CommonModel<ChordDiagramData> model = new CommonModel<>(200, "success", diagramData);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }

}
