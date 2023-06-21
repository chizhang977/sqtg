package com.justin.sqtg.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.sys.Region;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
public interface RegionService extends IService<Region> {

    //获取地区根据关键字
    List<Region> findRegion(String name);

    //根据上级id获取地区
    List<Region> findRegionByParentId(Long parentId);
}
