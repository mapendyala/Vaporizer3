package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class ListViewRecordColumn implements com.sforce.ws.bind.XMLizable , IListViewRecordColumn{

    /**
     * Constructor
     */
    public ListViewRecordColumn() {}

    /**
     * element : fieldNameOrPath of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo fieldNameOrPath__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","fieldNameOrPath","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean fieldNameOrPath__is_set = false;

    private java.lang.String fieldNameOrPath;

    @Override
    public java.lang.String getFieldNameOrPath() {
      return fieldNameOrPath;
    }

    @Override
    public void setFieldNameOrPath(java.lang.String fieldNameOrPath) {
      this.fieldNameOrPath = fieldNameOrPath;
      fieldNameOrPath__is_set = true;
    }

    /**
     * element : value of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo value__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","value","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean value__is_set = false;

    private java.lang.String value;

    @Override
    public java.lang.String getValue() {
      return value;
    }

    @Override
    public void setValue(java.lang.String value) {
      this.value = value;
      value__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       __typeMapper.writeString(__out, fieldNameOrPath__typeInfo, fieldNameOrPath, fieldNameOrPath__is_set);
       __typeMapper.writeString(__out, value__typeInfo, value, value__is_set);
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
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, fieldNameOrPath__typeInfo)) {
            setFieldNameOrPath(__typeMapper.readString(__in, fieldNameOrPath__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, value__typeInfo)) {
            setValue(__typeMapper.readString(__in, value__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[ListViewRecordColumn ");
      sb.append(" fieldNameOrPath='").append(com.sforce.ws.util.Verbose.toString(fieldNameOrPath)).append("'\n");
      sb.append(" value='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
