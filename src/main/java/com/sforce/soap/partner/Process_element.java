package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class Process_element implements com.sforce.ws.bind.XMLizable , IProcess_element{

    /**
     * Constructor
     */
    public Process_element() {}

    /**
     * element : actions of type {urn:partner.soap.sforce.com}ProcessRequest
     * java type: com.sforce.soap.partner.ProcessRequest[]
     */
    private static final com.sforce.ws.bind.TypeInfo actions__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","actions","urn:partner.soap.sforce.com","ProcessRequest",0,-1,true);

    private boolean actions__is_set = false;

    private com.sforce.soap.partner.ProcessRequest[] actions = new com.sforce.soap.partner.ProcessRequest[0];

    @Override
    public com.sforce.soap.partner.ProcessRequest[] getActions() {
      return actions;
    }

    @Override
    public void setActions(com.sforce.soap.partner.IProcessRequest[] actions) {
      this.actions = castArray(com.sforce.soap.partner.ProcessRequest.class, actions);
      actions__is_set = true;
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
       __typeMapper.writeObject(__out, actions__typeInfo, actions, actions__is_set);
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
        if (__typeMapper.isElement(__in, actions__typeInfo)) {
            setActions((com.sforce.soap.partner.ProcessRequest[])__typeMapper.readObject(__in, actions__typeInfo, com.sforce.soap.partner.ProcessRequest[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[Process_element ");
      sb.append(" actions='").append(com.sforce.ws.util.Verbose.toString(actions)).append("'\n");
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
