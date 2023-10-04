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
package com.dongli.service.impl;

import com.dongli.domain.BpCardInfo;
import com.dongli.domain.BpCardTypeInfo;
import com.dongli.domain.BpParentalInfo;
import com.dongli.repository.BpCardTypeInfoRepository;
import com.dongli.repository.BpParentalInfoRepository;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import com.dongli.repository.BpCardInfoRepository;
import com.dongli.service.BpCardInfoService;
import com.dongli.service.dto.BpCardInfoDto;
import com.dongli.service.dto.BpCardInfoQueryCriteria;
import com.dongli.service.mapstruct.BpCardInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author DongLi
* @date 2023-10-04
**/
@Service
@RequiredArgsConstructor
public class BpCardInfoServiceImpl implements BpCardInfoService {

    private final BpCardInfoRepository bpCardInfoRepository;
    private final BpCardInfoMapper bpCardInfoMapper;
    private final BpParentalInfoRepository bpParentalInfoRepository;
    private final BpCardTypeInfoRepository bpCardTypeInfoRepository;

    @Override
    public PageResult<BpCardInfoDto> queryAll(BpCardInfoQueryCriteria criteria, Pageable pageable){
        Page<BpCardInfoDto> page = bpCardInfoRepository.findByUserNameOrUserPhone( criteria.getKeyword() == null ? "%" : "%" + criteria.getKeyword() + "%",pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public List<BpCardInfoDto> queryAll(BpCardInfoQueryCriteria criteria){
        return bpCardInfoMapper.toDto(bpCardInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BpCardInfoDto findById(Integer id) {
        BpCardInfo bpCardInfo = bpCardInfoRepository.findById(id).orElseGet(BpCardInfo::new);
        ValidationUtil.isNull(bpCardInfo.getId(),"BpCardInfo","id",id);
        return bpCardInfoMapper.toDto(bpCardInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(BpCardInfo resources) {
        bpCardInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BpCardInfo resources) {
        BpCardInfo bpCardInfo = bpCardInfoRepository.findById(resources.getId()).orElseGet(BpCardInfo::new);
        ValidationUtil.isNull( bpCardInfo.getId(),"BpCardInfo","id",resources.getId());
        bpCardInfo.copy(resources);
        bpCardInfoRepository.save(bpCardInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            bpCardInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BpCardInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BpCardInfoDto bpCardInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("关联的卡种ID", bpCardInfo.getCardTypeId());
            map.put("关联的用户ID（家长或客户）", bpCardInfo.getUserId());
            map.put("剩余次数", bpCardInfo.getRemainingCount());
            map.put("开卡时间", bpCardInfo.getCreateTime());
            map.put("消费时间", bpCardInfo.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}