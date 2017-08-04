package com.abc12366.bangbang.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
public class KnowledgeRel  {

	/****/
	private String id;

	/**bb_knowledge_base表的ID**/
	private String knowledgeId;

	/**关联的bb_knowledge_base表的ID**/
	private String relKnowledgeId;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setKnowledgeId(String knowledgeId){
		this.knowledgeId = knowledgeId;
	}

	public String getKnowledgeId(){
		return this.knowledgeId;
	}

	public void setRelKnowledgeId(String relKnowledgeId){
		this.relKnowledgeId = relKnowledgeId;
	}

	public String getRelKnowledgeId(){
		return this.relKnowledgeId;
	}

}
