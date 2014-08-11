package com.force.example.fulfillment.order.model;

public class VaporizerData {

	private int sequence;
	private String siebelObject;
	private String primBase;
	public VaporizerData() {
		
	}
	public VaporizerData(int sequence, String siebelObject, String primBase) {
		super();
		this.sequence = sequence;
		this.siebelObject = siebelObject;
		this.primBase = primBase;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getSiebelObject() {
		return siebelObject;
	}
	public void setSiebelObject(String siebelObject) {
		this.siebelObject = siebelObject;
	}
	public String getPrimBase() {
		return primBase;
	}
	public void setPrimBase(String primBase) {
		this.primBase = primBase;
	} 
	
}
