package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class RelatedList implements com.sforce.ws.bind.XMLizable , IRelatedList{

    /**
     * Constructor
     */
    public RelatedList() {}

    /**
     * element : accessLevelRequiredForCreate of type {urn:partner.soap.sforce.com}ShareAccessLevel
     * java type: com.sforce.soap.partner.ShareAccessLevel
     */
    private static final com.sforce.ws.bind.TypeInfo accessLevelRequiredForCreate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","accessLevelRequiredForCreate","urn:partner.soap.sforce.com","ShareAccessLevel",1,1,true);

    private boolean accessLevelRequiredForCreate__is_set = false;

    private com.sforce.soap.partner.ShareAccessLevel accessLevelRequiredForCreate;

    @Override
    public com.sforce.soap.partner.ShareAccessLevel getAccessLevelRequiredForCreate() {
      return accessLevelRequiredForCreate;
    }

    @Override
    public void setAccessLevelRequiredForCreate(com.sforce.soap.partner.ShareAccessLevel accessLevelRequiredForCreate) {
      this.accessLevelRequiredForCreate = accessLevelRequiredForCreate;
      accessLevelRequiredForCreate__is_set = true;
    }

    /**
     * element : buttons of type {urn:partner.soap.sforce.com}DescribeLayoutButton
     * java type: com.sforce.soap.partner.DescribeLayoutButton[]
     */
    private static final com.sforce.ws.bind.TypeInfo buttons__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","buttons","urn:partner.soap.sforce.com","DescribeLayoutButton",0,-1,true);

    private boolean buttons__is_set = false;

    private com.sforce.soap.partner.DescribeLayoutButton[] buttons = new com.sforce.soap.partner.DescribeLayoutButton[0];

    @Override
    public com.sforce.soap.partner.DescribeLayoutButton[] getButtons() {
      return buttons;
    }

    @Override
    public void setButtons(com.sforce.soap.partner.IDescribeLayoutButton[] buttons) {
      this.buttons = castArray(com.sforce.soap.partner.DescribeLayoutButton.class, buttons);
      buttons__is_set = true;
    }

    /**
     * element : columns of type {urn:partner.soap.sforce.com}RelatedListColumn
     * java type: com.sforce.soap.partner.RelatedListColumn[]
     */
    private static final com.sforce.ws.bind.TypeInfo columns__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","columns","urn:partner.soap.sforce.com","RelatedListColumn",1,-1,true);

    private boolean columns__is_set = false;

    private com.sforce.soap.partner.RelatedListColumn[] columns = new com.sforce.soap.partner.RelatedListColumn[0];

    @Override
    public com.sforce.soap.partner.RelatedListColumn[] getColumns() {
      return columns;
    }

    @Override
    public void setColumns(com.sforce.soap.partner.IRelatedListColumn[] columns) {
      this.columns = castArray(com.sforce.soap.partner.RelatedListColumn.class, columns);
      columns__is_set = true;
    }

    /**
     * element : custom of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo custom__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","custom","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean custom__is_set = false;

    private boolean custom;

    @Override
    public boolean getCustom() {
      return custom;
    }

    @Override
    public boolean isCustom() {
      return custom;
    }

    @Override
    public void setCustom(boolean custom) {
      this.custom = custom;
      custom__is_set = true;
    }

    /**
     * element : field of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo field__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","field","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean field__is_set = false;

    private java.lang.String field;

    @Override
    public java.lang.String getField() {
      return field;
    }

    @Override
    public void setField(java.lang.String field) {
      this.field = field;
      field__is_set = true;
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
     * element : limitRows of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private static final com.sforce.ws.bind.TypeInfo limitRows__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","limitRows","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean limitRows__is_set = false;

    private int limitRows;

    @Override
    public int getLimitRows() {
      return limitRows;
    }

    @Override
    public void setLimitRows(int limitRows) {
      this.limitRows = limitRows;
      limitRows__is_set = true;
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
     * element : sobject of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo sobject__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","sobject","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean sobject__is_set = false;

    private java.lang.String sobject;

    @Override
    public java.lang.String getSobject() {
      return sobject;
    }

    @Override
    public void setSobject(java.lang.String sobject) {
      this.sobject = sobject;
      sobject__is_set = true;
    }

    /**
     * element : sort of type {urn:partner.soap.sforce.com}RelatedListSort
     * java type: com.sforce.soap.partner.RelatedListSort[]
     */
    private static final com.sforce.ws.bind.TypeInfo sort__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","sort","urn:partner.soap.sforce.com","RelatedListSort",0,-1,true);

    private boolean sort__is_set = false;

    private com.sforce.soap.partner.RelatedListSort[] sort = new com.sforce.soap.partner.RelatedListSort[0];

    @Override
    public com.sforce.soap.partner.RelatedListSort[] getSort() {
      return sort;
    }

    @Override
    public void setSort(com.sforce.soap.partner.IRelatedListSort[] sort) {
      this.sort = castArray(com.sforce.soap.partner.RelatedListSort.class, sort);
      sort__is_set = true;
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
       __typeMapper.writeObject(__out, accessLevelRequiredForCreate__typeInfo, accessLevelRequiredForCreate, accessLevelRequiredForCreate__is_set);
       __typeMapper.writeObject(__out, buttons__typeInfo, buttons, buttons__is_set);
       __typeMapper.writeObject(__out, columns__typeInfo, columns, columns__is_set);
       __typeMapper.writeBoolean(__out, custom__typeInfo, custom, custom__is_set);
       __typeMapper.writeString(__out, field__typeInfo, field, field__is_set);
       __typeMapper.writeString(__out, label__typeInfo, label, label__is_set);
       __typeMapper.writeInt(__out, limitRows__typeInfo, limitRows, limitRows__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeString(__out, sobject__typeInfo, sobject, sobject__is_set);
       __typeMapper.writeObject(__out, sort__typeInfo, sort, sort__is_set);
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
        if (__typeMapper.verifyElement(__in, accessLevelRequiredForCreate__typeInfo)) {
            setAccessLevelRequiredForCreate((com.sforce.soap.partner.ShareAccessLevel)__typeMapper.readObject(__in, accessLevelRequiredForCreate__typeInfo, com.sforce.soap.partner.ShareAccessLevel.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, buttons__typeInfo)) {
            setButtons((com.sforce.soap.partner.DescribeLayoutButton[])__typeMapper.readObject(__in, buttons__typeInfo, com.sforce.soap.partner.DescribeLayoutButton[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, columns__typeInfo)) {
            setColumns((com.sforce.soap.partner.RelatedListColumn[])__typeMapper.readObject(__in, columns__typeInfo, com.sforce.soap.partner.RelatedListColumn[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, custom__typeInfo)) {
            setCustom(__typeMapper.readBoolean(__in, custom__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, field__typeInfo)) {
            setField(__typeMapper.readString(__in, field__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, label__typeInfo)) {
            setLabel(__typeMapper.readString(__in, label__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, limitRows__typeInfo)) {
            setLimitRows((int)__typeMapper.readInt(__in, limitRows__typeInfo, int.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, sobject__typeInfo)) {
            setSobject(__typeMapper.readString(__in, sobject__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, sort__typeInfo)) {
            setSort((com.sforce.soap.partner.RelatedListSort[])__typeMapper.readObject(__in, sort__typeInfo, com.sforce.soap.partner.RelatedListSort[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[RelatedList ");
      sb.append(" accessLevelRequiredForCreate='").append(com.sforce.ws.util.Verbose.toString(accessLevelRequiredForCreate)).append("'\n");
      sb.append(" buttons='").append(com.sforce.ws.util.Verbose.toString(buttons)).append("'\n");
      sb.append(" columns='").append(com.sforce.ws.util.Verbose.toString(columns)).append("'\n");
      sb.append(" custom='").append(com.sforce.ws.util.Verbose.toString(custom)).append("'\n");
      sb.append(" field='").append(com.sforce.ws.util.Verbose.toString(field)).append("'\n");
      sb.append(" label='").append(com.sforce.ws.util.Verbose.toString(label)).append("'\n");
      sb.append(" limitRows='").append(com.sforce.ws.util.Verbose.toString(limitRows)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" sobject='").append(com.sforce.ws.util.Verbose.toString(sobject)).append("'\n");
      sb.append(" sort='").append(com.sforce.ws.util.Verbose.toString(sort)).append("'\n");
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
