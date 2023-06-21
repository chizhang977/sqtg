package com.justin.sqtg.sys.controller;


import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.sys.service.RegionService;
import com.justin.sqtg.sys.service.RegionWareService;
import com.justin.ssyx.model.sys.Region;
import com.justin.ssyx.model.sys.RegionWare;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
@Api(tags = "地区接口")
@RestController
@RequestMapping("/admin/sys/region")
@CrossOrigin
public class RegionController {

    @Autowired
    private RegionService regionService;


    @ApiOperation("根据关键字获取地区")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegion(@PathVariable String keyword){
        List<Region> regionList  =  regionService.findRegion(keyword);
        return Result.ok(regionList);
    }
    @ApiOperation("根据上级id获取地区")
    @GetMapping("findByParentId/{parentId}")
    public Result findRegion(@PathVariable Long parentId){
        List<Region> regionList  =  regionService.findRegionByParentId(parentId);
        return Result.ok(regionList);
    }



}

