package com.abc12366.uc.model.weixin.bo.template;

import java.io.Serializable;
import java.util.List;

public class FileContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private List<Byte> fileContent;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Byte> getFileContent() {
		return fileContent;
	}

	public void setFileContent(List<Byte> fileContent) {
		this.fileContent = fileContent;
	}

	
}
