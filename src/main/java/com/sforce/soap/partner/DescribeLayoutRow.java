package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class DescribeLayoutRow implements com.sforce.ws.bind.XMLizable , IDescribeLayoutRow{

    /**
     * Constructor
     */
    public DescribeLayoutRow() {}

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
     * element : numItems of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private static final com.sforce.ws.bind.TypeInfo numItems__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","numItems","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean numItems__is_set = false;

    private int numItems;

    @Override
    public int getNumItems() {
      return numItems;
    }

    @Override
    public void setNumItems(int numItems) {
      this.numItems = numItems;
      numItems__is_set = true;
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
       __typeMapper.writeObject(__out, layoutItems__typeInfo, layoutItems, layoutItems__is_set);
       __typeMapper.writeInt(__out, numItems__typeInfo, numItems, numItems__is_set);
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
        if (__typeMapper.verifyElement(__in, layoutItems__typeInfo)) {
            setLayoutItems((com.sforce.soap.partner.DescribeLayoutItem[])__typeMapper.readObject(__in, layoutItems__typeInfo, com.sforce.soap.partner.DescribeLayoutItem[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, numItems__typeInfo)) {
            setNumItems((int)__typeMapper.readInt(__in, numItems__typeInfo, int.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[DescribeLayoutRow ");
      sb.append(" layoutItems='").append(com.sforce.ws.util.Verbose.toString(layoutItems)).append("'\n");
      sb.append(" numItems='").append(com.sforce.ws.util.Verbose.toString(numItems)).append("'\n");
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
