package com.justin.sqtg.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.justin.sqtg.product.mapper.AttrMapper;
import com.justin.sqtg.product.service.AttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.ssyx.model.product.Attr;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Override
    public List<Attr> getAttrList(Long groupId) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getAttrGroupId,groupId);
        List<Attr> attrs = baseMapper.selectList(wrapper);
        return attrs;
    }
}
