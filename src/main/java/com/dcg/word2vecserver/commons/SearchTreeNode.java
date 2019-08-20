package com.dcg.word2vecserver.commons;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @author duchenguang
 * @date 19-6-19
 */
public class SearchTreeNode implements Serializable {

  @JSONField(name="node_name")
  private String name;

  @JSONField(name="children_dict")
  private Map<String, SearchTreeNode> childDict;

  public SearchTreeNode() {

  }

  public SearchTreeNode(String name) {
    this.name = name;
    this.childDict = new HashMap<>();
  }

  public SearchTreeNode(String name, Map<String, SearchTreeNode> childDict) {
    this.name = name;
    this.childDict = childDict;
  }

  public void addDescendants(List<String> descendants) {
    String childName = descendants.get(0);
    descendants = descendants.subList(1, descendants.size() - 1);
    if(this.childDict.keySet().contains(childName)){
      if(descendants.size() != 0){
        SearchTreeNode childNode = this.childDict.get(childName);
        childNode.addDescendants(descendants);
      }
    } else {
      SearchTreeNode childNode = new SearchTreeNode(childName);
      if (descendants.size() != 0)
        childNode.addDescendants(descendants);
      this.childDict.put(childName, childNode);
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, SearchTreeNode> getChildDict() {
    return childDict;
  }

  public void setChildDict(Map<String, SearchTreeNode> childDict) {
    this.childDict = childDict;
  }

  @Override
  public String toString() {
    return "SearchTreeNode{" +
        "name='" + name + '\'' +
        ", childDict=" + childDict +
        '}';
  }
}
