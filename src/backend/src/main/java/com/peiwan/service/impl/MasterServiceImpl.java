package com.peiwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peiwan.entity.Master;
import com.peiwan.mapper.MasterMapper;
import com.peiwan.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterMapper masterMapper;

    @Override
    public List<Master> getApprovedMasters() {
        LambdaQueryWrapper<Master> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Master::getAuthStatus, "approved");
        return masterMapper.selectList(wrapper);
    }

    @Override
    public Master getMasterByUserId(Long userId) {
        return masterMapper.selectById(userId);
    }

    @Override
    public boolean applyForMaster(Master master) {
        return masterMapper.insert(master) > 0;
    }

    @Override
    public boolean approveMaster(Long userId, boolean approved) {
        Master master = masterMapper.selectById(userId);
        if (master == null) return false;
        master.setAuthStatus(approved ? "approved" : "rejected");
        return masterMapper.updateById(master) > 0;
    }
}