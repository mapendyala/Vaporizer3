package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FieldLayoutComponent extends com.sforce.soap.partner.DescribeLayoutComponent implements IFieldLayoutComponent{

    /**
     * Constructor
     */
    public FieldLayoutComponent() {}

    /**
     * element : components of type {urn:partner.soap.sforce.com}DescribeLayoutComponent
     * java type: com.sforce.soap.partner.DescribeLayoutComponent[]
     */
    private static final com.sforce.ws.bind.TypeInfo components__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","components","urn:partner.soap.sforce.com","DescribeLayoutComponent",0,-1,true);

    private boolean components__is_set = false;

    private com.sforce.soap.partner.DescribeLayoutComponent[] components = new com.sforce.soap.partner.DescribeLayoutComponent[0];

    @Override
    public com.sforce.soap.partner.DescribeLayoutComponent[] getComponents() {
      return components;
    }

    @Override
    public void setComponents(com.sforce.soap.partner.IDescribeLayoutComponent[] components) {
      this.components = castArray(com.sforce.soap.partner.DescribeLayoutComponent.class, components);
      components__is_set = true;
    }

    /**
     * element : fieldType of type {urn:partner.soap.sforce.com}fieldType
     * java type: com.sforce.soap.partner.FieldType
     */
    private static final com.sforce.ws.bind.TypeInfo fieldType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","fieldType","urn:partner.soap.sforce.com","fieldType",1,1,true);

    private boolean fieldType__is_set = false;

    private com.sforce.soap.partner.FieldType fieldType;

    @Override
    public com.sforce.soap.partner.FieldType getFieldType() {
      return fieldType;
    }

    @Override
    public void setFieldType(com.sforce.soap.partner.FieldType fieldType) {
      this.fieldType = fieldType;
      fieldType__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:partner.soap.sforce.com", "FieldLayoutComponent");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, components__typeInfo, components, components__is_set);
       __typeMapper.writeObject(__out, fieldType__typeInfo, fieldType, fieldType__is_set);
    }

    @Override
    public void load(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __typeMapper.consumeStartTag(__in);
      loadFields(__in, __typeMapper);
      __typeMapper.consumeEndTag(__in);
    }

    protected void loadFields(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
        super.loadFields(__in, __typeMapper);
        __in.peekTag();
        if (__typeMapper.isElement(__in, components__typeInfo)) {
            setComponents((com.sforce.soap.partner.DescribeLayoutComponent[])__typeMapper.readObject(__in, components__typeInfo, com.sforce.soap.partner.DescribeLayoutComponent[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, fieldType__typeInfo)) {
            setFieldType((com.sforce.soap.partner.FieldType)__typeMapper.readObject(__in, fieldType__typeInfo, com.sforce.soap.partner.FieldType.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FieldLayoutComponent ");
      sb.append(super.toString());sb.append(" components='").append(com.sforce.ws.util.Verbose.toString(components)).append("'\n");
      sb.append(" fieldType='").append(com.sforce.ws.util.Verbose.toString(fieldType)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private <T,U> T[] castArray(Class<T> clazz, U[] array) {
        if (array == null) {
            return null;
        }
        T[] retVal = (T[]) java.lang.reflect.Array.newInstance(clazz, array.length);
        for (int i=0; i < array.length; i++) {
            retVal[i] = (T)array[i];
        }

        return retVal;
	}
}