package com.cscn.uranus.fds.core.telegraph.asx.mapper;

import com.cscn.uranus.fds.core.telegraph.TelegramMapper;
import com.cscn.uranus.fds.core.telegraph.asx.dom.AsxGramDom;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsxGramMapper implements TelegramMapper {
  private Set<AsxGramDom> asxGramDoms = new HashSet<>();

  public Set<AsxGramDom> getAsxGramDoms() {
    return asxGramDoms;
  }

  @Override
  public Pattern regexPattern() {
    return Pattern.compile("ZCZC[\\s\\S]*?NNNN");
  }

  @Override
  public void readValue(String input) {
    Matcher matcher = this.regexPattern().matcher(input);
    while (matcher.find()) {
      String content = matcher.group(1);
      AsxGramDom asxGramDom = new AsxGramDom(content);
      this.asxGramDoms.add(asxGramDom);
    }
  }
}
