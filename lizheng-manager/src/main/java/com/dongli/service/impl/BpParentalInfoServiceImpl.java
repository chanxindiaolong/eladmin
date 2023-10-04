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

import com.dongli.domain.BpParentalInfo;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.dongli.repository.BpParentalInfoRepository;
import com.dongli.service.BpParentalInfoService;
import com.dongli.service.dto.BpParentalInfoDto;
import com.dongli.service.dto.BpParentalInfoQueryCriteria;
import com.dongli.service.mapstruct.BpParentalInfoMapper;
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
* @date 2023-10-03
**/
@Service
@RequiredArgsConstructor
public class BpParentalInfoServiceImpl implements BpParentalInfoService {

    private final BpParentalInfoRepository bpParentalInfoRepository;
    private final BpParentalInfoMapper bpParentalInfoMapper;

    @Override
    public PageResult<BpParentalInfoDto> queryAll(BpParentalInfoQueryCriteria criteria, Pageable pageable){
        Page<BpParentalInfo> page = bpParentalInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bpParentalInfoMapper::toDto));
    }

    @Override
    public List<BpParentalInfoDto> queryAll(BpParentalInfoQueryCriteria criteria){
        return bpParentalInfoMapper.toDto(bpParentalInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BpParentalInfoDto findById(Integer id) {
        BpParentalInfo bpParentalInfo = bpParentalInfoRepository.findById(id).orElseGet(BpParentalInfo::new);
        ValidationUtil.isNull(bpParentalInfo.getId(),"BpParentalInfo","id",id);
        return bpParentalInfoMapper.toDto(bpParentalInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(BpParentalInfo resources) {
        if(bpParentalInfoRepository.findByPhone(resources.getPhone()) != null){
            throw new EntityExistException(BpParentalInfo.class,"phone",resources.getPhone());
        }
        bpParentalInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BpParentalInfo resources) {
        BpParentalInfo bpParentalInfo = bpParentalInfoRepository.findById(resources.getId()).orElseGet(BpParentalInfo::new);
        ValidationUtil.isNull( bpParentalInfo.getId(),"BpParentalInfo","id",resources.getId());
        BpParentalInfo bpParentalInfo1 = bpParentalInfoRepository.findByPhone(resources.getPhone());
        if(bpParentalInfo1 != null && !bpParentalInfo1.getId().equals(bpParentalInfo.getId())){
            throw new EntityExistException(BpParentalInfo.class,"phone",resources.getPhone());
        }
        bpParentalInfo.copy(resources);

        bpParentalInfoRepository.save(bpParentalInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            bpParentalInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BpParentalInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BpParentalInfoDto bpParentalInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("家长姓名", bpParentalInfo.getName());
            map.put("家长角色（爸爸、妈妈、爷爷、奶奶、姥姥、姥爷、其他或自定义）", bpParentalInfo.getRole());
            map.put("家长性别", bpParentalInfo.getGender());
            map.put("家长手机号（唯一标识）", bpParentalInfo.getPhone());
            map.put("家庭地址", bpParentalInfo.getAddress());
            map.put("家长邮箱", bpParentalInfo.getEmail());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}