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
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author DongLi
* @date 2023-10-03
**/
@Entity
@Data
@Table(name="bp_parental_info")
public class BpParentalInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "家长信息ID")
    private Integer id;

    @Column(name = "`name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "家长姓名")
    private String name;

    @Column(name = "`role`")
    @ApiModelProperty(value = "家长角色（爸爸、妈妈、爷爷、奶奶、姥姥、姥爷、其他或自定义）")
    private String role;

    @Column(name = "`gender`")
    @ApiModelProperty(value = "家长性别")
    private String gender;

    @Column(name = "`phone`",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "家长手机号（唯一标识）")
    private String phone;

    @Column(name = "`address`")
    @ApiModelProperty(value = "家庭地址")
    private String address;

    @Column(name = "`email`")
    @ApiModelProperty(value = "家长邮箱")
    private String email;

    public void copy(BpParentalInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
