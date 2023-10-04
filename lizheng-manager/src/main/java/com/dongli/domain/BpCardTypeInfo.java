/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.dongli.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author DongLi
* @date 2023-10-04
**/
@Entity
@Data
@Table(name="bp_card_type_info")
public class BpCardTypeInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "卡种信息ID")
    private Integer id;

    @Column(name = "`card_name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "卡种名称")
    private String cardName;

    @Column(name = "`initial_card_count`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "初始卡的次数")
    private Integer initialCardCount;

    @Column(name = "`initial_card_balance`")
    @ApiModelProperty(value = "初始卡的余额")
    private BigDecimal initialCardBalance;

    public void copy(BpCardTypeInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
