package com.cscn.uranus.fds.fie.domain.controller;

import com.cscn.uranus.fds.fie.domain.entity.FieGram;
import com.cscn.uranus.fds.fie.domain.service.FieGramManager;
import com.google.common.collect.Sets;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/asxgram")
public class FieGramController {

  private final FieGramManager fieGramManager;

  public FieGramController(FieGramManager fieGramManager) {

    this.fieGramManager = fieGramManager;
  }

  @GetMapping(path = "/findById")
  public Result<FieGram> findById(@RequestBody FieGram inputFieGram) {
    Result<FieGram> result = new Result<>();
    FieGram foundFieGram = this.fieGramManager.findById(inputFieGram.getId());
    if (foundFieGram == null) {
      result.setCode(ResultType.UNKNOWN_ERROR.getCode());
      result.setMessage(ResultType.UNKNOWN_ERROR.getMessage());
      return result;
    } else {
      result.setCode(ResultType.SUCCESS.getCode());
      result.setMessage(ResultType.SUCCESS.getMessage());
      result.setData(foundFieGram);
      return result;
    }

  }

  @PostMapping(path = "/add")
  public Result<FieGram> add(@RequestBody FieGram fieGram) {
    Set<FieGram> asxgramsPersisted = this.fieGramManager.findAll();
    if (asxgramsPersisted.contains(fieGram)) {
      Result<FieGram> result = new Result<>();
      result.setCode(ResultType.SUCCESS.getCode());
      result.setMessage(ResultType.SUCCESS.getMessage());
      result.setDescription("已经存在相同的报文,共添加0条记录!");
      return result;
    } else {
      this.fieGramManager.add(fieGram);
      Result<FieGram> result = new Result<>();
      result.setCode(ResultType.SUCCESS.getCode());
      result.setMessage(ResultType.SUCCESS.getMessage());
      result.setDescription("添加成功");
      return result;
    }
  }

  @PostMapping(path = "/addAll")
  public Result<FieGram> addAll(@RequestBody Set<FieGram> fieGrams) {
    Set<FieGram> asxgramsPersisted = this.fieGramManager.findAll();
    Set<FieGram> asxgramsOnlyInPost = Sets.difference(fieGrams,asxgramsPersisted);
    int count = this.fieGramManager.addAll(asxgramsOnlyInPost).size();
    Result<FieGram> result = new Result<>();
    result.setCode(ResultType.SUCCESS.getCode());
    result.setMessage(ResultType.SUCCESS.getMessage());
    result.setDescription("添加成功,一共添加了" + count + "条记录");
    return result;
  }

  @PostMapping(path = "/clear")
  public @ResponseBody
  Result<FieGram> clear() {
    this.fieGramManager.deleteAll();
    Result<FieGram> result = new Result<>();
    result.setCode(ResultType.SUCCESS.getCode());
    result.setMessage(ResultType.SUCCESS.getMessage());
    return result;

  }
}
