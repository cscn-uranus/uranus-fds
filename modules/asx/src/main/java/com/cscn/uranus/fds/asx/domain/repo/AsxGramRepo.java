package com.cscn.uranus.fds.asx.domain.repo;

import com.cscn.uranus.fds.asx.domain.entity.AsxGram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsxGramRepo extends JpaRepository<AsxGram, String> {

}
