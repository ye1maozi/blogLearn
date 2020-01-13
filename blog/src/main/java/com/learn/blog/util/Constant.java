package com.learn.blog.util;

public class Constant {

    public static enum ResultCode {
        UAUTH(1000),
        UNLOGIN(10001);

        public final int num;
        ResultCode(int i) {
            this.num = i;
        }
        }
}
