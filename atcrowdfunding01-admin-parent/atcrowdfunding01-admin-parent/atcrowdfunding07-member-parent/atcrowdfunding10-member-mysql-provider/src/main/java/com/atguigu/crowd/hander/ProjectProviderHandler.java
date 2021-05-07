package com.atguigu.crowd.hander;

import com.atguigu.crowd.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectProviderHandler {
    @Autowired
    private ProjectService projectService;
}
