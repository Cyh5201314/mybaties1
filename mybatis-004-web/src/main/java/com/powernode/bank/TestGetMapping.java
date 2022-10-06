package com.powernode.bank;

import com.powernode.bank.dao.AccountDao;
import com.powernode.bank.utils.SqlSessionUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

public class TestGetMapping {

    public static void main(String[] args) {
        AccountDao mapper = SqlSessionUtil.openSession().getMapper(AccountDao.class);
        String name = mapper.getClass().getName();
        System.out.println(name);
        Method[] methods = mapper.getClass().getDeclaredMethods();

    }
}
