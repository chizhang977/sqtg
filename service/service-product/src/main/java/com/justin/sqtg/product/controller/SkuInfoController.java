package com.justin.sqtg.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.product.service.SkuInfoService;
import com.justin.ssyx.model.product.SkuInfo;
import com.justin.ssyx.vo.product.SkuInfoQueryVo;
import com.justin.ssyx.vo.product.SkuInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
@RestController
@RequestMapping("/admin/product/skuInfo")
@CrossOrigin
@Api(tags = "sku接口")
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @ApiOperation("sku列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, SkuInfoQueryVo skuInfoQueryVo){
        Page<SkuInfo> pageParam = new Page<>(page,limit);
        IPage<SkuInfo> pageInfo = skuInfoService.selectPage(pageParam,skuInfoQueryVo);
        return Result.ok(pageInfo);
    }

    @ApiOperation("添加商品sku")
    @PostMapping("save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo){
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation("获取商品sku")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation("修改")
    @PutMapping("update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo){
        skuInfoService.udpateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("商品上架/下架")
    @GetMapping("publish/{id}/{status}")
    public Result publish(@PathVariable Long id, @PathVariable Integer status){
        skuInfoService.publish(id,status);
        return Result.ok(null);
    }
    @ApiOperation("商品审核")
    @GetMapping("check/{id}/{status}")
    public Result check(@PathVariable Long id, @PathVariable Integer status){
        skuInfoService.check(id,status);
        return Result.ok(null);
    }

   @ApiOperation("新人专享")
   @GetMapping("isNewPerson/{id}/{status}")
   public Result isNewPerson(@PathVariable Long id, @PathVariable Integer status){
        skuInfoService.isNewPerson(id,status);
        return Result.ok(null);
   }


}

