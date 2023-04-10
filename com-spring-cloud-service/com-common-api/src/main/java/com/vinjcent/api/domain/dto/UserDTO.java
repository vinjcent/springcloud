package com.vinjcent.api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author vinjcent
 * 后端简化封装 User
 * DataObject Transfer Object（数据传输对象）,并不在页面上做展示,只是传输用,可以用作前端表单接收
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String username;

    private String password;

    // private boolean rememberMe;
}
