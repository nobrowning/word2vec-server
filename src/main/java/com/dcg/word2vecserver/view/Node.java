package com.dcg.word2vecserver.view;

import lombok.Data;

/**
 * @author duchenguang
 * @date 19-6-21
 */
@Data
public class Node{
  private String name;

  public Node() {
  }

  public Node(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null){
      return false;
    }
    if (getClass() != obj.getClass()){
      return false;
    }

    Node other = (Node) obj;
    if (this.name == null){
      return other.name == null;
    }else {
      return other.name.equals(this.name);
    }
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

}
