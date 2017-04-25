package com.abc12366.admin.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc12366.common.util.Utils;

@RestController
@RequestMapping("/dict")
public class SiteController {
	@GetMapping("/cmsTest")
	public ResponseEntity cmsTest() {
		return ResponseEntity.ok(Utils.kv("message", "OK"));
	}
}
