package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public interface IRecordTypeMapping  {

      /**
       * element : available of type {http://www.w3.org/2001/XMLSchema}boolean
       * java type: boolean
       */

      public boolean getAvailable();

      public boolean isAvailable();

      public void setAvailable(boolean available);

      /**
       * element : defaultRecordTypeMapping of type {http://www.w3.org/2001/XMLSchema}boolean
       * java type: boolean
       */

      public boolean getDefaultRecordTypeMapping();

      public boolean isDefaultRecordTypeMapping();

      public void setDefaultRecordTypeMapping(boolean defaultRecordTypeMapping);

      /**
       * element : layoutId of type {urn:partner.soap.sforce.com}ID
       * java type: java.lang.String
       */

      public java.lang.String getLayoutId();

      public void setLayoutId(java.lang.String layoutId);

      /**
       * element : name of type {http://www.w3.org/2001/XMLSchema}string
       * java type: java.lang.String
       */

      public java.lang.String getName();

      public void setName(java.lang.String name);

      /**
       * element : picklistsForRecordType of type {urn:partner.soap.sforce.com}PicklistForRecordType
       * java type: com.sforce.soap.partner.PicklistForRecordType[]
       */

      public com.sforce.soap.partner.IPicklistForRecordType[] getPicklistsForRecordType();

      public void setPicklistsForRecordType(com.sforce.soap.partner.IPicklistForRecordType[] picklistsForRecordType);

      /**
       * element : recordTypeId of type {urn:partner.soap.sforce.com}ID
       * java type: java.lang.String
       */

      public java.lang.String getRecordTypeId();

      public void setRecordTypeId(java.lang.String recordTypeId);


}
