package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class LeadConvertResult implements com.sforce.ws.bind.XMLizable , ILeadConvertResult{

    /**
     * Constructor
     */
    public LeadConvertResult() {}

    /**
     * element : accountId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo accountId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","accountId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean accountId__is_set = false;

    private java.lang.String accountId;

    @Override
    public java.lang.String getAccountId() {
      return accountId;
    }

    @Override
    public void setAccountId(java.lang.String accountId) {
      this.accountId = accountId;
      accountId__is_set = true;
    }

    /**
     * element : contactId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo contactId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","contactId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean contactId__is_set = false;

    private java.lang.String contactId;

    @Override
    public java.lang.String getContactId() {
      return contactId;
    }

    @Override
    public void setContactId(java.lang.String contactId) {
      this.contactId = contactId;
      contactId__is_set = true;
    }

    /**
     * element : errors of type {urn:partner.soap.sforce.com}Error
     * java type: com.sforce.soap.partner.Error[]
     */
    private static final com.sforce.ws.bind.TypeInfo errors__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","errors","urn:partner.soap.sforce.com","Error",0,-1,true);

    private boolean errors__is_set = false;

    private com.sforce.soap.partner.Error[] errors = new com.sforce.soap.partner.Error[0];

    @Override
    public com.sforce.soap.partner.Error[] getErrors() {
      return errors;
    }

    @Override
    public void setErrors(com.sforce.soap.partner.IError[] errors) {
      this.errors = castArray(com.sforce.soap.partner.Error.class, errors);
      errors__is_set = true;
    }

    /**
     * element : leadId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo leadId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","leadId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean leadId__is_set = false;

    private java.lang.String leadId;

    @Override
    public java.lang.String getLeadId() {
      return leadId;
    }

    @Override
    public void setLeadId(java.lang.String leadId) {
      this.leadId = leadId;
      leadId__is_set = true;
    }

    /**
     * element : opportunityId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo opportunityId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","opportunityId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean opportunityId__is_set = false;

    private java.lang.String opportunityId;

    @Override
    public java.lang.String getOpportunityId() {
      return opportunityId;
    }

    @Override
    public void setOpportunityId(java.lang.String opportunityId) {
      this.opportunityId = opportunityId;
      opportunityId__is_set = true;
    }

    /**
     * element : success of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo success__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","success","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean success__is_set = false;

    private boolean success;

    @Override
    public boolean getSuccess() {
      return success;
    }

    @Override
    public boolean isSuccess() {
      return success;
    }

    @Override
    public void setSuccess(boolean success) {
      this.success = success;
      success__is_set = true;
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
       __typeMapper.writeString(__out, accountId__typeInfo, accountId, accountId__is_set);
       __typeMapper.writeString(__out, contactId__typeInfo, contactId, contactId__is_set);
       __typeMapper.writeObject(__out, errors__typeInfo, errors, errors__is_set);
       __typeMapper.writeString(__out, leadId__typeInfo, leadId, leadId__is_set);
       __typeMapper.writeString(__out, opportunityId__typeInfo, opportunityId, opportunityId__is_set);
       __typeMapper.writeBoolean(__out, success__typeInfo, success, success__is_set);
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
        if (__typeMapper.verifyElement(__in, accountId__typeInfo)) {
            setAccountId(__typeMapper.readString(__in, accountId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, contactId__typeInfo)) {
            setContactId(__typeMapper.readString(__in, contactId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, errors__typeInfo)) {
            setErrors((com.sforce.soap.partner.Error[])__typeMapper.readObject(__in, errors__typeInfo, com.sforce.soap.partner.Error[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, leadId__typeInfo)) {
            setLeadId(__typeMapper.readString(__in, leadId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, opportunityId__typeInfo)) {
            setOpportunityId(__typeMapper.readString(__in, opportunityId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, success__typeInfo)) {
            setSuccess(__typeMapper.readBoolean(__in, success__typeInfo, boolean.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[LeadConvertResult ");
      sb.append(" accountId='").append(com.sforce.ws.util.Verbose.toString(accountId)).append("'\n");
      sb.append(" contactId='").append(com.sforce.ws.util.Verbose.toString(contactId)).append("'\n");
      sb.append(" errors='").append(com.sforce.ws.util.Verbose.toString(errors)).append("'\n");
      sb.append(" leadId='").append(com.sforce.ws.util.Verbose.toString(leadId)).append("'\n");
      sb.append(" opportunityId='").append(com.sforce.ws.util.Verbose.toString(opportunityId)).append("'\n");
      sb.append(" success='").append(com.sforce.ws.util.Verbose.toString(success)).append("'\n");
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
