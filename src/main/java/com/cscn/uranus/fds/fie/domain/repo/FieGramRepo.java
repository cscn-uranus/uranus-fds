package com.cscn.uranus.fds.fie.domain.repo;

import com.cscn.uranus.fds.fie.domain.entity.FieGram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieGramRepo extends JpaRepository<FieGram, String> {

}
