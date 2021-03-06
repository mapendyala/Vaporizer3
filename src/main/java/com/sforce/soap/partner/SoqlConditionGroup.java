package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class SoqlConditionGroup extends com.sforce.soap.partner.SoqlWhereCondition implements ISoqlConditionGroup{

    /**
     * Constructor
     */
    public SoqlConditionGroup() {}

    /**
     * element : conditions of type {urn:partner.soap.sforce.com}SoqlWhereCondition
     * java type: com.sforce.soap.partner.SoqlWhereCondition[]
     */
    private static final com.sforce.ws.bind.TypeInfo conditions__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","conditions","urn:partner.soap.sforce.com","SoqlWhereCondition",1,-1,true);

    private boolean conditions__is_set = false;

    private com.sforce.soap.partner.SoqlWhereCondition[] conditions = new com.sforce.soap.partner.SoqlWhereCondition[0];

    @Override
    public com.sforce.soap.partner.SoqlWhereCondition[] getConditions() {
      return conditions;
    }

    @Override
    public void setConditions(com.sforce.soap.partner.ISoqlWhereCondition[] conditions) {
      this.conditions = castArray(com.sforce.soap.partner.SoqlWhereCondition.class, conditions);
      conditions__is_set = true;
    }

    /**
     * element : conjunction of type {urn:partner.soap.sforce.com}soqlConjunction
     * java type: com.sforce.soap.partner.SoqlConjunction
     */
    private static final com.sforce.ws.bind.TypeInfo conjunction__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","conjunction","urn:partner.soap.sforce.com","soqlConjunction",1,1,true);

    private boolean conjunction__is_set = false;

    private com.sforce.soap.partner.SoqlConjunction conjunction;

    @Override
    public com.sforce.soap.partner.SoqlConjunction getConjunction() {
      return conjunction;
    }

    @Override
    public void setConjunction(com.sforce.soap.partner.SoqlConjunction conjunction) {
      this.conjunction = conjunction;
      conjunction__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:partner.soap.sforce.com", "SoqlConditionGroup");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, conditions__typeInfo, conditions, conditions__is_set);
       __typeMapper.writeObject(__out, conjunction__typeInfo, conjunction, conjunction__is_set);
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
        super.loadFields(__in, __typeMapper);
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, conditions__typeInfo)) {
            setConditions((com.sforce.soap.partner.SoqlWhereCondition[])__typeMapper.readObject(__in, conditions__typeInfo, com.sforce.soap.partner.SoqlWhereCondition[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, conjunction__typeInfo)) {
            setConjunction((com.sforce.soap.partner.SoqlConjunction)__typeMapper.readObject(__in, conjunction__typeInfo, com.sforce.soap.partner.SoqlConjunction.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[SoqlConditionGroup ");
      sb.append(super.toString());sb.append(" conditions='").append(com.sforce.ws.util.Verbose.toString(conditions)).append("'\n");
      sb.append(" conjunction='").append(com.sforce.ws.util.Verbose.toString(conjunction)).append("'\n");
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
