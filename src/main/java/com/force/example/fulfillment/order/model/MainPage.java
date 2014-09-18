/**
 * 
 */
package com.force.example.fulfillment.order.model;

import java.util.Comparator;

/**
 * @author mapendyala
 *
 */
public class MainPage implements Comparable<MainPage> {

	private String migrate;
	private String sequence;
	private String siebelObject;
	private String primBaseTable;
	private String threshold;
	private String sfdcObject;
	private String status;
	private String rowCount;
	private String rowNo;
	private String pageName;
	private String sfdcId;
	
	public String getSfdcId() {
		return sfdcId;
	}
	public void setSfdcId(String sfdcId) {
		this.sfdcId = sfdcId;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	private String projId;
	
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getRowCount() {
		return rowCount;
	}
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
	public String getMigrate() {
		return migrate;
	}
	public void setMigrate(String migrate) {
		this.migrate = migrate;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getSiebelObject() {
		return siebelObject;
	}
	public void setSiebelObject(String siebelObject) {
		this.siebelObject = siebelObject;
	}
	public String getPrimBaseTable() {
		return primBaseTable;
	}
	public void setPrimBaseTable(String primBaseTable) {
		this.primBaseTable = primBaseTable;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getSfdcObject() {
		return sfdcObject;
	}
	public void setSfdcObject(String sfdcObject) {
		this.sfdcObject = sfdcObject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static final Comparator<MainPage> SequenceComparator= new Comparator<MainPage>() {
		@Override
		public int compare(MainPage  mainPage1, MainPage  mainPage2)
		{
			
			if(mainPage1.getSequence()!=null && mainPage2.getSequence()!=null){
				return  mainPage1.compareTo(mainPage2);
			}
			
			
			return 0;
		}
	};

	@Override
	public int compareTo(MainPage o) {
		// TODO Auto-generated method stub
		return Integer.valueOf(this.sequence)-Integer.valueOf(o.sequence);
	}
	
}
