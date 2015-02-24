package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class AllowFieldTruncationHeader_element extends com.sforce.ws.bind.SoapHeaderObject implements com.sforce.ws.bind.XMLizable , IAllowFieldTruncationHeader_element{

    /**
     * Constructor
     */
    public AllowFieldTruncationHeader_element() {}

    /**
     * element : allowFieldTruncation of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo allowFieldTruncation__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","allowFieldTruncation","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean allowFieldTruncation__is_set = false;

    private boolean allowFieldTruncation;

    @Override
    public boolean getAllowFieldTruncation() {
      return allowFieldTruncation;
    }

    @Override
    public boolean isAllowFieldTruncation() {
      return allowFieldTruncation;
    }

    @Override
    public void setAllowFieldTruncation(boolean allowFieldTruncation) {
      this.allowFieldTruncation = allowFieldTruncation;
      allowFieldTruncation__is_set = true;
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
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeBoolean(__out, allowFieldTruncation__typeInfo, allowFieldTruncation, allowFieldTruncation__is_set);
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
        if (__typeMapper.verifyElement(__in, allowFieldTruncation__typeInfo)) {
            setAllowFieldTruncation(__typeMapper.readBoolean(__in, allowFieldTruncation__typeInfo, boolean.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[AllowFieldTruncationHeader_element ");
      sb.append(super.toString());sb.append(" allowFieldTruncation='").append(com.sforce.ws.util.Verbose.toString(allowFieldTruncation)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
