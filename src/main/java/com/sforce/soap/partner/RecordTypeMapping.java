package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class RecordTypeMapping implements com.sforce.ws.bind.XMLizable , IRecordTypeMapping{

    /**
     * Constructor
     */
    public RecordTypeMapping() {}

    /**
     * element : available of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo available__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","available","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean available__is_set = false;

    private boolean available;

    @Override
    public boolean getAvailable() {
      return available;
    }

    @Override
    public boolean isAvailable() {
      return available;
    }

    @Override
    public void setAvailable(boolean available) {
      this.available = available;
      available__is_set = true;
    }

    /**
     * element : defaultRecordTypeMapping of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo defaultRecordTypeMapping__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","defaultRecordTypeMapping","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean defaultRecordTypeMapping__is_set = false;

    private boolean defaultRecordTypeMapping;

    @Override
    public boolean getDefaultRecordTypeMapping() {
      return defaultRecordTypeMapping;
    }

    @Override
    public boolean isDefaultRecordTypeMapping() {
      return defaultRecordTypeMapping;
    }

    @Override
    public void setDefaultRecordTypeMapping(boolean defaultRecordTypeMapping) {
      this.defaultRecordTypeMapping = defaultRecordTypeMapping;
      defaultRecordTypeMapping__is_set = true;
    }

    /**
     * element : layoutId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo layoutId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","layoutId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean layoutId__is_set = false;

    private java.lang.String layoutId;

    @Override
    public java.lang.String getLayoutId() {
      return layoutId;
    }

    @Override
    public void setLayoutId(java.lang.String layoutId) {
      this.layoutId = layoutId;
      layoutId__is_set = true;
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
     * element : picklistsForRecordType of type {urn:partner.soap.sforce.com}PicklistForRecordType
     * java type: com.sforce.soap.partner.PicklistForRecordType[]
     */
    private static final com.sforce.ws.bind.TypeInfo picklistsForRecordType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","picklistsForRecordType","urn:partner.soap.sforce.com","PicklistForRecordType",0,-1,true);

    private boolean picklistsForRecordType__is_set = false;

    private com.sforce.soap.partner.PicklistForRecordType[] picklistsForRecordType = new com.sforce.soap.partner.PicklistForRecordType[0];

    @Override
    public com.sforce.soap.partner.PicklistForRecordType[] getPicklistsForRecordType() {
      return picklistsForRecordType;
    }

    @Override
    public void setPicklistsForRecordType(com.sforce.soap.partner.IPicklistForRecordType[] picklistsForRecordType) {
      this.picklistsForRecordType = castArray(com.sforce.soap.partner.PicklistForRecordType.class, picklistsForRecordType);
      picklistsForRecordType__is_set = true;
    }

    /**
     * element : recordTypeId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo recordTypeId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","recordTypeId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean recordTypeId__is_set = false;

    private java.lang.String recordTypeId;

    @Override
    public java.lang.String getRecordTypeId() {
      return recordTypeId;
    }

    @Override
    public void setRecordTypeId(java.lang.String recordTypeId) {
      this.recordTypeId = recordTypeId;
      recordTypeId__is_set = true;
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
       __typeMapper.writeBoolean(__out, available__typeInfo, available, available__is_set);
       __typeMapper.writeBoolean(__out, defaultRecordTypeMapping__typeInfo, defaultRecordTypeMapping, defaultRecordTypeMapping__is_set);
       __typeMapper.writeString(__out, layoutId__typeInfo, layoutId, layoutId__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeObject(__out, picklistsForRecordType__typeInfo, picklistsForRecordType, picklistsForRecordType__is_set);
       __typeMapper.writeString(__out, recordTypeId__typeInfo, recordTypeId, recordTypeId__is_set);
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
        if (__typeMapper.verifyElement(__in, available__typeInfo)) {
            setAvailable(__typeMapper.readBoolean(__in, available__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, defaultRecordTypeMapping__typeInfo)) {
            setDefaultRecordTypeMapping(__typeMapper.readBoolean(__in, defaultRecordTypeMapping__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, layoutId__typeInfo)) {
            setLayoutId(__typeMapper.readString(__in, layoutId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, picklistsForRecordType__typeInfo)) {
            setPicklistsForRecordType((com.sforce.soap.partner.PicklistForRecordType[])__typeMapper.readObject(__in, picklistsForRecordType__typeInfo, com.sforce.soap.partner.PicklistForRecordType[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, recordTypeId__typeInfo)) {
            setRecordTypeId(__typeMapper.readString(__in, recordTypeId__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[RecordTypeMapping ");
      sb.append(" available='").append(com.sforce.ws.util.Verbose.toString(available)).append("'\n");
      sb.append(" defaultRecordTypeMapping='").append(com.sforce.ws.util.Verbose.toString(defaultRecordTypeMapping)).append("'\n");
      sb.append(" layoutId='").append(com.sforce.ws.util.Verbose.toString(layoutId)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" picklistsForRecordType='").append(com.sforce.ws.util.Verbose.toString(picklistsForRecordType)).append("'\n");
      sb.append(" recordTypeId='").append(com.sforce.ws.util.Verbose.toString(recordTypeId)).append("'\n");
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