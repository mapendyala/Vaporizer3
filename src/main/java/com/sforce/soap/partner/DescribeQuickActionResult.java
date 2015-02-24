package com.sforce.soap.partner;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class DescribeQuickActionResult implements com.sforce.ws.bind.XMLizable , IDescribeQuickActionResult{

    /**
     * Constructor
     */
    public DescribeQuickActionResult() {}

    /**
     * element : accessLevelRequired of type {urn:partner.soap.sforce.com}ShareAccessLevel
     * java type: com.sforce.soap.partner.ShareAccessLevel
     */
    private static final com.sforce.ws.bind.TypeInfo accessLevelRequired__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","accessLevelRequired","urn:partner.soap.sforce.com","ShareAccessLevel",1,1,true);

    private boolean accessLevelRequired__is_set = false;

    private com.sforce.soap.partner.ShareAccessLevel accessLevelRequired;

    @Override
    public com.sforce.soap.partner.ShareAccessLevel getAccessLevelRequired() {
      return accessLevelRequired;
    }

    @Override
    public void setAccessLevelRequired(com.sforce.soap.partner.ShareAccessLevel accessLevelRequired) {
      this.accessLevelRequired = accessLevelRequired;
      accessLevelRequired__is_set = true;
    }

    /**
     * element : canvasApplicationName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo canvasApplicationName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","canvasApplicationName","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean canvasApplicationName__is_set = false;

    private java.lang.String canvasApplicationName;

    @Override
    public java.lang.String getCanvasApplicationName() {
      return canvasApplicationName;
    }

    @Override
    public void setCanvasApplicationName(java.lang.String canvasApplicationName) {
      this.canvasApplicationName = canvasApplicationName;
      canvasApplicationName__is_set = true;
    }

    /**
     * element : colors of type {urn:partner.soap.sforce.com}DescribeColor
     * java type: com.sforce.soap.partner.DescribeColor[]
     */
    private static final com.sforce.ws.bind.TypeInfo colors__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","colors","urn:partner.soap.sforce.com","DescribeColor",0,-1,true);

    private boolean colors__is_set = false;

    private com.sforce.soap.partner.DescribeColor[] colors = new com.sforce.soap.partner.DescribeColor[0];

    @Override
    public com.sforce.soap.partner.DescribeColor[] getColors() {
      return colors;
    }

    @Override
    public void setColors(com.sforce.soap.partner.IDescribeColor[] colors) {
      this.colors = castArray(com.sforce.soap.partner.DescribeColor.class, colors);
      colors__is_set = true;
    }

    /**
     * element : contextSobjectType of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo contextSobjectType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","contextSobjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean contextSobjectType__is_set = false;

    private java.lang.String contextSobjectType;

    @Override
    public java.lang.String getContextSobjectType() {
      return contextSobjectType;
    }

    @Override
    public void setContextSobjectType(java.lang.String contextSobjectType) {
      this.contextSobjectType = contextSobjectType;
      contextSobjectType__is_set = true;
    }

    /**
     * element : defaultValues of type {urn:partner.soap.sforce.com}DescribeQuickActionDefaultValue
     * java type: com.sforce.soap.partner.DescribeQuickActionDefaultValue[]
     */
    private static final com.sforce.ws.bind.TypeInfo defaultValues__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","defaultValues","urn:partner.soap.sforce.com","DescribeQuickActionDefaultValue",0,-1,true);

    private boolean defaultValues__is_set = false;

    private com.sforce.soap.partner.DescribeQuickActionDefaultValue[] defaultValues = new com.sforce.soap.partner.DescribeQuickActionDefaultValue[0];

    @Override
    public com.sforce.soap.partner.DescribeQuickActionDefaultValue[] getDefaultValues() {
      return defaultValues;
    }

    @Override
    public void setDefaultValues(com.sforce.soap.partner.IDescribeQuickActionDefaultValue[] defaultValues) {
      this.defaultValues = castArray(com.sforce.soap.partner.DescribeQuickActionDefaultValue.class, defaultValues);
      defaultValues__is_set = true;
    }

    /**
     * element : height of type {http://www.w3.org/2001/XMLSchema}int
     * java type: java.lang.Integer
     */
    private static final com.sforce.ws.bind.TypeInfo height__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","height","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean height__is_set = false;

    private java.lang.Integer height;

    @Override
    public java.lang.Integer getHeight() {
      return height;
    }

    @Override
    public void setHeight(java.lang.Integer height) {
      this.height = height;
      height__is_set = true;
    }

    /**
     * element : iconName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo iconName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","iconName","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean iconName__is_set = false;

    private java.lang.String iconName;

    @Override
    public java.lang.String getIconName() {
      return iconName;
    }

    @Override
    public void setIconName(java.lang.String iconName) {
      this.iconName = iconName;
      iconName__is_set = true;
    }

    /**
     * element : iconUrl of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo iconUrl__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","iconUrl","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean iconUrl__is_set = false;

    private java.lang.String iconUrl;

    @Override
    public java.lang.String getIconUrl() {
      return iconUrl;
    }

    @Override
    public void setIconUrl(java.lang.String iconUrl) {
      this.iconUrl = iconUrl;
      iconUrl__is_set = true;
    }

    /**
     * element : icons of type {urn:partner.soap.sforce.com}DescribeIcon
     * java type: com.sforce.soap.partner.DescribeIcon[]
     */
    private static final com.sforce.ws.bind.TypeInfo icons__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","icons","urn:partner.soap.sforce.com","DescribeIcon",0,-1,true);

    private boolean icons__is_set = false;

    private com.sforce.soap.partner.DescribeIcon[] icons = new com.sforce.soap.partner.DescribeIcon[0];

    @Override
    public com.sforce.soap.partner.DescribeIcon[] getIcons() {
      return icons;
    }

    @Override
    public void setIcons(com.sforce.soap.partner.IDescribeIcon[] icons) {
      this.icons = castArray(com.sforce.soap.partner.DescribeIcon.class, icons);
      icons__is_set = true;
    }

    /**
     * element : label of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo label__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","label","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean label__is_set = false;

    private java.lang.String label;

    @Override
    public java.lang.String getLabel() {
      return label;
    }

    @Override
    public void setLabel(java.lang.String label) {
      this.label = label;
      label__is_set = true;
    }

    /**
     * element : layout of type {urn:partner.soap.sforce.com}DescribeLayoutSection
     * java type: com.sforce.soap.partner.DescribeLayoutSection[]
     */
    private static final com.sforce.ws.bind.TypeInfo layout__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","layout","urn:partner.soap.sforce.com","DescribeLayoutSection",0,-1,true);

    private boolean layout__is_set = false;

    private com.sforce.soap.partner.DescribeLayoutSection[] layout = new com.sforce.soap.partner.DescribeLayoutSection[0];

    @Override
    public com.sforce.soap.partner.DescribeLayoutSection[] getLayout() {
      return layout;
    }

    @Override
    public void setLayout(com.sforce.soap.partner.IDescribeLayoutSection[] layout) {
      this.layout = castArray(com.sforce.soap.partner.DescribeLayoutSection.class, layout);
      layout__is_set = true;
    }

    /**
     * element : miniIconUrl of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo miniIconUrl__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","miniIconUrl","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean miniIconUrl__is_set = false;

    private java.lang.String miniIconUrl;

    @Override
    public java.lang.String getMiniIconUrl() {
      return miniIconUrl;
    }

    @Override
    public void setMiniIconUrl(java.lang.String miniIconUrl) {
      this.miniIconUrl = miniIconUrl;
      miniIconUrl__is_set = true;
    }

    /**
     * element : name of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo name__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","name","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean name__is_set = false;

    private java.lang.String name;

    @Override
    public java.lang.String getName() {
      return name;
    }

    @Override
    public void setName(java.lang.String name) {
      this.name = name;
      name__is_set = true;
    }

    /**
     * element : targetParentField of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo targetParentField__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","targetParentField","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean targetParentField__is_set = false;

    private java.lang.String targetParentField;

    @Override
    public java.lang.String getTargetParentField() {
      return targetParentField;
    }

    @Override
    public void setTargetParentField(java.lang.String targetParentField) {
      this.targetParentField = targetParentField;
      targetParentField__is_set = true;
    }

    /**
     * element : targetRecordTypeId of type {urn:partner.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo targetRecordTypeId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","targetRecordTypeId","urn:partner.soap.sforce.com","ID",1,1,true);

    private boolean targetRecordTypeId__is_set = false;

    private java.lang.String targetRecordTypeId;

    @Override
    public java.lang.String getTargetRecordTypeId() {
      return targetRecordTypeId;
    }

    @Override
    public void setTargetRecordTypeId(java.lang.String targetRecordTypeId) {
      this.targetRecordTypeId = targetRecordTypeId;
      targetRecordTypeId__is_set = true;
    }

    /**
     * element : targetSobjectType of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo targetSobjectType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","targetSobjectType","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean targetSobjectType__is_set = false;

    private java.lang.String targetSobjectType;

    @Override
    public java.lang.String getTargetSobjectType() {
      return targetSobjectType;
    }

    @Override
    public void setTargetSobjectType(java.lang.String targetSobjectType) {
      this.targetSobjectType = targetSobjectType;
      targetSobjectType__is_set = true;
    }

    /**
     * element : type of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo type__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","type","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean type__is_set = false;

    private java.lang.String type;

    @Override
    public java.lang.String getType() {
      return type;
    }

    @Override
    public void setType(java.lang.String type) {
      this.type = type;
      type__is_set = true;
    }

    /**
     * element : visualforcePageName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo visualforcePageName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","visualforcePageName","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean visualforcePageName__is_set = false;

    private java.lang.String visualforcePageName;

    @Override
    public java.lang.String getVisualforcePageName() {
      return visualforcePageName;
    }

    @Override
    public void setVisualforcePageName(java.lang.String visualforcePageName) {
      this.visualforcePageName = visualforcePageName;
      visualforcePageName__is_set = true;
    }

    /**
     * element : width of type {http://www.w3.org/2001/XMLSchema}int
     * java type: java.lang.Integer
     */
    private static final com.sforce.ws.bind.TypeInfo width__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:partner.soap.sforce.com","width","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean width__is_set = false;

    private java.lang.Integer width;

    @Override
    public java.lang.Integer getWidth() {
      return width;
    }

    @Override
    public void setWidth(java.lang.Integer width) {
      this.width = width;
      width__is_set = true;
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
       __typeMapper.writeObject(__out, accessLevelRequired__typeInfo, accessLevelRequired, accessLevelRequired__is_set);
       __typeMapper.writeString(__out, canvasApplicationName__typeInfo, canvasApplicationName, canvasApplicationName__is_set);
       __typeMapper.writeObject(__out, colors__typeInfo, colors, colors__is_set);
       __typeMapper.writeString(__out, contextSobjectType__typeInfo, contextSobjectType, contextSobjectType__is_set);
       __typeMapper.writeObject(__out, defaultValues__typeInfo, defaultValues, defaultValues__is_set);
       __typeMapper.writeObject(__out, height__typeInfo, height, height__is_set);
       __typeMapper.writeString(__out, iconName__typeInfo, iconName, iconName__is_set);
       __typeMapper.writeString(__out, iconUrl__typeInfo, iconUrl, iconUrl__is_set);
       __typeMapper.writeObject(__out, icons__typeInfo, icons, icons__is_set);
       __typeMapper.writeString(__out, label__typeInfo, label, label__is_set);
       __typeMapper.writeObject(__out, layout__typeInfo, layout, layout__is_set);
       __typeMapper.writeString(__out, miniIconUrl__typeInfo, miniIconUrl, miniIconUrl__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeString(__out, targetParentField__typeInfo, targetParentField, targetParentField__is_set);
       __typeMapper.writeString(__out, targetRecordTypeId__typeInfo, targetRecordTypeId, targetRecordTypeId__is_set);
       __typeMapper.writeString(__out, targetSobjectType__typeInfo, targetSobjectType, targetSobjectType__is_set);
       __typeMapper.writeString(__out, type__typeInfo, type, type__is_set);
       __typeMapper.writeString(__out, visualforcePageName__typeInfo, visualforcePageName, visualforcePageName__is_set);
       __typeMapper.writeObject(__out, width__typeInfo, width, width__is_set);
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
        if (__typeMapper.verifyElement(__in, accessLevelRequired__typeInfo)) {
            setAccessLevelRequired((com.sforce.soap.partner.ShareAccessLevel)__typeMapper.readObject(__in, accessLevelRequired__typeInfo, com.sforce.soap.partner.ShareAccessLevel.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, canvasApplicationName__typeInfo)) {
            setCanvasApplicationName(__typeMapper.readString(__in, canvasApplicationName__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, colors__typeInfo)) {
            setColors((com.sforce.soap.partner.DescribeColor[])__typeMapper.readObject(__in, colors__typeInfo, com.sforce.soap.partner.DescribeColor[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, contextSobjectType__typeInfo)) {
            setContextSobjectType(__typeMapper.readString(__in, contextSobjectType__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, defaultValues__typeInfo)) {
            setDefaultValues((com.sforce.soap.partner.DescribeQuickActionDefaultValue[])__typeMapper.readObject(__in, defaultValues__typeInfo, com.sforce.soap.partner.DescribeQuickActionDefaultValue[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, height__typeInfo)) {
            setHeight((java.lang.Integer)__typeMapper.readObject(__in, height__typeInfo, java.lang.Integer.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, iconName__typeInfo)) {
            setIconName(__typeMapper.readString(__in, iconName__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, iconUrl__typeInfo)) {
            setIconUrl(__typeMapper.readString(__in, iconUrl__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, icons__typeInfo)) {
            setIcons((com.sforce.soap.partner.DescribeIcon[])__typeMapper.readObject(__in, icons__typeInfo, com.sforce.soap.partner.DescribeIcon[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, label__typeInfo)) {
            setLabel(__typeMapper.readString(__in, label__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, layout__typeInfo)) {
            setLayout((com.sforce.soap.partner.DescribeLayoutSection[])__typeMapper.readObject(__in, layout__typeInfo, com.sforce.soap.partner.DescribeLayoutSection[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, miniIconUrl__typeInfo)) {
            setMiniIconUrl(__typeMapper.readString(__in, miniIconUrl__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, targetParentField__typeInfo)) {
            setTargetParentField(__typeMapper.readString(__in, targetParentField__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, targetRecordTypeId__typeInfo)) {
            setTargetRecordTypeId(__typeMapper.readString(__in, targetRecordTypeId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, targetSobjectType__typeInfo)) {
            setTargetSobjectType(__typeMapper.readString(__in, targetSobjectType__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, type__typeInfo)) {
            setType(__typeMapper.readString(__in, type__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, visualforcePageName__typeInfo)) {
            setVisualforcePageName(__typeMapper.readString(__in, visualforcePageName__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, width__typeInfo)) {
            setWidth((java.lang.Integer)__typeMapper.readObject(__in, width__typeInfo, java.lang.Integer.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[DescribeQuickActionResult ");
      sb.append(" accessLevelRequired='").append(com.sforce.ws.util.Verbose.toString(accessLevelRequired)).append("'\n");
      sb.append(" canvasApplicationName='").append(com.sforce.ws.util.Verbose.toString(canvasApplicationName)).append("'\n");
      sb.append(" colors='").append(com.sforce.ws.util.Verbose.toString(colors)).append("'\n");
      sb.append(" contextSobjectType='").append(com.sforce.ws.util.Verbose.toString(contextSobjectType)).append("'\n");
      sb.append(" defaultValues='").append(com.sforce.ws.util.Verbose.toString(defaultValues)).append("'\n");
      sb.append(" height='").append(com.sforce.ws.util.Verbose.toString(height)).append("'\n");
      sb.append(" iconName='").append(com.sforce.ws.util.Verbose.toString(iconName)).append("'\n");
      sb.append(" iconUrl='").append(com.sforce.ws.util.Verbose.toString(iconUrl)).append("'\n");
      sb.append(" icons='").append(com.sforce.ws.util.Verbose.toString(icons)).append("'\n");
      sb.append(" label='").append(com.sforce.ws.util.Verbose.toString(label)).append("'\n");
      sb.append(" layout='").append(com.sforce.ws.util.Verbose.toString(layout)).append("'\n");
      sb.append(" miniIconUrl='").append(com.sforce.ws.util.Verbose.toString(miniIconUrl)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" targetParentField='").append(com.sforce.ws.util.Verbose.toString(targetParentField)).append("'\n");
      sb.append(" targetRecordTypeId='").append(com.sforce.ws.util.Verbose.toString(targetRecordTypeId)).append("'\n");
      sb.append(" targetSobjectType='").append(com.sforce.ws.util.Verbose.toString(targetSobjectType)).append("'\n");
      sb.append(" type='").append(com.sforce.ws.util.Verbose.toString(type)).append("'\n");
      sb.append(" visualforcePageName='").append(com.sforce.ws.util.Verbose.toString(visualforcePageName)).append("'\n");
      sb.append(" width='").append(com.sforce.ws.util.Verbose.toString(width)).append("'\n");
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
