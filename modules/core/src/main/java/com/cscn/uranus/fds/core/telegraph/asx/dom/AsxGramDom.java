package com.cscn.uranus.fds.core.telegraph.asx.dom;

import com.cscn.uranus.fds.core.telegraph.TelegramDom;

public class AsxGramDom implements TelegramDom {

  private final String header = "ZCZC";
  private final String tail = "NNNN";
  private String content;

  public AsxGramDom(String content) {
    this.content = content;
  }

  @Override
  public String getHeader() {
    return this.header;
  }

  @Override
  public String getContent() {
    return this.content;
  }

  @Override
  public String getTail() {
    return this.tail;
  }



  @Override
  public int length() {
    return this.header.length() + this.content.length() + this.tail.length();
  }

}
