package com.vinjcent.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author vinjcent
 * @description 计算数据
 * @since 2023/4/3 17:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObject {


    private BigDecimal num1;

    private BigDecimal num2;
}
