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

import com.dongli.domain.BpCardTypeInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.dongli.repository.BpCardTypeInfoRepository;
import com.dongli.service.BpCardTypeInfoService;
import com.dongli.service.dto.BpCardTypeInfoDto;
import com.dongli.service.dto.BpCardTypeInfoQueryCriteria;
import com.dongli.service.mapstruct.BpCardTypeInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author DongLi
* @date 2023-10-04
**/
@Service
@RequiredArgsConstructor
public class BpCardTypeInfoServiceImpl implements BpCardTypeInfoService {

    private final BpCardTypeInfoRepository bpCardTypeInfoRepository;
    private final BpCardTypeInfoMapper bpCardTypeInfoMapper;

    @Override
    public PageResult<BpCardTypeInfoDto> queryAll(BpCardTypeInfoQueryCriteria criteria, Pageable pageable){
        Page<BpCardTypeInfo> page = bpCardTypeInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bpCardTypeInfoMapper::toDto));
    }

    @Override
    public List<BpCardTypeInfoDto> queryAll(BpCardTypeInfoQueryCriteria criteria){
        return bpCardTypeInfoMapper.toDto(bpCardTypeInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BpCardTypeInfoDto findById(Integer id) {
        BpCardTypeInfo bpCardTypeInfo = bpCardTypeInfoRepository.findById(id).orElseGet(BpCardTypeInfo::new);
        ValidationUtil.isNull(bpCardTypeInfo.getId(),"BpCardTypeInfo","id",id);
        return bpCardTypeInfoMapper.toDto(bpCardTypeInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(BpCardTypeInfo resources) {
        bpCardTypeInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BpCardTypeInfo resources) {
        BpCardTypeInfo bpCardTypeInfo = bpCardTypeInfoRepository.findById(resources.getId()).orElseGet(BpCardTypeInfo::new);
        ValidationUtil.isNull( bpCardTypeInfo.getId(),"BpCardTypeInfo","id",resources.getId());
        bpCardTypeInfo.copy(resources);
        bpCardTypeInfoRepository.save(bpCardTypeInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            bpCardTypeInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BpCardTypeInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BpCardTypeInfoDto bpCardTypeInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("卡种名称", bpCardTypeInfo.getCardName());
            map.put("初始卡的次数", bpCardTypeInfo.getInitialCardCount());
            map.put("初始卡的余额", bpCardTypeInfo.getInitialCardBalance());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}