package com.atguigu.crowd.impl;

import com.atguigu.crowd.api.MemberService;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.mapper.MemberPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        // 创建example对象
        MemberPOExample example = new MemberPOExample();
        // 创建criteria对象
        MemberPOExample.Criteria criteria = example.createCriteria();
        // 封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        if (list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
