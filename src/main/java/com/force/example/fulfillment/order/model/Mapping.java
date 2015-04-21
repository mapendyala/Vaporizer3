package com.force.example.fulfillment.order.model;

import java.util.Comparator;


public class Mapping implements Comparable<Mapping> {
	
	String selectName;
	String id;
	String sblFldName;
	String joinName;
	String frgnKeyName;
	String clmnName;
	String joinCondition;
	String sfdcFldName;
	String lookupFieldName;
	String lookupObjName;
	String lookupRltnName;
	String lookupExtrnlName;
	
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getSblFldName() {
		return sblFldName;
	}
	public void setSblFldName(String sblFldName) {
		this.sblFldName = sblFldName;
	}
	public String getJoinName() {
		return joinName;
	}
	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}
	public String getFrgnKeyName() {
		return frgnKeyName;
	}
	public void setFrgnKeyName(String frgnKeyName) {
		this.frgnKeyName = frgnKeyName;
	}
	public String getClmnName() {
		return clmnName;
	}
	public void setClmnName(String clmnName) {
		this.clmnName = clmnName;
	}
	public String getJoinCondition() {
		return joinCondition;
	}
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}
	public String getSfdcFldName() {
		return sfdcFldName;
	}
	public void setSfdcFldName(String sfdcFldName) {
		this.sfdcFldName = sfdcFldName;
	}
	public String getLookupFieldName() {
		return lookupFieldName;
	}
	public void setLookupFieldName(String lookupFieldName) {
		this.lookupFieldName = lookupFieldName;
	}
	public String getLookupObjName() {
		return lookupObjName;
	}
	public void setLookupObjName(String lookupObjName) {
		this.lookupObjName = lookupObjName;
	}
	public String getLookupRltnName() {
		return lookupRltnName;
	}
	public void setLookupRltnName(String lookupRltnName) {
		this.lookupRltnName = lookupRltnName;
	}
	public String getLookupExtrnlName() {
		return lookupExtrnlName;
	}
	public void setLookupExtrnlName(String lookupExtrnlName) {
		this.lookupExtrnlName = lookupExtrnlName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public static final Comparator<Mapping> SequenceComparator= new Comparator<Mapping>() {
		@Override
		public int compare(Mapping  mapData1, Mapping  mapData2)
		{
			
			if(mapData1.getSblFldName()!=null && mapData2.getSblFldName()!=null){
				return  mapData1.compareTo(mapData2);
			}
			
			
			return 0;
		}
	};

	@Override
	public int compareTo(Mapping o) {
		// TODO Auto-generated method stub
		return Integer.valueOf(this.sblFldName.length())-Integer.valueOf(o.sblFldName.length());
	}
	
}
