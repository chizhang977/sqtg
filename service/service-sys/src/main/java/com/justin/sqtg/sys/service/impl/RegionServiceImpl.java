package com.justin.sqtg.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.justin.sqtg.sys.mapper.RegionMapper;
import com.justin.sqtg.sys.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.ssyx.model.sys.Region;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    //获取地区根据关键字
    @Override
    public List<Region> findRegion(String name) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Region::getName,name);
        List<Region> regions = baseMapper.selectList(wrapper);
        return regions;
    }

    @Override
    public List<Region> findRegionByParentId(Long parentId) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Region::getParentId,parentId);
        List<Region> regions = baseMapper.selectList(wrapper);
        return regions;
    }
}
