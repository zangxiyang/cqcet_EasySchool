package com.imsle.cqceteasayschool.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDetail {
    private String result;
    private String msg;
    private UserData data;

    @Getter
    @Setter
    @ToString
    public class UserData{
        private String id;
        private String dept_code;
        private String loginname;
        private String name;
        private String user_type;
        private String photo;
        private String login_flag;
        private String deptname;
        private String jwid;
        private String email;
        private String phone;
        private String mobile;
        private String remarks;
        private String disable;
        private String overdue;
        private String locking;
        private String plain;
        private String sortno;
        private String postname;
        private String postcode;
    }
}
