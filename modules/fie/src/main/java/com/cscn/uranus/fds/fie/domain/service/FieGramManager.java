package com.cscn.uranus.fds.fie.domain.service;

import com.cscn.uranus.fds.fie.domain.entity.FieGram;
import com.cscn.uranus.fds.fie.domain.repo.FieGramRepo;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FieGramManager {

  private FieGramRepo fieGramRepo;


  public FieGramManager(
      FieGramRepo fieGramRepo) {
    this.fieGramRepo = fieGramRepo;
  }

  public FieGram add(FieGram fieGram) {
    return this.fieGramRepo.save(fieGram);
  }

  public Set<FieGram> addAll(Set<FieGram> fieGrams) {
    return new HashSet<>(this.fieGramRepo.saveAll(fieGrams));
  }

  public void deleteAll() {
    this.fieGramRepo.deleteAll();
  }

  public FieGram findById(String id) {
    return this.fieGramRepo.getOne(id);
  }

  public Set<FieGram> findAll() {
    return new HashSet<>(this.fieGramRepo.findAll());
  }
}
