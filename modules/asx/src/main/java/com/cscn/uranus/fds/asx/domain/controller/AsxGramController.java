package com.cscn.uranus.fds.asx.domain.controller;

import com.cscn.uranus.fds.asx.domain.entity.AsxGram;
import com.cscn.uranus.fds.asx.domain.service.AsxGramManager;
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
public class AsxGramController {

  private final AsxGramManager asxGramManager;

  public AsxGramController(AsxGramManager asxGramManager) {

    this.asxGramManager = asxGramManager;
  }

  @GetMapping(path = "/findById")
  public Result<AsxGram> findById(@RequestBody AsxGram inputAsxGram) {
    Result<AsxGram> result = new Result<>();
    AsxGram foundAsxGram = this.asxGramManager.findById(inputAsxGram.getId());
    if (foundAsxGram == null) {
      result.setCode(ResultType.UNKNOWN_ERROR.getCode());
      result.setMessage(ResultType.UNKNOWN_ERROR.getMessage());
      return result;
    } else {
      result.setCode(ResultType.SUCCESS.getCode());
      result.setMessage(ResultType.SUCCESS.getMessage());
      result.setData(foundAsxGram);
      return result;
    }

  }

  @PostMapping(path = "/add")
  public Result<AsxGram> add(@RequestBody AsxGram asxGram) {
    Set<AsxGram> asxgramsPersisted = this.asxGramManager.findAll();
    if (asxgramsPersisted.contains(asxGram)) {
      Result<AsxGram> result = new Result<>();
      result.setCode(ResultType.SUCCESS.getCode());
      result.setMessage(ResultType.SUCCESS.getMessage());
      result.setDescription("已经存在相同的报文,共添加0条记录!");
      return result;
    } else {
      this.asxGramManager.add(asxGram);
      Result<AsxGram> result = new Result<>();
      result.setCode(ResultType.SUCCESS.getCode());
      result.setMessage(ResultType.SUCCESS.getMessage());
      result.setDescription("添加成功");
      return result;
    }
  }

  @PostMapping(path = "/addAll")
  public Result<AsxGram> addAll(@RequestBody Set<AsxGram> asxGrams) {
    Set<AsxGram> asxgramsPersisted = this.asxGramManager.findAll();
    Set<AsxGram> asxgramsOnlyInPost = Sets.difference(asxGrams,asxgramsPersisted);
    int count = this.asxGramManager.addAll(asxgramsOnlyInPost).size();
    Result<AsxGram> result = new Result<>();
    result.setCode(ResultType.SUCCESS.getCode());
    result.setMessage(ResultType.SUCCESS.getMessage());
    result.setDescription("添加成功,一共添加了" + count + "条记录");
    return result;
  }

  @PostMapping(path = "/clear")
  public @ResponseBody
  Result<AsxGram> clear() {
    this.asxGramManager.deleteAll();
    Result<AsxGram> result = new Result<>();
    result.setCode(ResultType.SUCCESS.getCode());
    result.setMessage(ResultType.SUCCESS.getMessage());
    return result;

  }
}
