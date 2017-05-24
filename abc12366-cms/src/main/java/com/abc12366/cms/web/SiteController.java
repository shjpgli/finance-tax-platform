package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.SiteBo;
import com.abc12366.cms.model.bo.SiteListBo;
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
	
	@GetMapping(path = "/{siteId}")
	public ResponseEntity<?> selectOneById(@PathVariable String siteId) {
		LOGGER.info("{}", siteId);
			SiteBo siteBo = siteService.selectOneById(siteId);
		LOGGER.info("{}", siteBo);
		return ResponseEntity.ok(siteBo);
	}
	@GetMapping
	public ResponseEntity selectList() {
		List<SiteListBo> siteList = siteService.selectList();
		LOGGER.info("{}", siteList);
		return ResponseEntity.ok(siteList);
	}

	@PostMapping
	public ResponseEntity save(@Valid @RequestBody SiteBo siteBo) {
		LOGGER.info("{}", siteBo);
		siteBo = siteService.save(siteBo);
		LOGGER.info("{}", siteBo);
		return new ResponseEntity<>(siteBo, HttpStatus.OK);
	}

	@PutMapping(path = "/{siteId}")
	public ResponseEntity update(@Valid @RequestBody SiteBo siteBo) {
		LOGGER.info("{}", siteBo);
		siteBo = siteService.update(siteBo);
		LOGGER.info("{}", siteBo);
		return new ResponseEntity<>(siteBo, HttpStatus.OK);
	}


	
}
