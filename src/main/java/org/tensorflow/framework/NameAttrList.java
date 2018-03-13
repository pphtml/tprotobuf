// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tensorflow/core/framework/attr_value.proto

package org.tensorflow.framework;

/**
 * <pre>
 * A list of attr names and their values. The whole list is attached
 * with a string name.  E.g., MatMul[T=float].
 * </pre>
 *
 * Protobuf type {@code tensorflow.NameAttrList}
 */
public  final class NameAttrList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:tensorflow.NameAttrList)
    NameAttrListOrBuilder {
private static final long serialVersionUID = 0L;
  // Use NameAttrList.newBuilder() to construct.
  private NameAttrList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NameAttrList() {
    name_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private NameAttrList(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              attr_ = com.google.protobuf.MapField.newMapField(
                  AttrDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000002;
            }
            com.google.protobuf.MapEntry<java.lang.String, org.tensorflow.framework.AttrValue>
            attr__ = input.readMessage(
                AttrDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            attr_.getMutableMap().put(
                attr__.getKey(), attr__.getValue());
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.tensorflow.framework.AttrValueProtos.internal_static_tensorflow_NameAttrList_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 2:
        return internalGetAttr();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.tensorflow.framework.AttrValueProtos.internal_static_tensorflow_NameAttrList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.tensorflow.framework.NameAttrList.class, org.tensorflow.framework.NameAttrList.Builder.class);
  }

  private int bitField0_;
  public static final int NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object name_;
  /**
   * <code>string name = 1;</code>
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <code>string name = 1;</code>
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ATTR_FIELD_NUMBER = 2;
  private static final class AttrDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.String, org.tensorflow.framework.AttrValue> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.String, org.tensorflow.framework.AttrValue>newDefaultInstance(
                org.tensorflow.framework.AttrValueProtos.internal_static_tensorflow_NameAttrList_AttrEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.MESSAGE,
                org.tensorflow.framework.AttrValue.getDefaultInstance());
  }
  private com.google.protobuf.MapField<
      java.lang.String, org.tensorflow.framework.AttrValue> attr_;
  private com.google.protobuf.MapField<java.lang.String, org.tensorflow.framework.AttrValue>
  internalGetAttr() {
    if (attr_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          AttrDefaultEntryHolder.defaultEntry);
    }
    return attr_;
  }

  public int getAttrCount() {
    return internalGetAttr().getMap().size();
  }
  /**
   * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
   */

  public boolean containsAttr(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    return internalGetAttr().getMap().containsKey(key);
  }
  /**
   * Use {@link #getAttrMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> getAttr() {
    return getAttrMap();
  }
  /**
   * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
   */

  public java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> getAttrMap() {
    return internalGetAttr().getMap();
  }
  /**
   * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
   */

  public org.tensorflow.framework.AttrValue getAttrOrDefault(
      java.lang.String key,
      org.tensorflow.framework.AttrValue defaultValue) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> map =
        internalGetAttr().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
   */

  public org.tensorflow.framework.AttrValue getAttrOrThrow(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> map =
        internalGetAttr().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
    }
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetAttr(),
        AttrDefaultEntryHolder.defaultEntry,
        2);
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
    }
    for (java.util.Map.Entry<java.lang.String, org.tensorflow.framework.AttrValue> entry
         : internalGetAttr().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, org.tensorflow.framework.AttrValue>
      attr__ = AttrDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, attr__);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.tensorflow.framework.NameAttrList)) {
      return super.equals(obj);
    }
    org.tensorflow.framework.NameAttrList other = (org.tensorflow.framework.NameAttrList) obj;

    boolean result = true;
    result = result && getName()
        .equals(other.getName());
    result = result && internalGetAttr().equals(
        other.internalGetAttr());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    if (!internalGetAttr().getMap().isEmpty()) {
      hash = (37 * hash) + ATTR_FIELD_NUMBER;
      hash = (53 * hash) + internalGetAttr().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.tensorflow.framework.NameAttrList parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.tensorflow.framework.NameAttrList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.tensorflow.framework.NameAttrList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.tensorflow.framework.NameAttrList parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.tensorflow.framework.NameAttrList prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * A list of attr names and their values. The whole list is attached
   * with a string name.  E.g., MatMul[T=float].
   * </pre>
   *
   * Protobuf type {@code tensorflow.NameAttrList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:tensorflow.NameAttrList)
      org.tensorflow.framework.NameAttrListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.tensorflow.framework.AttrValueProtos.internal_static_tensorflow_NameAttrList_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetAttr();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetMutableAttr();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.tensorflow.framework.AttrValueProtos.internal_static_tensorflow_NameAttrList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.tensorflow.framework.NameAttrList.class, org.tensorflow.framework.NameAttrList.Builder.class);
    }

    // Construct using org.tensorflow.framework.NameAttrList.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      name_ = "";

      internalGetMutableAttr().clear();
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.tensorflow.framework.AttrValueProtos.internal_static_tensorflow_NameAttrList_descriptor;
    }

    public org.tensorflow.framework.NameAttrList getDefaultInstanceForType() {
      return org.tensorflow.framework.NameAttrList.getDefaultInstance();
    }

    public org.tensorflow.framework.NameAttrList build() {
      org.tensorflow.framework.NameAttrList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.tensorflow.framework.NameAttrList buildPartial() {
      org.tensorflow.framework.NameAttrList result = new org.tensorflow.framework.NameAttrList(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.name_ = name_;
      result.attr_ = internalGetAttr();
      result.attr_.makeImmutable();
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.tensorflow.framework.NameAttrList) {
        return mergeFrom((org.tensorflow.framework.NameAttrList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.tensorflow.framework.NameAttrList other) {
      if (other == org.tensorflow.framework.NameAttrList.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      internalGetMutableAttr().mergeFrom(
          other.internalGetAttr());
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.tensorflow.framework.NameAttrList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.tensorflow.framework.NameAttrList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object name_ = "";
    /**
     * <code>string name = 1;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.MapField<
        java.lang.String, org.tensorflow.framework.AttrValue> attr_;
    private com.google.protobuf.MapField<java.lang.String, org.tensorflow.framework.AttrValue>
    internalGetAttr() {
      if (attr_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            AttrDefaultEntryHolder.defaultEntry);
      }
      return attr_;
    }
    private com.google.protobuf.MapField<java.lang.String, org.tensorflow.framework.AttrValue>
    internalGetMutableAttr() {
      onChanged();;
      if (attr_ == null) {
        attr_ = com.google.protobuf.MapField.newMapField(
            AttrDefaultEntryHolder.defaultEntry);
      }
      if (!attr_.isMutable()) {
        attr_ = attr_.copy();
      }
      return attr_;
    }

    public int getAttrCount() {
      return internalGetAttr().getMap().size();
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */

    public boolean containsAttr(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      return internalGetAttr().getMap().containsKey(key);
    }
    /**
     * Use {@link #getAttrMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> getAttr() {
      return getAttrMap();
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */

    public java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> getAttrMap() {
      return internalGetAttr().getMap();
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */

    public org.tensorflow.framework.AttrValue getAttrOrDefault(
        java.lang.String key,
        org.tensorflow.framework.AttrValue defaultValue) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> map =
          internalGetAttr().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */

    public org.tensorflow.framework.AttrValue getAttrOrThrow(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> map =
          internalGetAttr().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearAttr() {
      internalGetMutableAttr().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */

    public Builder removeAttr(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      internalGetMutableAttr().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue>
    getMutableAttr() {
      return internalGetMutableAttr().getMutableMap();
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */
    public Builder putAttr(
        java.lang.String key,
        org.tensorflow.framework.AttrValue value) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      if (value == null) { throw new java.lang.NullPointerException(); }
      internalGetMutableAttr().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, .tensorflow.AttrValue&gt; attr = 2;</code>
     */

    public Builder putAllAttr(
        java.util.Map<java.lang.String, org.tensorflow.framework.AttrValue> values) {
      internalGetMutableAttr().getMutableMap()
          .putAll(values);
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:tensorflow.NameAttrList)
  }

  // @@protoc_insertion_point(class_scope:tensorflow.NameAttrList)
  private static final org.tensorflow.framework.NameAttrList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.tensorflow.framework.NameAttrList();
  }

  public static org.tensorflow.framework.NameAttrList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NameAttrList>
      PARSER = new com.google.protobuf.AbstractParser<NameAttrList>() {
    public NameAttrList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new NameAttrList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<NameAttrList> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NameAttrList> getParserForType() {
    return PARSER;
  }

  public org.tensorflow.framework.NameAttrList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

