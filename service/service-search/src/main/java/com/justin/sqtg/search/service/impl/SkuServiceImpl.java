package com.justin.sqtg.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.justin.sqtg.client.product.ProductFeignClient;
import com.justin.sqtg.search.repository.SkuRepository;
import com.justin.sqtg.search.service.SkuService;
import com.justin.ssyx.enums.SkuType;
import com.justin.ssyx.model.product.Category;
import com.justin.ssyx.model.product.SkuInfo;
import com.justin.ssyx.model.search.SkuEs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuRepository skuRepository;


    @Autowired
    private ProductFeignClient productFeignClient;
    //上架
    @Override
    public void upperSku(Long skuId) {
        SkuEs skuEs = new SkuEs();
        //通过远程调用获取到sku信息和分类的信息
        // service-search---->service-product-client------>servcie-product
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (null == skuInfo) return;
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        if (category != null){
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());

        //普通和秒杀商品
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品

        }
        SkuEs save = skuRepository.save(skuEs);
        log.info("upperSku："+ JSON.toJSONString(save));

    }

    //下架
    @Override
    public void lowerSku(Long skuId) {
        this.skuRepository.deleteById(skuId);
    }
}
