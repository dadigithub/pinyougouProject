package com.pinyougou.service;


import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/**
 * 认证类,spring security需要的认证类
 */
public class UserDetailsServiceImpl implements UserDetailsService {


    //通过构造注入sellerService
    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService=sellerService;
    }


    @Override                            //username:用户在界面登陆输入的用户名
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(" running  UserDetailsServiceImpl...");
        //构建一个角色列表
        ArrayList<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        //得到商家对象
        TbSeller seller = sellerService.findOne(username);
        if (seller != null) {
            //判断商家入驻申请的状态是否已经通过审核
            if (seller.getStatus().equals("1")) {
                //user是spring security为我们提供的一个实现类
                return new User(username, seller.getPassword(), grantedAuths);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }






}
