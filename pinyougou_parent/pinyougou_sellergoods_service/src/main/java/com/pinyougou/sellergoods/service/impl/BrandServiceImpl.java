package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private TbBrandMapper brandMapper;

    /**
     * 查询所有品牌信息
     * @return
     */
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }


    /**
     * 品牌信息分页
     * @param pageNum 当前的页数
     * @param pageSize 每页的记录数
     * @return
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        //返回的集合类为分页插件的Page,之后通过Page分页插件,调取所需的功能
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);

        /*
            将Page插件获取到的total,result,通过get方法封装到自定义的PageResult中,返回给controller为自定义的分页结果集
          为序列化json做准备.
        */
        return new PageResult(page.getTotal(),page.getResult());
    }



    /**
     * 添加品牌信息
     * @param brand
     */
    @Override
    public void add(TbBrand brand) {
        brandMapper.insert(brand);
    }



    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @Override
    public TbBrand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }



    /**修改品牌信息
     * @param brand
     */
    @Override
    public void update(TbBrand brand) {
        //根据数据库中品牌的主键进行修改,主键从前端传入
        brandMapper.updateByPrimaryKey(brand);
    }


    /**
     * 根据id删除品牌信息
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }


    /**
     * 条件查询,分页展示
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        //创建工具类对象,我们根据example创建的criteria,criteria使用java代码的方式拼接的sql语句,就会封装到example当中
        TbBrandExample example = new TbBrandExample();
        //创建example的内部类对象,调取拼接sql语句
        TbBrandExample.Criteria criteria = example.createCriteria();
        if (brand != null) {
            if (brand.getName() != null && brand.getName().length() > 0) {
                //模糊查询品牌名称
                criteria.andNameLike("%" + brand.getName() + "%");
            }

            if (brand.getFirstChar() != null && brand.getFirstChar().length() > 0) {
                //模糊查询品牌首字母
                criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
            }
        }


        //返回的集合类为分页插件的Page,之后通过Page分页插件,调取所需的功能
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);

        /*
            将Page插件获取到的total,result,通过get方法封装到自定义的PageResult中,返回给controller为自定义的分页结果集
          为序列化json做准备.
        */
        return new PageResult(page.getTotal(),page.getResult());

    }




    /**
     * 返回商品下拉列表数据
     * @return
     */
    @Override
    public List<Map> selectOptionList() {
        List<TbBrand> tbBrands = brandMapper.selectByExample(null);
        List<Map> list= new ArrayList<>();
        for (TbBrand tbBrand : tbBrands) {
            Map map=new HashMap();
            map.put("id",tbBrand.getId());
            map.put("text",tbBrand.getName());
            list.add(map);
        }
        return list;
    }






}
