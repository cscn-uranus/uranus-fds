package com.cscn.uranus.fds.asx.domain.service;

import com.cscn.uranus.fds.asx.domain.entity.AsxGram;
import com.cscn.uranus.fds.asx.domain.repo.AsxGramRepo;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AsxGramManager {

  private AsxGramRepo asxGramRepo;


  public AsxGramManager(
      AsxGramRepo asxGramRepo) {
    this.asxGramRepo = asxGramRepo;
  }

  public AsxGram add(AsxGram asxGram) {
    return this.asxGramRepo.save(asxGram);
  }

  public Set<AsxGram> addAll(Set<AsxGram> asxGrams) {
    return new HashSet<>(this.asxGramRepo.saveAll(asxGrams));
  }

  public void deleteAll() {
    this.asxGramRepo.deleteAll();
  }

  public AsxGram findById(String id) {
    return this.asxGramRepo.getOne(id);
  }

  public Set<AsxGram> findAll() {
    return new HashSet<>(this.asxGramRepo.findAll());
  }
}
