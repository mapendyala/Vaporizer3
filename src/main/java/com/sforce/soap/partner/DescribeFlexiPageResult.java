package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class DescribeFlexiPageResult implements com.sforce.ws.bind.XMLizable , IDescribeFlexiPageResult{

    /**
     * Constructor
     */
    public DescribeFlexiPageResult() {}

    /**
     * element : id of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo id__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","id","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean id__is_set = false;

    private java.lang.String id;

    @Override
    public java.lang.String getId() {
      return id;
    }

    @Override
    public void setId(java.lang.String id) {
      this.id = id;
      id__is_set = true;
    }

    /**
     * element : label of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo label__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","label","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean label__is_set = false;

    private java.lang.String label;

    @Override
    public java.lang.String getLabel() {
      return label;
    }

    @Override
    public void setLabel(java.lang.String label) {
      this.label = label;
      label__is_set = true;
    }

    /**
     * element : name of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo name__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","name","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean name__is_set = false;

    private java.lang.String name;

    @Override
    public java.lang.String getName() {
      return name;
    }

    @Override
    public void setName(java.lang.String name) {
      this.name = name;
      name__is_set = true;
    }

    /**
     * element : quickActionList of type {urn:partner.soap.sforce.com}DescribeQuickActionListResult
     * java type: com.sforce.soap.partner.DescribeQuickActionListResult
     */
    private static final com.sforce.ws.bind.TypeInfo quickActionList__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","quickActionList","urn:partner.soap.sforce.com","DescribeQuickActionListResult",0,1,true);

    private boolean quickActionList__is_set = false;

    private com.sforce.soap.partner.DescribeQuickActionListResult quickActionList;

    @Override
    public com.sforce.soap.partner.DescribeQuickActionListResult getQuickActionList() {
      return quickActionList;
    }

    @Override
    public void setQuickActionList(com.sforce.soap.partner.IDescribeQuickActionListResult quickActionList) {
      this.quickActionList = (com.sforce.soap.partner.DescribeQuickActionListResult)quickActionList;
      quickActionList__is_set = true;
    }

    /**
     * element : regions of type {urn:partner.soap.sforce.com}DescribeFlexiPageRegion
     * java type: com.sforce.soap.partner.DescribeFlexiPageRegion[]
     */
    private static final com.sforce.ws.bind.TypeInfo regions__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","regions","urn:partner.soap.sforce.com","DescribeFlexiPageRegion",0,-1,true);

    private boolean regions__is_set = false;

    private com.sforce.soap.partner.DescribeFlexiPageRegion[] regions = new com.sforce.soap.partner.DescribeFlexiPageRegion[0];

    @Override
    public com.sforce.soap.partner.DescribeFlexiPageRegion[] getRegions() {
      return regions;
    }

    @Override
    public void setRegions(com.sforce.soap.partner.IDescribeFlexiPageRegion[] regions) {
      this.regions = castArray(com.sforce.soap.partner.DescribeFlexiPageRegion.class, regions);
      regions__is_set = true;
    }

    /**
     * element : sobjectType of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo sobjectType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","sobjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean sobjectType__is_set = false;

    private java.lang.String sobjectType;

    @Override
    public java.lang.String getSobjectType() {
      return sobjectType;
    }

    @Override
    public void setSobjectType(java.lang.String sobjectType) {
      this.sobjectType = sobjectType;
      sobjectType__is_set = true;
    }

    /**
     * element : template of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo template__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","template","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean template__is_set = false;

    private java.lang.String template;

    @Override
    public java.lang.String getTemplate() {
      return template;
    }

    @Override
    public void setTemplate(java.lang.String template) {
      this.template = template;
      template__is_set = true;
    }

    /**
     * element : type of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo type__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","type","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean type__is_set = false;

    private java.lang.String type;

    @Override
    public java.lang.String getType() {
      return type;
    }

    @Override
    public void setType(java.lang.String type) {
      this.type = type;
      type__is_set = true;
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
       __typeMapper.writeString(__out, id__typeInfo, id, id__is_set);
       __typeMapper.writeString(__out, label__typeInfo, label, label__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeObject(__out, quickActionList__typeInfo, quickActionList, quickActionList__is_set);
       __typeMapper.writeObject(__out, regions__typeInfo, regions, regions__is_set);
       __typeMapper.writeString(__out, sobjectType__typeInfo, sobjectType, sobjectType__is_set);
       __typeMapper.writeString(__out, template__typeInfo, template, template__is_set);
       __typeMapper.writeString(__out, type__typeInfo, type, type__is_set);
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
        if (__typeMapper.verifyElement(__in, id__typeInfo)) {
            setId(__typeMapper.readString(__in, id__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, label__typeInfo)) {
            setLabel(__typeMapper.readString(__in, label__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, quickActionList__typeInfo)) {
            setQuickActionList((com.sforce.soap.partner.DescribeQuickActionListResult)__typeMapper.readObject(__in, quickActionList__typeInfo, com.sforce.soap.partner.DescribeQuickActionListResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, regions__typeInfo)) {
            setRegions((com.sforce.soap.partner.DescribeFlexiPageRegion[])__typeMapper.readObject(__in, regions__typeInfo, com.sforce.soap.partner.DescribeFlexiPageRegion[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, sobjectType__typeInfo)) {
            setSobjectType(__typeMapper.readString(__in, sobjectType__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, template__typeInfo)) {
            setTemplate(__typeMapper.readString(__in, template__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, type__typeInfo)) {
            setType(__typeMapper.readString(__in, type__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[DescribeFlexiPageResult ");
      sb.append(" id='").append(com.sforce.ws.util.Verbose.toString(id)).append("'\n");
      sb.append(" label='").append(com.sforce.ws.util.Verbose.toString(label)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" quickActionList='").append(com.sforce.ws.util.Verbose.toString(quickActionList)).append("'\n");
      sb.append(" regions='").append(com.sforce.ws.util.Verbose.toString(regions)).append("'\n");
      sb.append(" sobjectType='").append(com.sforce.ws.util.Verbose.toString(sobjectType)).append("'\n");
      sb.append(" template='").append(com.sforce.ws.util.Verbose.toString(template)).append("'\n");
      sb.append(" type='").append(com.sforce.ws.util.Verbose.toString(type)).append("'\n");
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
