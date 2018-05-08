package com.cscn.uranus.fds.fie.job.controller;

import com.cscn.uranus.fds.fie.domain.controller.Result;
import com.cscn.uranus.fds.fie.domain.controller.ResultBuilder;
import com.cscn.uranus.fds.fie.job.entity.FieJob;
import com.cscn.uranus.fds.fie.job.service.FieJobManager;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/job")
public class JobController {

  private final FieJobManager fieJobManager;

  public JobController(FieJobManager fieJobManager) {
    this.fieJobManager = fieJobManager;
  }

  @GetMapping(path = "/getAll")
  public Result<Set<FieJob>> findAll() {
    Set<FieJob> fieJobs = this.fieJobManager.findAll();

    return ResultBuilder.success(fieJobs);
  }
}
