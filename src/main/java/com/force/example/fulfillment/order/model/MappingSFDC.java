package com.force.example.fulfillment.order.model;

/**
 * @author gvolete
 *
 */
public class MappingSFDC {
	
	public String label;
	public String name;
	public String relationshipName;
	public String[] referenceTo;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelationshipName() {
		return relationshipName;
	}
	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}
	public String[] getReferenceTo() {
		return referenceTo;
	}
	public void setReferenceTo(String[] referenceTo) {
		this.referenceTo = referenceTo;
	}
}
