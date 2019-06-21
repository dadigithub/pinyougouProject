package com.pinyougou.sellergoods.service;


import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌接口
 */
public interface BrandService {


    /**
     * 查询品牌的所有信息
     * @return
     */
    public List<TbBrand> findAll();

    /**
     * 品牌分页
     * @param pageNum 当前的页数
     * @param pageSize 每页的记录数
     * @return
     */
    public PageResult findPage(int pageNum,int pageSize);


    /**
     * 添加品牌信息
     * @param brand
     */
    public void add(TbBrand brand);



    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    public TbBrand findOne(Long id);



    /**
     * 修改品牌信息
     * @param brand
     */
    public void update(TbBrand brand);




    /**
     * 根据id删除品牌信息
     * @param ids
     */
    public void delete(Long[] ids);


    /**
     * 模糊查询,并进行分页
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findPage(TbBrand brand,int pageNum,int pageSize);


    /**
     * 返回下拉列表数据
     * @return
     */
    public List<Map> selectOptionList();







}
