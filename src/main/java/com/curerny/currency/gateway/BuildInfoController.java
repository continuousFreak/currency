package com.curerny.currency.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    private final BuildInfo buildInfo;

    @Autowired
    public BuildInfoController(BuildInfo buildInfo) {
        this.buildInfo = buildInfo;
    }


    @GetMapping("/build-info")
    @ResponseBody
    public BuildInfo build() {
        return buildInfo;
    }
}
