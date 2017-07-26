package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.FjBo;
import com.abc12366.cms.model.bo.FjListBo;
import com.abc12366.cms.util.SFTPUtil;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.jcraft.jsch.ChannelSftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/sftp",headers = Constant.VERSION_HEAD + "=1")
public class SftpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SftpController.class);

	@PostMapping
	public ResponseEntity upload(@Valid @RequestBody FjListBo fjListBo) {
		LOGGER.info("{}", fjListBo);
		SFTPUtil sf = new SFTPUtil();
		String host = "118.118.116.202";
		int port = 22;
		String username = "root";
		String password = "hngs_123";
		String directory = fjListBo.getDirectory();
		List<FjBo> fjBoList = fjListBo.getFjBo();
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		String fileName = "";
		List<Byte> content = null;
		for(FjBo fjBo:fjBoList){
            ChannelSftp sftp=sf.connect(host, port, username, password);
			fileName = fjBo.getFileName();
			content = fjBo.getContent();
			Map<String, String> map = sf.uploadByByte(directory,content,fileName,sftp);
			dataList.add(map);
		}
		LOGGER.info("{}", dataList);
		return ResponseEntity.ok(Utils.kv("dataList", dataList));
	}


	
}
