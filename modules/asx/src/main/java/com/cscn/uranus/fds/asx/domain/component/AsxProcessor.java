package com.cscn.uranus.fds.asx.domain.component;

import com.cscn.uranus.fds.asx.domain.entity.AsxGram;
import com.cscn.uranus.fds.asx.domain.service.AsxGramManager;
import com.cscn.uranus.fds.core.telegraph.asx.dom.AsxGramDom;
import com.cscn.uranus.fds.core.telegraph.asx.mapper.AsxGramMapper;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AsxProcessor {

  private final AsxGramManager asxGramManager;

  public AsxProcessor(AsxGramManager asxGramManager) {
    this.asxGramManager = asxGramManager;
  }

  public void process(String input) {
    AsxGramMapper asxGramMapper = new AsxGramMapper();
    asxGramMapper.readValue(input);
    Set<AsxGramDom> asxGramDoms = asxGramMapper.getAsxGramDoms();
    Set<AsxGram> asxGrams = new HashSet<>();
    for(AsxGramDom asxGramDom: asxGramDoms) {
      AsxGram asxGram = new AsxGram(asxGramDom.getContent());
      asxGrams.add(asxGram);
    }
    this.asxGramManager.addAll(asxGrams);
  }
}
