package com.abc12366.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc12366.admin.service.SiteService;
import com.abc12366.common.util.Utils;

@RestController
@RequestMapping("/site")
public class SiteController {
	
	@Autowired
    private SiteService siteService;
	
	@GetMapping("/cmsTest")
	public ResponseEntity cmsTest() {
		return ResponseEntity.ok(Utils.kv("message", "OK"));
	}
	
	@GetMapping("/listSite")
	public ResponseEntity listSite() {
		return ResponseEntity.ok(siteService.listSite());
	}
	
}
