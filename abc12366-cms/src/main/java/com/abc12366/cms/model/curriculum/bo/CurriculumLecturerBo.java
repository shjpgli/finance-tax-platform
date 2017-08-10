package com.abc12366.cms.model.curriculum.bo;
import java.io.Serializable;


/**
 * 
 * 讲师表
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumLecturerBo implements Serializable {

	/**讲师ID**varchar(64)**/
	private String lecturerId;

	/**讲师用户ID**varchar(64)**/
	private String userId;

	/**讲师姓名**varchar(30)**/
	private String lecturerName;

	/**讲师简介**longtext**/
	private String intro;

	/**联系电话**varchar(20)**/
	private String phone;

	/**单位**varchar(50)**/
	private String company;

	/**讲师QQ**varchar(20)**/
	private String lecturerQQ;

	/**讲师Email**varchar(30)**/
	private String lecturerEmail;

	/**讲师图片**varchar(200)**/
	private String lecturerPicture;



	public void setLecturerId(String lecturerId){
		this.lecturerId = lecturerId;
	}

	public String getLecturerId(){
		return this.lecturerId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setLecturerName(String lecturerName){
		this.lecturerName = lecturerName;
	}

	public String getLecturerName(){
		return this.lecturerName;
	}

	public void setIntro(String intro){
		this.intro = intro;
	}

	public String getIntro(){
		return this.intro;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return this.company;
	}

	public void setLecturerQQ(String lecturerQQ){
		this.lecturerQQ = lecturerQQ;
	}

	public String getLecturerQQ(){
		return this.lecturerQQ;
	}

	public void setLecturerEmail(String lecturerEmail){
		this.lecturerEmail = lecturerEmail;
	}

	public String getLecturerEmail(){
		return this.lecturerEmail;
	}

	public void setLecturerPicture(String lecturerPicture){
		this.lecturerPicture = lecturerPicture;
	}

	public String getLecturerPicture(){
		return this.lecturerPicture;
	}

}
