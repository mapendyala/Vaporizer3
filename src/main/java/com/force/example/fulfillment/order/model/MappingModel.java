package com.force.example.fulfillment.order.model;

public class MappingModel {
	public String siebleBaseTable;
	public String siebleBaseTableColumn;
	public String sfdcObjectName;
	public String mappingSfdcId;
	public String foreignFieldMapping;
	public String sfdcFieldTable;
	public int mappingSeq;
	public String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean checkFlag ;
	public boolean isCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(boolean checkFlag) {
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

}
