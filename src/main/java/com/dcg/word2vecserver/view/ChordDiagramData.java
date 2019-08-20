package com.dcg.word2vecserver.view;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author duchenguang
 * @date 19-6-21
 */
@Data
public class ChordDiagramData implements Serializable {

  private Set<Node> nodes;

  private Set<Link> links;

  public ChordDiagramData() {
    nodes = new HashSet<>(3);
    links = new HashSet<>(60);
  }

  public void addNode(String nodeName) {
    nodes.add(new Node(nodeName));
  }

  public void addLink(String sourceName, String targetName) {
    Link link = new Link(sourceName, targetName);
    links.add(link);
  }
}
