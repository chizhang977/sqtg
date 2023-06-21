package com.justin.sqtg.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.product.Category;
import com.justin.ssyx.vo.product.CategoryQueryVo;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
public interface CategoryService extends IService<Category> {

    IPage<Category> selectPage(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);


    List<Category> findAllList();
}
