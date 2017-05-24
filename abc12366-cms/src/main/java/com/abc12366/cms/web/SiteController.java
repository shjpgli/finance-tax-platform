package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.SiteBo;
import com.abc12366.cms.model.bo.SiteListBo;
import com.abc12366.cms.service.SiteService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/site",headers = Constant.VERSION_HEAD + "=1")
public class SiteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SiteController.class);
	@Autowired
    private SiteService siteService;

	@GetMapping
	public ResponseEntity selectList(@RequestParam(value = "pageNum", defaultValue = Constant.pageNum) int pageNum,
									 @RequestParam(value = "pageSize", defaultValue = Constant.pageSize) int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
		List<SiteListBo> dataList = siteService.selectList();
		LOGGER.info("{}", dataList);
		return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
	}
	
	@GetMapping(path = "/{siteId}")
	public ResponseEntity<?> selectOneById(@PathVariable("siteId") String siteId) {
		LOGGER.info("{}", siteId);
		SiteBo siteBo = siteService.selectOneById(siteId);
		LOGGER.info("{}", siteBo);
		return ResponseEntity.ok(siteBo);
	}

	@PostMapping
	public ResponseEntity save(@Valid @RequestBody SiteBo siteBo) {
		LOGGER.info("{}", siteBo);
		siteBo = siteService.save(siteBo);
		LOGGER.info("{}", siteBo);
		return new ResponseEntity<>(siteBo, HttpStatus.OK);
	}

	@PutMapping(path = "/{siteId}")
	public ResponseEntity update(@Valid @RequestBody SiteBo siteBo, @PathVariable("siteId") String siteId) {
		LOGGER.info("{}", siteBo);
		siteBo = siteService.update(siteBo);
		LOGGER.info("{}", siteBo);
		return new ResponseEntity<>(siteBo, HttpStatus.OK);
	}


	
}
