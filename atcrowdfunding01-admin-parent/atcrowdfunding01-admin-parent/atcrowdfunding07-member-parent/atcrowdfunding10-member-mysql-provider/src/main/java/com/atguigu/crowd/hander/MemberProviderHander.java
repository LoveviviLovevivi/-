package com.atguigu.crowd.hander;

import com.atguigu.crowd.api.MemberService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.DuplicateFormatFlagsException;

@RestController
public class MemberProviderHander {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO){
        try {
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            // 如果异常属于DuplicateKeyException，表示账号被占用
            if(e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            // 调用本地service完成查询
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
            // 没有抛异常，返回正常结果
            return ResultEntity.successWithData(memberPO);
        }catch(Exception e){
            e.printStackTrace();
            // 捕获异常，返回失败结果
            return ResultEntity.failed(e.getMessage());
        }
    }
}
