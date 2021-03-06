package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class SendEmailError implements com.sforce.ws.bind.XMLizable , ISendEmailError{

    /**
     * Constructor
     */
    public SendEmailError() {}

    /**
     * element : fields of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo fields__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","fields","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean fields__is_set = false;

    private java.lang.String[] fields = new java.lang.String[0];

    @Override
    public java.lang.String[] getFields() {
      return fields;
    }

    @Override
    public void setFields(java.lang.String[] fields) {
      this.fields = castArray(java.lang.String.class, fields);
      fields__is_set = true;
    }

    /**
     * element : message of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo message__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","message","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean message__is_set = false;

    private java.lang.String message;

    @Override
    public java.lang.String getMessage() {
      return message;
    }

    @Override
    public void setMessage(java.lang.String message) {
      this.message = message;
      message__is_set = true;
    }

    /**
     * element : statusCode of type {urn:partner.soap.sforce.com}StatusCode
     * java type: com.sforce.soap.partner.StatusCode
     */
    private static final com.sforce.ws.bind.TypeInfo statusCode__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","statusCode","urn:partner.soap.sforce.com","StatusCode",1,1,true);

    private boolean statusCode__is_set = false;

    private com.sforce.soap.partner.StatusCode statusCode;

    @Override
    public com.sforce.soap.partner.StatusCode getStatusCode() {
      return statusCode;
    }

    @Override
    public void setStatusCode(com.sforce.soap.partner.StatusCode statusCode) {
      this.statusCode = statusCode;
      statusCode__is_set = true;
    }

    /**
     * element : targetObjectId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo targetObjectId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","targetObjectId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean targetObjectId__is_set = false;

    private java.lang.String targetObjectId;

    @Override
    public java.lang.String getTargetObjectId() {
      return targetObjectId;
    }

    @Override
    public void setTargetObjectId(java.lang.String targetObjectId) {
      this.targetObjectId = targetObjectId;
      targetObjectId__is_set = true;
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
       __typeMapper.writeObject(__out, fields__typeInfo, fields, fields__is_set);
       __typeMapper.writeString(__out, message__typeInfo, message, message__is_set);
       __typeMapper.writeObject(__out, statusCode__typeInfo, statusCode, statusCode__is_set);
       __typeMapper.writeString(__out, targetObjectId__typeInfo, targetObjectId, targetObjectId__is_set);
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
        if (__typeMapper.isElement(__in, fields__typeInfo)) {
            setFields((java.lang.String[])__typeMapper.readObject(__in, fields__typeInfo, java.lang.String[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, message__typeInfo)) {
            setMessage(__typeMapper.readString(__in, message__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, statusCode__typeInfo)) {
            setStatusCode((com.sforce.soap.partner.StatusCode)__typeMapper.readObject(__in, statusCode__typeInfo, com.sforce.soap.partner.StatusCode.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, targetObjectId__typeInfo)) {
            setTargetObjectId(__typeMapper.readString(__in, targetObjectId__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[SendEmailError ");
      sb.append(" fields='").append(com.sforce.ws.util.Verbose.toString(fields)).append("'\n");
      sb.append(" message='").append(com.sforce.ws.util.Verbose.toString(message)).append("'\n");
      sb.append(" statusCode='").append(com.sforce.ws.util.Verbose.toString(statusCode)).append("'\n");
      sb.append(" targetObjectId='").append(com.sforce.ws.util.Verbose.toString(targetObjectId)).append("'\n");
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
