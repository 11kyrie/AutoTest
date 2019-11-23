package com.course.model;

import lombok.Data;
@Data
public class VerifySmsCheckCase {
    private int id;
    private String tel;
    private String type;
    private Boolean expected;
}
