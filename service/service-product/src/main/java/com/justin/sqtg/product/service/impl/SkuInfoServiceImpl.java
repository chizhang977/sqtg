package com.justin.sqtg.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.common.constant.MqConst;
import com.justin.sqtg.common.service.RabbitService;
import com.justin.sqtg.product.mapper.SkuInfoMapper;
import com.justin.sqtg.product.service.SkuAttrValueService;
import com.justin.sqtg.product.service.SkuImageService;
import com.justin.sqtg.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.sqtg.product.service.SkuPosterService;
import com.justin.ssyx.model.product.SkuAttrValue;
import com.justin.ssyx.model.product.SkuImage;
import com.justin.ssyx.model.product.SkuInfo;
import com.justin.ssyx.model.product.SkuPoster;
import com.justin.ssyx.vo.product.SkuInfoQueryVo;
import com.justin.ssyx.vo.product.SkuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private SkuImageService skuImageService;

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    @Autowired
    private SkuPosterService skuPosterService;

    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        String keyword = skuInfoQueryVo.getKeyword();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String skuType = skuInfoQueryVo.getSkuType();
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)){
            wrapper.like(SkuInfo::getSkuName,keyword);
        }
        if (!StringUtils.isEmpty(categoryId)){
            wrapper.like(SkuInfo::getCategoryId,categoryId);
        }
        if (!StringUtils.isEmpty(skuType)){
            wrapper.like(SkuInfo::getSkuType,skuType);
        }
        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    //新增sku
    @Override
    @Transactional
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        //添加sku基本信息
        //skuInfoVo---skuInfo
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo,skuInfo);
        baseMapper.insert(skuInfo);
        //添加海报
        //遍历目的为了获取sku_id
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)){
            skuPosterList.stream().forEach(item -> item.setSkuId(skuInfo.getId()));
        }
        skuPosterService.saveBatch(skuPosterList);
        //添加图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)){
            skuImagesList.stream().forEach(item -> item.setSkuId(skuInfo.getId()));
        }
        skuImageService.saveBatch(skuImagesList);
        //添加平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)){
            skuAttrValueList.stream().forEach(item -> item.setSkuId(skuInfo.getId()));
        }
        skuAttrValueService.saveBatch(skuAttrValueList);
    }

    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        //查询基本信息
        SkuInfo skuInfo = baseMapper.selectById(id);
        //图片
        List<SkuImage> skuImageList = skuImageService.getImageListBySkuId(id);
        //海报
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListBySkuId(id);
        //属性信息
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getAttrValueListBySkuId(id);

        BeanUtils.copyProperties(skuInfo,skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        return skuInfoVo;
    }

    @Override
    @Transactional
    public void udpateSkuInfo(SkuInfoVo skuInfoVo) {
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo,skuInfo);
        //修改sku基本信息
        baseMapper.updateById(skuInfo);
        Long id = skuInfoVo.getId();

        //图片
        LambdaQueryWrapper<SkuImage> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(SkuImage::getSkuId,id);
        skuImageService.remove(wrapper1);

        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)){
            skuImagesList.stream().forEach(item -> item.setSkuId(id));
        }
        skuImageService.saveBatch(skuImagesList);

        //海报
        LambdaQueryWrapper<SkuPoster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuPoster::getSkuId,id);
        skuPosterService.remove(wrapper);

        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)){
            skuPosterList.stream().forEach(item -> item.setSkuId(id));
        }
        skuPosterService.saveBatch(skuPosterList);

        //属性
        LambdaQueryWrapper<SkuAttrValue> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(SkuAttrValue::getSkuId,id);
        skuAttrValueService.remove(wrapper2);

        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)){
            skuAttrValueList.stream().forEach(item -> item.setSkuId(id));
        }
        skuAttrValueService.saveBatch(skuAttrValueList);


    }
    //上架
    @Override
    public void publish(Long id, Integer status) {
       if (status == 1){//上架
           SkuInfo skuInfo = baseMapper.selectById(id);
           skuInfo.setPublishStatus(status);
           baseMapper.updateById(skuInfo);
           //TODO 上架发送消息队列同步到es中
           rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT,MqConst.ROUTING_GOODS_UPPER,id);

       }else {  //下架
           SkuInfo skuInfo = baseMapper.selectById(id);
           skuInfo.setPublishStatus(status);
           baseMapper.updateById(skuInfo);
           //TODO
           rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT,MqConst.ROUTING_GOODS_LOWER,id);
       }
    }

    //审核
    @Override
    public void check(Long id, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public void isNewPerson(Long id, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }
}
