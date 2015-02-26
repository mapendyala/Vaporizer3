package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class SendEmail_element implements com.sforce.ws.bind.XMLizable , ISendEmail_element{

    /**
     * Constructor
     */
    public SendEmail_element() {}

    /**
     * element : messages of type {urn:partner.soap.sforce.com}Email
     * java type: com.sforce.soap.partner.Email[]
     */
    private static final com.sforce.ws.bind.TypeInfo messages__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","messages","urn:partner.soap.sforce.com","Email",0,10,true);

    private boolean messages__is_set = false;

    private com.sforce.soap.partner.Email[] messages = new com.sforce.soap.partner.Email[0];

    @Override
    public com.sforce.soap.partner.Email[] getMessages() {
      return messages;
    }

    @Override
    public void setMessages(com.sforce.soap.partner.IEmail[] messages) {
      this.messages = castArray(com.sforce.soap.partner.Email.class, messages);
      messages__is_set = true;
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
       __typeMapper.writeObject(__out, messages__typeInfo, messages, messages__is_set);
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
        if (__typeMapper.isElement(__in, messages__typeInfo)) {
            setMessages((com.sforce.soap.partner.Email[])__typeMapper.readObject(__in, messages__typeInfo, com.sforce.soap.partner.Email[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[SendEmail_element ");
      sb.append(" messages='").append(com.sforce.ws.util.Verbose.toString(messages)).append("'\n");
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