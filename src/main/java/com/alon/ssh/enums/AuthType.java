package com.alon.ssh.enums;

/**
 * Created by zhangyl on 2019/4/8
 */
public enum AuthType {
    PASSWORD("1", "用户名/密码"), SSH_KEY("2", "SSH密钥");

    private String code;
    private String text;

    AuthType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static AuthType verify(String code) {
        AuthType[] values = AuthType.values();
        for (AuthType type : values) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static void main(String[] args) {
        System.out.println(AuthType.verify("1"));
        System.out.println(AuthType.PASSWORD.equals(AuthType.verify("1")));

    }
}
