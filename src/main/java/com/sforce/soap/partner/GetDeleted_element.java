package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class GetDeleted_element implements com.sforce.ws.bind.XMLizable , IGetDeleted_element{

    /**
     * Constructor
     */
    public GetDeleted_element() {}

    /**
     * element : sObjectType of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo sObjectType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","sObjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean sObjectType__is_set = false;

    private java.lang.String sObjectType;

    @Override
    public java.lang.String getSObjectType() {
      return sObjectType;
    }

    @Override
    public void setSObjectType(java.lang.String sObjectType) {
      this.sObjectType = sObjectType;
      sObjectType__is_set = true;
    }

    /**
     * element : startDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo startDate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","startDate","http://www.w3.org/2001/XMLSchema","dateTime",1,1,true);

    private boolean startDate__is_set = false;

    private java.util.Calendar startDate;

    @Override
    public java.util.Calendar getStartDate() {
      return startDate;
    }

    @Override
    public void setStartDate(java.util.Calendar startDate) {
      this.startDate = startDate;
      startDate__is_set = true;
    }

    /**
     * element : endDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo endDate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","endDate","http://www.w3.org/2001/XMLSchema","dateTime",1,1,true);

    private boolean endDate__is_set = false;

    private java.util.Calendar endDate;

    @Override
    public java.util.Calendar getEndDate() {
      return endDate;
    }

    @Override
    public void setEndDate(java.util.Calendar endDate) {
      this.endDate = endDate;
      endDate__is_set = true;
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
       __typeMapper.writeString(__out, sObjectType__typeInfo, sObjectType, sObjectType__is_set);
       __typeMapper.writeObject(__out, startDate__typeInfo, startDate, startDate__is_set);
       __typeMapper.writeObject(__out, endDate__typeInfo, endDate, endDate__is_set);
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
        if (__typeMapper.verifyElement(__in, sObjectType__typeInfo)) {
            setSObjectType(__typeMapper.readString(__in, sObjectType__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, startDate__typeInfo)) {
            setStartDate((java.util.Calendar)__typeMapper.readObject(__in, startDate__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, endDate__typeInfo)) {
            setEndDate((java.util.Calendar)__typeMapper.readObject(__in, endDate__typeInfo, java.util.Calendar.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[GetDeleted_element ");
      sb.append(" sObjectType='").append(com.sforce.ws.util.Verbose.toString(sObjectType)).append("'\n");
      sb.append(" startDate='").append(com.sforce.ws.util.Verbose.toString(startDate)).append("'\n");
      sb.append(" endDate='").append(com.sforce.ws.util.Verbose.toString(endDate)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
