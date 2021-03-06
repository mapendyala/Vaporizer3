package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class DescribeSObjectListViews_element implements com.sforce.ws.bind.XMLizable , IDescribeSObjectListViews_element{

    /**
     * Constructor
     */
    public DescribeSObjectListViews_element() {}

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
     * element : recentsOnly of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo recentsOnly__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","recentsOnly","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean recentsOnly__is_set = false;

    private boolean recentsOnly;

    @Override
    public boolean getRecentsOnly() {
      return recentsOnly;
    }

    @Override
    public boolean isRecentsOnly() {
      return recentsOnly;
    }

    @Override
    public void setRecentsOnly(boolean recentsOnly) {
      this.recentsOnly = recentsOnly;
      recentsOnly__is_set = true;
    }

    /**
     * element : isSoqlCompatible of type {urn:partner.soap.sforce.com}listViewIsSoqlCompatible
     * java type: com.sforce.soap.partner.ListViewIsSoqlCompatible
     */
    private static final com.sforce.ws.bind.TypeInfo isSoqlCompatible__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","isSoqlCompatible","urn:partner.soap.sforce.com","listViewIsSoqlCompatible",1,1,true);

    private boolean isSoqlCompatible__is_set = false;

    private com.sforce.soap.partner.ListViewIsSoqlCompatible isSoqlCompatible;

    @Override
    public com.sforce.soap.partner.ListViewIsSoqlCompatible getIsSoqlCompatible() {
      return isSoqlCompatible;
    }

    @Override
    public void setIsSoqlCompatible(com.sforce.soap.partner.ListViewIsSoqlCompatible isSoqlCompatible) {
      this.isSoqlCompatible = isSoqlCompatible;
      isSoqlCompatible__is_set = true;
    }

    /**
     * element : limit of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private static final com.sforce.ws.bind.TypeInfo limit__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","limit","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean limit__is_set = false;

    private int limit;

    @Override
    public int getLimit() {
      return limit;
    }

    @Override
    public void setLimit(int limit) {
      this.limit = limit;
      limit__is_set = true;
    }

    /**
     * element : offset of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private static final com.sforce.ws.bind.TypeInfo offset__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","offset","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean offset__is_set = false;

    private int offset;

    @Override
    public int getOffset() {
      return offset;
    }

    @Override
    public void setOffset(int offset) {
      this.offset = offset;
      offset__is_set = true;
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
       __typeMapper.writeBoolean(__out, recentsOnly__typeInfo, recentsOnly, recentsOnly__is_set);
       __typeMapper.writeObject(__out, isSoqlCompatible__typeInfo, isSoqlCompatible, isSoqlCompatible__is_set);
       __typeMapper.writeInt(__out, limit__typeInfo, limit, limit__is_set);
       __typeMapper.writeInt(__out, offset__typeInfo, offset, offset__is_set);
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
        if (__typeMapper.verifyElement(__in, recentsOnly__typeInfo)) {
            setRecentsOnly(__typeMapper.readBoolean(__in, recentsOnly__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, isSoqlCompatible__typeInfo)) {
            setIsSoqlCompatible((com.sforce.soap.partner.ListViewIsSoqlCompatible)__typeMapper.readObject(__in, isSoqlCompatible__typeInfo, com.sforce.soap.partner.ListViewIsSoqlCompatible.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, limit__typeInfo)) {
            setLimit((int)__typeMapper.readInt(__in, limit__typeInfo, int.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, offset__typeInfo)) {
            setOffset((int)__typeMapper.readInt(__in, offset__typeInfo, int.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[DescribeSObjectListViews_element ");
      sb.append(" sObjectType='").append(com.sforce.ws.util.Verbose.toString(sObjectType)).append("'\n");
      sb.append(" recentsOnly='").append(com.sforce.ws.util.Verbose.toString(recentsOnly)).append("'\n");
      sb.append(" isSoqlCompatible='").append(com.sforce.ws.util.Verbose.toString(isSoqlCompatible)).append("'\n");
      sb.append(" limit='").append(com.sforce.ws.util.Verbose.toString(limit)).append("'\n");
      sb.append(" offset='").append(com.sforce.ws.util.Verbose.toString(offset)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
