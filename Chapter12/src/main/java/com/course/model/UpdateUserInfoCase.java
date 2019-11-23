package com.course.model;


import lombok.Data;

@Data
public class UpdateUserInfoCase {
    private int id;
    private String realname;
    private String avatar;
    private String background;
    private int gender;
    private String education;
    private String description;
    private String birthday;
    private String age;
    private String school_age;
    private String province;
    private String city;
    private String area;
    private Boolean expected;
}
