package com.vinjcent.api.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author vinjcent
 * 传递给前端的用户信息
 */
@Data
@Accessors
public class UserInfoVo implements Serializable {

    private Integer id;

    private String nickname;

    private String avatar;

}
