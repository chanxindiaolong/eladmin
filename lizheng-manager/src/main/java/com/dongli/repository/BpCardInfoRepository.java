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
package com.dongli.repository;

import com.dongli.domain.BpCardInfo;
import com.dongli.service.dto.BpCardInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * @author DongLi
 * @website https://eladmin.vip
 * @date 2023-10-04
 **/
public interface BpCardInfoRepository extends JpaRepository<BpCardInfo, Integer>, JpaSpecificationExecutor<BpCardInfo> {
    //根据用户名或用户手机号查询卡消费信息
    @Query(value = "select new com.dongli.service.dto.BpCardInfoDto(bci.id,bci.remainingCount,bci.createTime,bci.updateTime,bcti.id,bcti.cardName,bpi.id,bpi.name,bpi.phone) from BpCardInfo bci left join BpParentalInfo bpi on bci.userId = bpi.id left join BpCardTypeInfo bcti on bci.cardTypeId = bcti.id  where bpi.phone like ?1 " + " or bpi.name like ?1 ")
    Page<BpCardInfoDto> findByUserNameOrUserPhone(String keyword, Pageable pageable);
}