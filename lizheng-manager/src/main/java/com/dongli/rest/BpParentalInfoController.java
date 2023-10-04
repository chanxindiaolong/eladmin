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
import com.dongli.domain.BpParentalInfo;
import com.dongli.service.BpParentalInfoService;
import com.dongli.service.dto.BpParentalInfoQueryCriteria;
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
import com.dongli.service.dto.BpParentalInfoDto;

/**
* @website https://eladmin.vip
* @author DongLi
* @date 2023-10-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "家长信息管理管理")
@RequestMapping("/api/bpParentalInfo")
public class BpParentalInfoController {

    private final BpParentalInfoService bpParentalInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bpParentalInfo:list')")
    public void exportBpParentalInfo(HttpServletResponse response, BpParentalInfoQueryCriteria criteria) throws IOException {
        bpParentalInfoService.download(bpParentalInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询家长信息管理")
    @ApiOperation("查询家长信息管理")
    @PreAuthorize("@el.check('bpParentalInfo:list')")
    public ResponseEntity<PageResult<BpParentalInfoDto>> queryBpParentalInfo(BpParentalInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bpParentalInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增家长信息管理")
    @ApiOperation("新增家长信息管理")
    @PreAuthorize("@el.check('bpParentalInfo:add')")
    public ResponseEntity<Object> createBpParentalInfo(@Validated @RequestBody BpParentalInfo resources){
        bpParentalInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改家长信息管理")
    @ApiOperation("修改家长信息管理")
    @PreAuthorize("@el.check('bpParentalInfo:edit')")
    public ResponseEntity<Object> updateBpParentalInfo(@Validated @RequestBody BpParentalInfo resources){
        bpParentalInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除家长信息管理")
    @ApiOperation("删除家长信息管理")
    @PreAuthorize("@el.check('bpParentalInfo:del')")
    public ResponseEntity<Object> deleteBpParentalInfo(@RequestBody Integer[] ids) {
        bpParentalInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}