package com.pinyougou.sellergoods.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.SpecificationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;


	//注入规格参数表TbSpecificationOption的mapper
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;



	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}



	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}



	/**
	 * 增加
	 */
	@Override
	public void add(SpecificationGroup specificationGroup) {
		//获取规格表实体对象
		TbSpecification tbSpecification = specificationGroup.getSpecification();
		specificationMapper.insert(tbSpecification);

		//获取规格选项的集合对象
		List<TbSpecificationOption> specificationOptionList = specificationGroup.getSpecificationOptionList();
		for (TbSpecificationOption option : specificationOptionList) {
			//TbSpecification获取规格实体表中规格名称的id
			option.setSpecId(tbSpecification.getId());//设置规格id
			specificationOptionMapper.insert(option);//新增规格,根据规格表新增的id
		}

	}




	/**
	 * 修改
	 */
	@Override
	public void update(SpecificationGroup specificationGroup){

		//更新规格表实体对象
		TbSpecification tbSpecification = specificationGroup.getSpecification();
		specificationMapper.updateByPrimaryKey(tbSpecification);


		//删除原有的规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		//获取规格实体的对象id,并存入example中
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		//根据example删除规格实体和规格参数
		specificationOptionMapper.deleteByExample(example);


		//获取规格选项的集合对象
		List<TbSpecificationOption> specificationOptionList = specificationGroup.getSpecificationOptionList();
		for (TbSpecificationOption option : specificationOptionList) {
			//tbSpecification获取规格实体表中规格名称的id
			option.setSpecId(tbSpecification.getId());//设置规格id
			specificationOptionMapper.insert(option);//新增规格,根据规格表新增的id
		}
	}	



	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public SpecificationGroup findOne(Long id){
		//创建自定义组合实体类对象
		SpecificationGroup specificationGroup = new SpecificationGroup();
		//1.添加规格实体类到自定义实体类中

		//根据规格实体类id查询规格对象,并添加到自定义实体类中
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		specificationGroup.setSpecification(tbSpecification);

		//2.添加规格参数到自定义实体类中
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);//根据规格id查询
		List<TbSpecificationOption> optionList = specificationOptionMapper.selectByExample(example);
		specificationGroup.setSpecificationOptionList(optionList);

		return specificationGroup;//返回组合实体类对象
	}



	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//1.根据id删除规格表中的数据(规格表)
			specificationMapper.deleteByPrimaryKey(id);

			//2.根据规格表中的id和规格数组表中的id进行比较,如果相等,进行删除(规格数组表)
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);//指定规格id为条件
			specificationOptionMapper.deleteByExample(example);//根据example条件进行删除

		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}



	/**
	 * 返回商品规格下拉列表参数
	 * @return
	 */
	@Override
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}


}
