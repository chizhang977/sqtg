package com.justin.sqtg.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.sys.mapper.WareMapper;
import com.justin.sqtg.sys.service.WareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.ssyx.model.sys.Region;
import com.justin.ssyx.model.sys.Ware;
import com.justin.ssyx.vo.sys.RegionVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
@Service
public class WareServiceImpl extends ServiceImpl<WareMapper, Ware> implements WareService {


    ////条件带分页
    @Override
    public IPage<Ware> selectPage(Page<Ware> pageParam, Ware ware) {
        String name = ware.getName();
        LambdaQueryWrapper<Ware> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            wrapper.like(Ware::getName,name);
        }
        Page<Ware> warePage = baseMapper.selectPage(pageParam, wrapper);

        return warePage;
    }
}
