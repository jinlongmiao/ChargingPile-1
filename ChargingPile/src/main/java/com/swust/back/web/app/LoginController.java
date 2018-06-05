package com.swust.back.web.app;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.swust.back.model.User;
import com.swust.back.service.app.LoginService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.constants.Constants;
import com.swust.back.util.constants.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录相关Controller
 */

@RestController
@RequestMapping("/login")
public class LoginController {
    static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private Map<String,String> captchaMap = new HashMap<>();

    @Resource
    private LoginService loginService;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    /**
     * 登陆
     * @param user
     * @return
     */
    @PostMapping(value = "/auth")
    public JSONObject login(@RequestBody User user, HttpServletRequest request){
        String captcha = captchaMap.get("vrifyCode");
        // 判断验证码是否正确
        if( captcha.equals(user.getCaptcha()) ){
            return loginService.login(user);
        }
        else {
            return CommonUtil.errorJson(ErrorEnum.E_90001);
        }


    }

    /**
     * 查询当前登录用户的信息
     * @return
     */
    @PostMapping("/getInfo")
    public JSONObject getInfo(HttpServletRequest request){

        HttpSession session = request.getSession();
        User userInfo = (User) session.getAttribute(Constants.SESSION_USER_INFO);
        if(userInfo == null){
            return CommonUtil.errorJson(ErrorEnum.E_20011);
        }
        else{
            return loginService.getInfo();
        }
    }


    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public JSONObject logout() {
        return loginService.logout();
    }


    /**
     * 生成验证码
     * @param httpServletResponse
     * @throws Exception
     */
    @GetMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到captchaMap中
            String createText = defaultKaptcha.createText();
            logger.info("createText--->" + createText);
            captchaMap.put("vrifyCode",createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
            captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            httpServletResponse.setHeader("Cache-Control", "no-store");
            httpServletResponse.setHeader("Pragma", "no-cache");
            httpServletResponse.setDateHeader("Expires", 0);
            httpServletResponse.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream =
                    httpServletResponse.getOutputStream();
            responseOutputStream.write(captchaChallengeAsJpeg);
            responseOutputStream.flush();
            responseOutputStream.close();

        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


    }
}
