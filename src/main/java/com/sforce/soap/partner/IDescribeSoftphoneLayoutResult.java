package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public interface IDescribeSoftphoneLayoutResult  {

      /**
       * element : callTypes of type {urn:partner.soap.sforce.com}DescribeSoftphoneLayoutCallType
       * java type: com.sforce.soap.partner.DescribeSoftphoneLayoutCallType[]
       */

      public com.sforce.soap.partner.IDescribeSoftphoneLayoutCallType[] getCallTypes();

      public void setCallTypes(com.sforce.soap.partner.IDescribeSoftphoneLayoutCallType[] callTypes);

      /**
       * element : id of type {urn:partner.soap.sforce.com}ID
       * java type: java.lang.String
       */

      public java.lang.String getId();

      public void setId(java.lang.String id);

      /**
       * element : name of type {http://www.w3.org/2001/XMLSchema}string
       * java type: java.lang.String
       */

      public java.lang.String getName();

      public void setName(java.lang.String name);


}
