package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class DescribeApprovalLayout implements com.sforce.ws.bind.XMLizable , IDescribeApprovalLayout{

    /**
     * Constructor
     */
    public DescribeApprovalLayout() {}

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
     * element : layoutItems of type {urn:partner.soap.sforce.com}DescribeLayoutItem
     * java type: com.sforce.soap.partner.DescribeLayoutItem[]
     */
    private static final com.sforce.ws.bind.TypeInfo layoutItems__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","layoutItems","urn:partner.soap.sforce.com","DescribeLayoutItem",1,-1,true);

    private boolean layoutItems__is_set = false;

    private com.sforce.soap.partner.DescribeLayoutItem[] layoutItems = new com.sforce.soap.partner.DescribeLayoutItem[0];

    @Override
    public com.sforce.soap.partner.DescribeLayoutItem[] getLayoutItems() {
      return layoutItems;
    }

    @Override
    public void setLayoutItems(com.sforce.soap.partner.IDescribeLayoutItem[] layoutItems) {
      this.layoutItems = castArray(com.sforce.soap.partner.DescribeLayoutItem.class, layoutItems);
      layoutItems__is_set = true;
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
       __typeMapper.writeObject(__out, layoutItems__typeInfo, layoutItems, layoutItems__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
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
        if (__typeMapper.verifyElement(__in, layoutItems__typeInfo)) {
            setLayoutItems((com.sforce.soap.partner.DescribeLayoutItem[])__typeMapper.readObject(__in, layoutItems__typeInfo, com.sforce.soap.partner.DescribeLayoutItem[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[DescribeApprovalLayout ");
      sb.append(" id='").append(com.sforce.ws.util.Verbose.toString(id)).append("'\n");
      sb.append(" label='").append(com.sforce.ws.util.Verbose.toString(label)).append("'\n");
      sb.append(" layoutItems='").append(com.sforce.ws.util.Verbose.toString(layoutItems)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
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
