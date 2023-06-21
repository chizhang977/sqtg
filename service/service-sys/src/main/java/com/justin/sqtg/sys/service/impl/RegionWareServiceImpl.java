package com.justin.sqtg.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.common.exception.SsyxException;
import com.justin.sqtg.common.result.ResultCodeEnum;
import com.justin.sqtg.sys.mapper.RegionWareMapper;
import com.justin.sqtg.sys.service.RegionWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.ssyx.model.sys.RegionWare;
import com.justin.ssyx.vo.sys.RegionWareQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {


    @Override
    public IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        String keyword = regionWareQueryVo.getKeyword();
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)){
            wrapper.like(RegionWare::getRegionName,keyword)
                    .or().like(RegionWare::getWareName,keyword);
        }
        Page<RegionWare> regionWarePage = baseMapper.selectPage(pageParam, wrapper);
        return regionWarePage;
    }

    //新增
    @Override
    public void saveRegionWare(RegionWare regionWare) {
        Long regionId = regionWare.getRegionId();
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId,regionId);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    //取消开通区域
    @Override
    public void updateRegionWare(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        if (regionWare == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }
}
