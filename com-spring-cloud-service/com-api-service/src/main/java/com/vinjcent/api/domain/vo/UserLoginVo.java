package com.vinjcent.api.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author vinjcent
 * 回传给前端的登陆后的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo implements Serializable {

    private String token;
    private UserInfoVo userInfo;

}
