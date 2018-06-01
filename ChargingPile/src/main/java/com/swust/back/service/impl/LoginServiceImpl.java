package com.swust.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.UserMapper;
import com.swust.back.model.User;
import com.swust.back.service.AccessService;
import com.swust.back.service.LoginService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import com.swust.back.util.MD5;
import com.swust.back.util.constants.Constants;
import com.swust.back.web.LoginController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private AccessService accessService;

    static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    public User selectUserByAccount(String account){
        return userMapper.selectUserByAccount(account);
    }

    @Override
    public JSONObject login(User user) {
        String username = user.getAccount();
        String password = user.getPassword();
        password = MD5.MD5(password);
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            return CommonUtil.successJson( this.selectUserByAccount(username) );
        } catch (UnknownAccountException uae) {
            return CommonUtil.errorMsg("There is no user with username of " + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            return CommonUtil.errorMsg("Password for account " + token.getPrincipal() + " was incorrect!");
        } catch (LockedAccountException lae) {
            return CommonUtil.errorMsg("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
        }  catch (AuthenticationException ae) {
            return CommonUtil.errorMsg("Authentication Exception");
        }

    }

    /**
     * 查询当前登录用户的权限等信息
     * @return
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        User userInfo = (User) session.getAttribute(Constants.SESSION_USER_INFO);

        JSONObject returnData = new JSONObject();
        JSONObject userPermission = accessService.getPermissions(userInfo);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        returnData.put("userPermission", userPermission);
        return CommonUtil.successJson(returnData);
    }

    /**
     * 退出登录
     * @return
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }
}
