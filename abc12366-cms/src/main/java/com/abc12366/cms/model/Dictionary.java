package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * 字典表
 * 
 **/
@SuppressWarnings("serial")
public class Dictionary implements Serializable {

	/**id**varchar(64)**/
	private String id;

	/**name**varchar(255)**/
	private String name;

	/**value**varchar(255)**/
	private String value;

	/**type**varchar(255)**/
	private String type;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

}
