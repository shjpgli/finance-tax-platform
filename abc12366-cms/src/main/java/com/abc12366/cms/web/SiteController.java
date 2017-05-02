package com.abc12366.cms.web;

import com.abc12366.cms.service.SiteService;
import com.abc12366.cms.vo.SiteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SiteController.class);
	@Autowired
    private SiteService siteService;
	
	@GetMapping(path = "/selectOneById/{id}")
	public ResponseEntity<?> selectOneById(@PathVariable String id) {
		LOGGER.info("{}", id);
		SiteVO siteVO = siteService.selectOneById(id);
		LOGGER.info("{}", siteVO);
		return ResponseEntity.ok(siteVO);
	}
	@GetMapping
	public ResponseEntity selectList() {
		List<SiteVO> siteList = siteService.selectList();
		LOGGER.info("{}", siteList);
		return ResponseEntity.ok(siteList);
	}

	@PutMapping()
	public ResponseEntity update(@Valid @RequestBody SiteVO siteVO) {
		LOGGER.info("{}", siteVO);
		int count = siteService.update(siteVO);
		LOGGER.info("{}", siteVO);
		return new ResponseEntity<>(siteVO, HttpStatus.OK);
	}


	
}
