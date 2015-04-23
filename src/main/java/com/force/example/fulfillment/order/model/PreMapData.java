package com.force.example.fulfillment.order.model;

import java.util.Set;



public class PreMapData  {

	/**
	 * @param args
	 */
	
	public String name;
	public String siebelFldName;
	public String sfdcFldName;
	public String sfdcObjName;
	public String mapTypeName;
	public String joinName;
	public String frKeyTblName;
	public String sblColName;
	public String joinCondition;
	public String lookupFldName;
	public String lookupObjName;
	public String lookupRltName;
	public String lookupExtrnlName;
	public Set<String> lstExternalIds;
	public Boolean lookUpFlag;
	
	public Boolean getLookUpFlag() {
		return lookUpFlag;
	}
	public void setLookUpFlag(Boolean lookUpFlag) {
		this.lookUpFlag = lookUpFlag;
	}
	public Set<String> getLstExternalIds() {
		return lstExternalIds;
	}
	public void setLstExternalIds(Set<String> lstExternalIds) {
		this.lstExternalIds = lstExternalIds;
	}
	public String getLookupFldName() {
		return lookupFldName;
	}
	public void setLookupFldName(String lookupFldName) {
		this.lookupFldName = lookupFldName;
	}
	public String getLookupObjName() {
		return lookupObjName;
	}
	public void setLookupObjName(String lookupObjName) {
		this.lookupObjName = lookupObjName;
	}
	public String getLookupRltName() {
		return lookupRltName;
	}
	public void setLookupRltName(String lookupRltName) {
		this.lookupRltName = lookupRltName;
	}
	public String getLookupExtrnlName() {
		return lookupExtrnlName;
	}
	public void setLookupExtrnlName(String lookupExtrnlName) {
		this.lookupExtrnlName = lookupExtrnlName;
	}
	public String getSblColName() {
		return sblColName;
	}
	public void setSblColName(String sblColName) {
		this.sblColName = sblColName;
	}	
	public String getJoinName() {
		return joinName;
	}
	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}
	public String getFrKeyTblName() {
		return frKeyTblName;
	}
	public void setFrKeyTblName(String frKeyTblName) {
		this.frKeyTblName = frKeyTblName;
	}
	public String getJoinCondition() {
		return joinCondition;
	}
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSiebelFldName() {
		return siebelFldName;
	}
	public void setSiebelFldName(String siebelFldName) {
		this.siebelFldName = siebelFldName;
	}
	public String getSfdcFldName() {
		return sfdcFldName;
	}
	public void setSfdcFldName(String sfdcFldName) {
		this.sfdcFldName = sfdcFldName;
	}
	public String getSfdcObjName() {
		return sfdcObjName;
	}
	public void setSfdcObjName(String sfdcObjName) {
		this.sfdcObjName = sfdcObjName;
	}
	public String getMapTypeName() {
		return mapTypeName;
	}
	public void setMapTypeName(String mapTypeName) {
		this.mapTypeName = mapTypeName;
	}
	
}
