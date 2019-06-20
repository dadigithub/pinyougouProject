package com.pinyougou.mapper;

import com.pinyougou.pojo.TbCities;
import com.pinyougou.pojo.TbCitiesExample;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface TbCitiesMapper   {
    int countByExample(TbCitiesExample example);

    int deleteByExample(TbCitiesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCities record);

    int insertSelective(TbCities record);

    List<TbCities> selectByExample(TbCitiesExample example);

    TbCities selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCities record, @Param("example") TbCitiesExample example);

    int updateByExample(@Param("record") TbCities record, @Param("example") TbCitiesExample example);

    int updateByPrimaryKeySelective(TbCities record);

    int updateByPrimaryKey(TbCities record);
}