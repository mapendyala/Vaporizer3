package com.force.example.fulfillment.order.model;

import java.util.ArrayList;
import java.util.List;

public class MultiValMappingModel {
	
	private String id;
	private String siebelField;
	private String relationType;
	private String childEntity;
	private String childTable;
	public String getChildTable() {
		return childTable;
	}
	public void setChildTable(String childTable) {
		this.childTable = childTable;
	}
	private String childField;
	private String interTable;
	private String interParentColumn;
	private String interChildColumn;
	private String joinCondition;
	private String SFDCFieldName;
	private String junctionObject;
	private String junctionObjectChildField;
	private String sfdcFieldDescription;
	private String lovMapping;
	
	private String sfdcChildObject;
	private String lookupField;
	private String lookupRelationName;
	private String lookupExternalId;
	private String junctionObjParentField;
	private String parentRelationName;
	private String parentExternalId;
	private String juncObjChildField;
	private String childRelationName;
	private String childExternalId;
	public String sfdcRowId;
	List<MappingSFDC> lookupObjList = new ArrayList<MappingSFDC>();//prtnrWSDL1.getLookupObjFieldList((String)sfdcObjectName);
	List<MappingSFDC> jnObjParentList =new ArrayList<MappingSFDC>();// prtnrWSDL1.getJnObjParentFieldList((String)sfdcObjectName);
	List<MappingSFDC> jnObjChildList = new ArrayList<MappingSFDC>();
	
	public List<MappingSFDC> getLookupObjList() {
		return lookupObjList;
	}
	public void setLookupObjList(List<MappingSFDC> lookupObjList) {
		this.lookupObjList = lookupObjList;
	}
	public List<MappingSFDC> getJnObjParentList() {
		return jnObjParentList;
	}
	public void setJnObjParentList(List<MappingSFDC> jnObjParentList) {
		this.jnObjParentList = jnObjParentList;
	}
	public List<MappingSFDC> getJnObjChildList() {
		return jnObjChildList;
	}
	public void setJnObjChildList(List<MappingSFDC> jnObjChildList) {
		this.jnObjChildList = jnObjChildList;
	}
	public String getSfdcRowId() {
		return sfdcRowId;
	}
	public void setSfdcRowId(String sfdcRowId) {
		this.sfdcRowId = sfdcRowId;
	}
	public String getSfdcChildObject() {
		return sfdcChildObject;
	}
	public void setSfdcChildObject(String sfdcChildObject) {
		this.sfdcChildObject = sfdcChildObject;
	}
	public String getLookupField() {
		return lookupField;
	}
	public void setLookupField(String lookupField) {
		this.lookupField = lookupField;
	}
	public String getLookupRelationName() {
		return lookupRelationName;
	}
	public void setLookupRelationName(String lookupRelationName) {
		this.lookupRelationName = lookupRelationName;
	}
	public String getLookupExternalId() {
		return lookupExternalId;
	}
	public void setLookupExternalId(String lookupExternalId) {
		this.lookupExternalId = lookupExternalId;
	}
	public String getJunctionObjParentField() {
		return junctionObjParentField;
	}
	public void setJunctionObjParentField(String junctionObjParentField) {
		this.junctionObjParentField = junctionObjParentField;
	}
	public String getParentRelationName() {
		return parentRelationName;
	}
	public void setParentRelationName(String parentRelationName) {
		this.parentRelationName = parentRelationName;
	}
	public String getParentExternalId() {
		return parentExternalId;
	}
	public void setParentExternalId(String parentExternalId) {
		this.parentExternalId = parentExternalId;
	}
	public String getJuncObjChildField() {
		return juncObjChildField;
	}
	public void setJuncObjChildField(String juncObjChildField) {
		this.juncObjChildField = juncObjChildField;
	}
	public String getChildRelationName() {
		return childRelationName;
	}
	public void setChildRelationName(String childRelationName) {
		this.childRelationName = childRelationName;
	}
	public String getChildExternalId() {
		return childExternalId;
	}
	public void setChildExternalId(String childExternalId) {
		this.childExternalId = childExternalId;
	}
	public String getSiebleBaseTable() {
		return siebleBaseTable;
	}
	public void setSiebleBaseTable(String siebleBaseTable) {
		this.siebleBaseTable = siebleBaseTable;
	}
	public String getSfdcObjectName() {
		return sfdcObjectName;
	}
	public void setSfdcObjectName(String sfdcObjectName) {
		this.sfdcObjectName = sfdcObjectName;
	}
	public int mappingSeq;
	public Boolean checkFlag ;
	
	private String siebleBaseTable;
	private String sfdcObjectName;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSiebelField() {
		return siebelField;
	}
	public void setSiebelField(String siebelField) {
		this.siebelField = siebelField;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getChildEntity() {
		return childEntity;
	}
	public void setChildEntity(String childEntity) {
		this.childEntity = childEntity;
	}
	public String getChildField() {
		return childField;
	}
	public void setChildField(String childField) {
		this.childField = childField;
	}
	public String getInterTable() {
		return interTable;
	}
	public void setInterTable(String interTable) {
		this.interTable = interTable;
	}
	public String getInterParentColumn() {
		return interParentColumn;
	}
	public void setInterParentColumn(String interParentColumn) {
		this.interParentColumn = interParentColumn;
	}
	public String getInterChildColumn() {
		return interChildColumn;
	}
	public void setInterChildColumn(String interChildColumn) {
		this.interChildColumn = interChildColumn;
	}
	public String getJoinCondition() {
		return joinCondition;
	}
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}
	public String getSFDCFieldName() {
		return SFDCFieldName;
	}
	public void setSFDCFieldName(String sFDCFieldName) {
		SFDCFieldName = sFDCFieldName;
	}
	public String getJunctionObject() {
		return junctionObject;
	}
	public void setJunctionObject(String junctionObject) {
		this.junctionObject = junctionObject;
	}
	public String getJunctionObjectChildField() {
		return junctionObjectChildField;
	}
	public void setJunctionObjectChildField(String junctionObjectChildField) {
		this.junctionObjectChildField = junctionObjectChildField;
	}
	public String getSfdcFieldDescription() {
		return sfdcFieldDescription;
	}
	public void setSfdcFieldDescription(String sfdcFieldDescription) {
		this.sfdcFieldDescription = sfdcFieldDescription;
	}
	public String getLovMapping() {
		return lovMapping;
	}
	public void setLovMapping(String lovMapping) {
		this.lovMapping = lovMapping;
	}

}
