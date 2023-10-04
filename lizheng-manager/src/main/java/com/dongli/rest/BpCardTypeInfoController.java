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
package com.dongli.rest;

import me.zhengjie.annotation.Log;
import com.dongli.domain.BpCardTypeInfo;
import com.dongli.service.BpCardTypeInfoService;
import com.dongli.service.dto.BpCardTypeInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;
import com.dongli.service.dto.BpCardTypeInfoDto;

/**
* @website https://eladmin.vip
* @author DongLi
* @date 2023-10-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "卡种信息管理")
@RequestMapping("/api/bpCardTypeInfo")
public class BpCardTypeInfoController {

    private final BpCardTypeInfoService bpCardTypeInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bpCardTypeInfo:list')")
    public void exportBpCardTypeInfo(HttpServletResponse response, BpCardTypeInfoQueryCriteria criteria) throws IOException {
        bpCardTypeInfoService.download(bpCardTypeInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询卡种信息")
    @ApiOperation("查询卡种信息")
    @PreAuthorize("@el.check('bpCardTypeInfo:list')")
    public ResponseEntity<PageResult<BpCardTypeInfoDto>> queryBpCardTypeInfo(BpCardTypeInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bpCardTypeInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增卡种信息")
    @ApiOperation("新增卡种信息")
    @PreAuthorize("@el.check('bpCardTypeInfo:add')")
    public ResponseEntity<Object> createBpCardTypeInfo(@Validated @RequestBody BpCardTypeInfo resources){
        bpCardTypeInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改卡种信息")
    @ApiOperation("修改卡种信息")
    @PreAuthorize("@el.check('bpCardTypeInfo:edit')")
    public ResponseEntity<Object> updateBpCardTypeInfo(@Validated @RequestBody BpCardTypeInfo resources){
        bpCardTypeInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除卡种信息")
    @ApiOperation("删除卡种信息")
    @PreAuthorize("@el.check('bpCardTypeInfo:del')")
    public ResponseEntity<Object> deleteBpCardTypeInfo(@RequestBody Integer[] ids) {
        bpCardTypeInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}