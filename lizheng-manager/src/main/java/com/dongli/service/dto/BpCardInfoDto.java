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
package com.dongli.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Date;

/**
* @website https://eladmin.vip
* @description /
* @author DongLi
* @date 2023-10-04
**/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BpCardInfoDto implements Serializable {
    /** 卡信息ID */
    private Integer id;

    /** 剩余次数 */
    private Integer remainingCount;

    /** 开卡时间 */
    private Date createTime;

    /** 消费时间 */
    private Date updateTime;

    /** 关联的卡种ID */
    private Integer cardTypeId;

    /** 卡种名称 */
    private String cardTypeName;

    /** 关联的用户ID（家长或客户） */
    private Integer userId;

    /** 用户名称 */
    private String userName;

    /**持卡人手机号*/
    private String userPhone;
}