package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class PerformQuickActions_element implements com.sforce.ws.bind.XMLizable , IPerformQuickActions_element{

    /**
     * Constructor
     */
    public PerformQuickActions_element() {}

    /**
     * element : quickActions of type {urn:partner.soap.sforce.com}PerformQuickActionRequest
     * java type: com.sforce.soap.partner.PerformQuickActionRequest[]
     */
    private static final com.sforce.ws.bind.TypeInfo quickActions__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","quickActions","urn:partner.soap.sforce.com","PerformQuickActionRequest",0,-1,true);

    private boolean quickActions__is_set = false;

    private com.sforce.soap.partner.PerformQuickActionRequest[] quickActions = new com.sforce.soap.partner.PerformQuickActionRequest[0];

    @Override
    public com.sforce.soap.partner.PerformQuickActionRequest[] getQuickActions() {
      return quickActions;
    }

    @Override
    public void setQuickActions(com.sforce.soap.partner.IPerformQuickActionRequest[] quickActions) {
      this.quickActions = castArray(com.sforce.soap.partner.PerformQuickActionRequest.class, quickActions);
      quickActions__is_set = true;
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
       __typeMapper.writeObject(__out, quickActions__typeInfo, quickActions, quickActions__is_set);
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
        if (__typeMapper.isElement(__in, quickActions__typeInfo)) {
            setQuickActions((com.sforce.soap.partner.PerformQuickActionRequest[])__typeMapper.readObject(__in, quickActions__typeInfo, com.sforce.soap.partner.PerformQuickActionRequest[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[PerformQuickActions_element ");
      sb.append(" quickActions='").append(com.sforce.ws.util.Verbose.toString(quickActions)).append("'\n");
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
