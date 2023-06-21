package com.justin.sqtg.search.controller;


import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.search.service.SkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    @ApiOperation("商品上架")
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperGoods(@PathVariable Long skuId){
            skuService.upperSku(skuId);
            return Result.ok(null);
    }

    @ApiOperation("商品下架")
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerGoods(@PathVariable Long skuId){
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }


}
