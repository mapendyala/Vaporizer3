package com.force.example.fulfillment.order.model;

public class Transformation {
	public String expression;
	public String transformation;
	public String argument;
	public String databaseName;
	public String helpText;
	
	
	public String getHelpText() {
		return helpText;
	}
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getTransformation() {
		return transformation;
	}
	public void setTransformation(String transformation) {
		this.transformation = transformation;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	

}
