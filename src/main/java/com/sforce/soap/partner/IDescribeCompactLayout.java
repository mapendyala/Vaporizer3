package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public interface IDescribeCompactLayout  {

      /**
       * element : actions of type {urn:partner.soap.sforce.com}DescribeLayoutButton
       * java type: com.sforce.soap.partner.DescribeLayoutButton[]
       */

      public com.sforce.soap.partner.IDescribeLayoutButton[] getActions();

      public void setActions(com.sforce.soap.partner.IDescribeLayoutButton[] actions);

      /**
       * element : fieldItems of type {urn:partner.soap.sforce.com}DescribeLayoutItem
       * java type: com.sforce.soap.partner.DescribeLayoutItem[]
       */

      public com.sforce.soap.partner.IDescribeLayoutItem[] getFieldItems();

      public void setFieldItems(com.sforce.soap.partner.IDescribeLayoutItem[] fieldItems);

      /**
       * element : id of type {urn:partner.soap.sforce.com}ID
       * java type: java.lang.String
       */

      public java.lang.String getId();

      public void setId(java.lang.String id);

      /**
       * element : imageItems of type {urn:partner.soap.sforce.com}DescribeLayoutItem
       * java type: com.sforce.soap.partner.DescribeLayoutItem[]
       */

      public com.sforce.soap.partner.IDescribeLayoutItem[] getImageItems();

      public void setImageItems(com.sforce.soap.partner.IDescribeLayoutItem[] imageItems);

      /**
       * element : label of type {http://www.w3.org/2001/XMLSchema}string
       * java type: java.lang.String
       */

      public java.lang.String getLabel();

      public void setLabel(java.lang.String label);

      /**
       * element : name of type {http://www.w3.org/2001/XMLSchema}string
       * java type: java.lang.String
       */

      public java.lang.String getName();

      public void setName(java.lang.String name);

      /**
       * element : objectType of type {http://www.w3.org/2001/XMLSchema}string
       * java type: java.lang.String
       */

      public java.lang.String getObjectType();

      public void setObjectType(java.lang.String objectType);


}
