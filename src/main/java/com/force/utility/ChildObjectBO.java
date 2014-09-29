package com.force.utility;

public class ChildObjectBO {

	private int seqNum;
	private String baseObjName;
	private String childObjName;
	private String joinCondition;
	
	private boolean  checkFlag;
	
	private String childSfdcId;
	
	
	
	public String getChildSfdcId() {
		return childSfdcId;
	}
	public void setChildSfdcId(String childSfdcId) {
		this.childSfdcId = childSfdcId;
	}
	public boolean isCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	
	
	/**
	 * @return the seqNum
	 */
	public int getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum the seqNum to set
	 */
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * @return the baseObjName
	 */
	public String getBaseObjName() {
		return baseObjName;
	}
	/**
	 * @param baseObjName the baseObjName to set
	 */
	public void setBaseObjName(String baseObjName) {
		this.baseObjName = baseObjName;
	}
	/**
	 * @return the childObjName
	 */
	public String getChildObjName() {
		return childObjName;
	}
	/**
	 * @param childObjName the childObjName to set
	 */
	public void setChildObjName(String childObjName) {
		this.childObjName = childObjName;
	}
	/**
	 * @return the joinCondition
	 */
	public String getJoinCondition() {
		return joinCondition;
	}
	/**
	 * @param joinCondition the joinCondition to set
	 */
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}
	
	
}
