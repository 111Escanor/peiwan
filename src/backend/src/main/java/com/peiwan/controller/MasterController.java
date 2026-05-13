package com.peiwan.controller;

import com.peiwan.dto.Result;
import com.peiwan.entity.Master;
import com.peiwan.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master")
@CrossOrigin
public class MasterController {

    @Autowired
    private MasterService masterService;

    @GetMapping("/list")
    public Result<List<Master>> getMasterList() {
        return Result.success(masterService.getApprovedMasters());
    }

    @GetMapping("/detail/{userId}")
    public Result<Master> getMasterDetail(@PathVariable Long userId) {
        Master master = masterService.getMasterByUserId(userId);
        return Result.success(master);
    }

    // 陪玩师申请入驻（注册时已做，此接口为补充）
    @PostMapping("/apply")
    public Result<String> applyMaster(@RequestBody Master master) {
        masterService.applyForMaster(master);
        return Result.success("申请已提交，等待审核");
    }
}