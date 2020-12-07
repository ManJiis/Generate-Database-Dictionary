package cn.tlh.ex.controller;

import cn.tlh.ex.service.ExportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: TANG
 * @description: 数据词典导出
 * @date: 2020-12-07
 */
@RestController
@RequestMapping("export")
public class ExportController {
    @Resource
    private ExportService exportService;

}
