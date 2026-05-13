package com.peiwan.service;

import com.peiwan.entity.Master;
import java.util.List;

public interface MasterService {
    List<Master> getApprovedMasters();
    Master getMasterByUserId(Long userId);
    boolean applyForMaster(Master master);
    boolean approveMaster(Long userId, boolean approved);
}