package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.po.MmberLaunchInfoPO;
import com.atguigu.crowd.entity.po.MmberLaunchInfoPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MmberLaunchInfoPOMapper {
    int countByExample(MmberLaunchInfoPOExample example);

    int deleteByExample(MmberLaunchInfoPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmberLaunchInfoPO record);

    int insertSelective(MmberLaunchInfoPO record);

    List<MmberLaunchInfoPO> selectByExample(MmberLaunchInfoPOExample example);

    MmberLaunchInfoPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmberLaunchInfoPO record, @Param("example") MmberLaunchInfoPOExample example);

    int updateByExample(@Param("record") MmberLaunchInfoPO record, @Param("example") MmberLaunchInfoPOExample example);

    int updateByPrimaryKeySelective(MmberLaunchInfoPO record);

    int updateByPrimaryKey(MmberLaunchInfoPO record);
}