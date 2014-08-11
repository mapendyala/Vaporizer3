package com.force.example.fulfillment.order.model;

public class MappingModel {
	public String siebleBaseTable;
	public String siebleBaseTableColumn;
	public String sfdcObjectName;
	public String sfdcFieldTable;
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

}
