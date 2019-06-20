package com.dadi.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dadi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName() {
        return "dadi Come on!";
    }
}
