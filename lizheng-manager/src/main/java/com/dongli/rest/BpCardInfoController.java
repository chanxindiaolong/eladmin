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
import com.dongli.domain.BpCardInfo;
import com.dongli.service.BpCardInfoService;
import com.dongli.service.dto.BpCardInfoQueryCriteria;
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
import com.dongli.service.dto.BpCardInfoDto;

/**
* @website https://eladmin.vip
* @author DongLi
* @date 2023-10-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "卡信息管理管理")
@RequestMapping("/api/bpCardInfo")
public class BpCardInfoController {

    private final BpCardInfoService bpCardInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bpCardInfo:list')")
    public void exportBpCardInfo(HttpServletResponse response, BpCardInfoQueryCriteria criteria) throws IOException {
        bpCardInfoService.download(bpCardInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询卡信息管理")
    @ApiOperation("查询卡信息管理")
    @PreAuthorize("@el.check('bpCardInfo:list')")
    public ResponseEntity<PageResult<BpCardInfoDto>> queryBpCardInfo(BpCardInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bpCardInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增卡信息管理")
    @ApiOperation("新增卡信息管理")
    @PreAuthorize("@el.check('bpCardInfo:add')")
    public ResponseEntity<Object> createBpCardInfo(@Validated @RequestBody BpCardInfo resources){
        bpCardInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改卡信息管理")
    @ApiOperation("修改卡信息管理")
    @PreAuthorize("@el.check('bpCardInfo:edit')")
    public ResponseEntity<Object> updateBpCardInfo(@Validated @RequestBody BpCardInfo resources){
        bpCardInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除卡信息管理")
    @ApiOperation("删除卡信息管理")
    @PreAuthorize("@el.check('bpCardInfo:del')")
    public ResponseEntity<Object> deleteBpCardInfo(@RequestBody Integer[] ids) {
        bpCardInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}