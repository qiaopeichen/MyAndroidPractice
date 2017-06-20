// IService.aidl
package com.example.a71_alipay;

// Declare any non-default types here with import statements

interface IService {
    boolean callPay(String username, String pwd, int money);
}
