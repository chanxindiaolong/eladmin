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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author DongLi
* @date 2023-10-04
**/
@Entity
@Data
@Table(name="bp_card_info")
public class BpCardInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "卡信息ID")
    private Integer id;

    @Column(name = "`card_type_id`")
    @ApiModelProperty(value = "关联的卡种ID")
    private Integer cardTypeId;

    @Column(name = "`user_id`")
    @ApiModelProperty(value = "关联的用户ID（家长或客户）")
    private Integer userId;

    @Column(name = "`remaining_count`")
    @ApiModelProperty(value = "剩余次数")
    private Integer remainingCount;

    @CreationTimestamp
    @Column(name = "`create_time`")
    @ApiModelProperty(value = "开卡时间")
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "`update_time`")
    @ApiModelProperty(value = "消费时间")
    private Timestamp updateTime;

    public void copy(BpCardInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
