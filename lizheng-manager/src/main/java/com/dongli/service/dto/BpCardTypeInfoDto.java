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

import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author DongLi
* @date 2023-10-04
**/
@Data
public class BpCardTypeInfoDto implements Serializable {

    /** 卡种信息ID */
    private Integer id;

    /** 卡种名称 */
    private String cardName;

    /** 初始卡的次数 */
    private Integer initialCardCount;

    /** 初始卡的余额 */
    private BigDecimal initialCardBalance;
}