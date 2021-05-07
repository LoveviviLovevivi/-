package com.atguigu.crowd.handler;

import com.atguigu.crowd.config.OSSproperties;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerHandler {

    @Autowired
    private OSSproperties ossproperties;
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(
            // 接收除上传图片外其他的普通数据
            ProjectVO projectVO,
            // 接收上传的头图
            MultipartFile headerPicture,
            // 接收上传的详情图片
            List<MultipartFile> detailPictureList,
            // 用来将收集了一部分数据的projectVo,存入session域
            HttpSession session,
            // 错误时返回提示消息
            ModelMap modelMap
    ) throws IOException {
        /**
         * 无oss服务器，暂时注释上传图片代码
         */
      /*  // 完成头图上传
        boolean headerPictureIsEmpty = headerPicture.isEmpty();
        if (headerPictureIsEmpty){
            // 如果没有上传则返回错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }
            // 如果上传了内容，则执行上传
            ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                    ossproperties.getEndPoint(),
                    ossproperties.getAccessKeyId(),
                    ossproperties.getAccessKeySecret(),
                    headerPicture.getInputStream(),
                    ossproperties.getBucketName(),
                    ossproperties.getBucketDomain(),
                    headerPicture.getOriginalFilename());
            String result = uploadHeaderPicResultEntity.getResult();
            // 判断头图是否上传成功
            if (ResultEntity.SUCCESS.equals(result)){
                // 从返回的数据中获取图片访问路径
                String headPicturePath = uploadHeaderPicResultEntity.getData();
                // 存入projectVO对象中
                projectVO.setHeaderPicturePath(headPicturePath);
            }else{
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
                return "project-launch";

        }
        List<String> detailPicturePathList = new ArrayList<String>();
        // 检查detailPicturePathList是否有效
        if(detailPictureList == null || detailPictureList.size() == 0){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        // 遍历detailPicturePathList集合
        for (MultipartFile detailPicture: detailPictureList) {
            // 判断是否为空
            if(detailPicture.isEmpty()) {
                // 如果没有上传则返回错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
                // 如果上传了内容，则执行上传
                ResultEntity<String> detailuploadResultEntity = CrowdUtil.uploadFileToOss(
                        ossproperties.getEndPoint(),
                        ossproperties.getAccessKeyId(),
                        ossproperties.getAccessKeySecret(),
                        detailPicture.getInputStream(),
                        ossproperties.getBucketName(),
                        ossproperties.getBucketDomain(),
                        detailPicture.getOriginalFilename());
                // 检查上传结果
                String detailResult = detailuploadResultEntity.getResult();
                if (ResultEntity.SUCCESS.equals(detailResult)) {
                    String detailPicturePath = detailuploadResultEntity.getData();
                    // 收集访问路径
                    detailPicturePathList.add(detailPicturePath);
                }else{
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
                    return "project-launch";

                }
            }

        // 存入到projectVO
        projectVO.setDetailPicturePathList(detailPicturePathList);*/
        // 将projectVO对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        return "redirect:http://localhost/project/return/info/page";
    }
}
