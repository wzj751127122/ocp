package com.open.capacity.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.open.capacity.common.exception.service.ServiceException;
import com.open.capacity.common.web.Result;
import com.open.capacity.user.entity.OcpTql;

public interface UserService extends IService<OcpTql> {

    Result deductionAmount(String userId) throws ServiceException;

}
