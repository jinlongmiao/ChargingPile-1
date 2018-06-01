package com.swust.back.dao;


import com.swust.back.model.User;

public interface LoginMapper {
    User selectUser(Integer id);
    User selectUserByAccount(String account);
}
