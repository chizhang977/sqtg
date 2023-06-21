package com.justin.sqtg.product.controller;


import com.justin.sqtg.common.exception.SsyxException;
import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.common.result.ResultCodeEnum;
import com.justin.sqtg.product.service.AttrService;
import com.justin.ssyx.model.product.Attr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
@Api(tags = "属性接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @ApiOperation("根据属性分组id来获取属性的列表")
    @GetMapping("{groupId}")
    public Result getAttrListByGroupId(@PathVariable Long groupId){
      List<Attr> attrList =   attrService.getAttrList(groupId);
      if (attrList.isEmpty()){
          throw new SsyxException(ResultCodeEnum.DATA_ERROR);
      }
      return Result.ok(attrList);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok(null);
    }


}

