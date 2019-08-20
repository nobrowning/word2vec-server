package com.dcg.word2vecserver.view;

import lombok.Data;

/**
 * @author duchenguang
 * @date 19-6-21
 */
@Data
public class Link {

  private String source;

  private String target;

  private Float weight;

  public Link() {
  }

  public Link(String source, String target) {
    this.source = source;
    this.target = target;
    this.weight = 1.0f;
  }

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

    Link other = (Link) obj;
    if (this.source == null){
      if (other.source != null){
        return false;
      }
    }else {
      if (!other.source.equals(this.source)){
        return false;
      }
    }
    if (this.target == null){
      if (other.target != null){
        return false;
      }
    }else {
      if (!other.target.equals(this.target)){
        return false;
      }
    }
    return true;
  }

  /**
   * 重写方法,由于目前关系都是单向的，所以忽略source和target的区别
   * @return
   */
  @Override
  public int hashCode() {
    return this.target.hashCode() + this.source.hashCode();
  }
}
