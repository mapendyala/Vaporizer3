package com.force.example.fulfillment.order.model;


import java.util.Set;

public class MappingModel {
	public String siebleBaseTable;
	public String siebleBaseTableColumn;
	public String sfdcObjectName;
	public String mappingSfdcId;
	public String foreignFieldMapping;
	public String sfdcFieldTable;
	public int mappingSeq;
	public String id;
	public String sblFieldNmdropdown;
//	public String sbldscription;
	public String joinNamerow;
	public String frgnKeyrow;
	public String clmnNmrow;
	public String slfrcdropdown;
//	public String slsfrcdscription;
	public String joinCondition;
	public String siebelEntity;
	public Boolean checkFlag ;
	public Boolean lookUpFlag;
	public String lookUpObject;
	public String lookUpRelationShipName;
	public String lookUpExternalId;
	public String sfdcRowId;
	public Set<String> lstExternalIds;
	
	public Set<String> getLstExternalIds() {
		return lstExternalIds;
	}
	public void setLstExternalIds(Set<String> lstExternalIds) {
		this.lstExternalIds = lstExternalIds;
	}
	public String getSfdcRowId() {
		return sfdcRowId;
	}
	public void setSfdcRowId(String sfdcRowId) {
		this.sfdcRowId = sfdcRowId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Boolean getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(Boolean checkFlag) {
		this.checkFlag = checkFlag;
	}
	public int getMappingSeq() {
		return mappingSeq;
	}
	public void setMappingSeq(int mappingSeq) {
		this.mappingSeq = mappingSeq;
	}
	public String getForeignFieldMapping() {
		return foreignFieldMapping;
	}
	public void setForeignFieldMapping(String foreignFieldMapping) {
		this.foreignFieldMapping = foreignFieldMapping;
	}
	public String getSiebleBaseTable() {
		return siebleBaseTable;
	}
	public void setSiebleBaseTable(String siebleBaseTable) {
		this.siebleBaseTable = siebleBaseTable;
	}
	public String getSiebleBaseTableColumn() {
		return siebleBaseTableColumn;
	}
	public void setSiebleBaseTableColumn(String siebleBaseTableColumn) {
		this.siebleBaseTableColumn = siebleBaseTableColumn;
	}
	public String getSfdcObjectName() {
		return sfdcObjectName;
	}
	public void setSfdcObjectName(String sfdcObjectName) {
		this.sfdcObjectName = sfdcObjectName;
	}
	public String getSfdcFieldTable() {
		return sfdcFieldTable;
	}
	public void setSfdcFieldTable(String sfdcFieldTable) {
		this.sfdcFieldTable = sfdcFieldTable;
	}
	public String getMappingSfdcId() {
		return mappingSfdcId;
	}
	public void setMappingSfdcId(String mappingSfdcId) {
		this.mappingSfdcId = mappingSfdcId;
	}
	public String getSblFieldNmdropdown() {
		return sblFieldNmdropdown;
	}
	public void setSblFieldNmdropdown(String sblFieldNmdropdown) {
		this.sblFieldNmdropdown = sblFieldNmdropdown;
	}
	/*public String getSbldscription() {
		return sbldscription;
	}
	public void setSbldscription(String sbldscription) {
		this.sbldscription = sbldscription;
	}*/
	public String getFrgnKeyrow() {
		return frgnKeyrow;
	}
	public void setFrgnKeyrow(String frgnKeyrow) {
		this.frgnKeyrow = frgnKeyrow;
	}
	public String getClmnNmrow() {
		return clmnNmrow;
	}
	public void setClmnNmrow(String clmnNmrow) {
		this.clmnNmrow = clmnNmrow;
	}
	public String getSlfrcdropdown() {
		return slfrcdropdown;
	}
	public void setSlfrcdropdown(String slfrcdropdown) {
		this.slfrcdropdown = slfrcdropdown;
	}
	/*public String getSlsfrcdscription() {
		return slsfrcdscription;
	}
	public void setSlsfrcdscription(String slsfrcdscription) {
		this.slsfrcdscription = slsfrcdscription;
	}*/
	public String getJoinCondition() {
		return joinCondition;
	}
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}
	public String getSiebelEntity() {
		return siebelEntity;
	}
	public void setSiebelEntity(String siebelEntity) {
		this.siebelEntity = siebelEntity;
	}
	public Boolean getLookUpFlag() {
		return lookUpFlag;
	}
	public void setLookUpFlag(Boolean lookUpFlag) {
		this.lookUpFlag = lookUpFlag;
	}
	public String getLookUpObject() {
		return lookUpObject;
	}
	public void setLookUpObject(String lookUpObject) {
		this.lookUpObject = lookUpObject;
	}
	public String getJoinNamerow() {
		return joinNamerow;
	}
	public void setJoinNamerow(String joinNamerow) {
		this.joinNamerow = joinNamerow;
	}
	public String getLookUpRelationShipName() {
		return lookUpRelationShipName;
	}
	public void setLookUpRelationShipName(String lookUpRelationShipName) {
		this.lookUpRelationShipName = lookUpRelationShipName;
	}
	public String getLookUpExternalId() {
		return lookUpExternalId;
	}
	public void setLookUpExternalId(String lookUpExternalId) {
		this.lookUpExternalId = lookUpExternalId;
	}
}
