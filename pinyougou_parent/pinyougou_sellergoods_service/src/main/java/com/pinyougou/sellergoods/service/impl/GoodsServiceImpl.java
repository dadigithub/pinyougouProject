package com.pinyougou.sellergoods.service.impl;
import java.util.List;

import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbGoodsExample;
import com.pinyougou.pojogroup.GoodsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(GoodsGroup goods) {
		//设置未申请状态为未审核
		goods.getGoods().setAuditStatus("0");
		//1.插入商品基本表的数据SPU
		goodsMapper.insert(goods.getGoods());
		//插入扩展表中的信息
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());//将商品基本表的id交给商品扩展表

		//2.插入商品扩展表的数据SKU
		goodsDescMapper.insert(goods.getGoodsDesc());
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(GoodsGroup goods){
		//设置未申请状态:如果是经过修改的商品,需要重新设置状态
		//因为修改过后的产品也是需要重新审核的,所以一律设置商品的状态为未审核
		goods.getGoods().setAuditStatus("0");
		//保存商品表
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		//保存商品扩展表
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());


	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public GoodsGroup findOne(Long id){
		GoodsGroup goodsGroup = new GoodsGroup();
		//根据id获取商品的基本属性,并添加到自定义的goodsGroup当中
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goodsGroup.setGoods(tbGoods);

		//根据id获取商品的扩展属性,并添加到自定义的goodsGroup当中
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goodsGroup.setGoodsDesc(tbGoodsDesc);

		//返回自定义组合实体类
		return goodsGroup;
	}



	/**
	 * 批量删除,其实是为商品添加标记为"1"
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(goods);
		}		
	}


	/**
	 * 模糊查询+分页
	 * @param goods
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		TbGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();//指定条件为未逻辑删除记录,就是只查询isDelete字段的商品,如果该字段不为空则不查询.
		
		if(goods!=null){			
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
//				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
				//把模糊查询改为精确查询,防止查询错误的产生
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
		}
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}


	/**
	 * 更改状态
	 * @param ids
	 * @param status
	 */
	@Override
	public void updateStatus(Long[] ids, String status) {

		for (Long id : ids) {
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(goods);
		}
	}




}
