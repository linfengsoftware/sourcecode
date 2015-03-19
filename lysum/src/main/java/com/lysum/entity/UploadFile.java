package com.lysum.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "smfile")
public class UploadFile extends IdEntity{
	
	private String fileName ;
	
	private String filePath ;
	
	private Date uploadTime;
	
	private Long size ;
	
	private String orginalName;
	
	private String remark ;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Date getUploadTime() {
		return uploadTime;
	}
	
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getOrginalName() {
		return orginalName;
	}

	public void setOrginalName(String orginalName) {
		this.orginalName = orginalName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
