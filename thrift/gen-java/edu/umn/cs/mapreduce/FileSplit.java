/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package edu.umn.cs.mapreduce;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-04-16")
public class FileSplit implements org.apache.thrift.TBase<FileSplit, FileSplit._Fields>, java.io.Serializable, Cloneable, Comparable<FileSplit> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FileSplit");

  private static final org.apache.thrift.protocol.TField FILENAME_FIELD_DESC = new org.apache.thrift.protocol.TField("filename", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField OFFSET_FIELD_DESC = new org.apache.thrift.protocol.TField("offset", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField LENGTH_FIELD_DESC = new org.apache.thrift.protocol.TField("length", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FileSplitStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FileSplitTupleSchemeFactory());
  }

  public String filename; // required
  public long offset; // required
  public long length; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILENAME((short)1, "filename"),
    OFFSET((short)2, "offset"),
    LENGTH((short)3, "length");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // FILENAME
          return FILENAME;
        case 2: // OFFSET
          return OFFSET;
        case 3: // LENGTH
          return LENGTH;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __OFFSET_ISSET_ID = 0;
  private static final int __LENGTH_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILENAME, new org.apache.thrift.meta_data.FieldMetaData("filename", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OFFSET, new org.apache.thrift.meta_data.FieldMetaData("offset", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LENGTH, new org.apache.thrift.meta_data.FieldMetaData("length", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FileSplit.class, metaDataMap);
  }

  public FileSplit() {
  }

  public FileSplit(
    String filename,
    long offset,
    long length)
  {
    this();
    this.filename = filename;
    this.offset = offset;
    setOffsetIsSet(true);
    this.length = length;
    setLengthIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FileSplit(FileSplit other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFilename()) {
      this.filename = other.filename;
    }
    this.offset = other.offset;
    this.length = other.length;
  }

  public FileSplit deepCopy() {
    return new FileSplit(this);
  }

  @Override
  public void clear() {
    this.filename = null;
    setOffsetIsSet(false);
    this.offset = 0;
    setLengthIsSet(false);
    this.length = 0;
  }

  public String getFilename() {
    return this.filename;
  }

  public FileSplit setFilename(String filename) {
    this.filename = filename;
    return this;
  }

  public void unsetFilename() {
    this.filename = null;
  }

  /** Returns true if field filename is set (has been assigned a value) and false otherwise */
  public boolean isSetFilename() {
    return this.filename != null;
  }

  public void setFilenameIsSet(boolean value) {
    if (!value) {
      this.filename = null;
    }
  }

  public long getOffset() {
    return this.offset;
  }

  public FileSplit setOffset(long offset) {
    this.offset = offset;
    setOffsetIsSet(true);
    return this;
  }

  public void unsetOffset() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OFFSET_ISSET_ID);
  }

  /** Returns true if field offset is set (has been assigned a value) and false otherwise */
  public boolean isSetOffset() {
    return EncodingUtils.testBit(__isset_bitfield, __OFFSET_ISSET_ID);
  }

  public void setOffsetIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OFFSET_ISSET_ID, value);
  }

  public long getLength() {
    return this.length;
  }

  public FileSplit setLength(long length) {
    this.length = length;
    setLengthIsSet(true);
    return this;
  }

  public void unsetLength() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LENGTH_ISSET_ID);
  }

  /** Returns true if field length is set (has been assigned a value) and false otherwise */
  public boolean isSetLength() {
    return EncodingUtils.testBit(__isset_bitfield, __LENGTH_ISSET_ID);
  }

  public void setLengthIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LENGTH_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FILENAME:
      if (value == null) {
        unsetFilename();
      } else {
        setFilename((String)value);
      }
      break;

    case OFFSET:
      if (value == null) {
        unsetOffset();
      } else {
        setOffset((Long)value);
      }
      break;

    case LENGTH:
      if (value == null) {
        unsetLength();
      } else {
        setLength((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FILENAME:
      return getFilename();

    case OFFSET:
      return getOffset();

    case LENGTH:
      return getLength();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FILENAME:
      return isSetFilename();
    case OFFSET:
      return isSetOffset();
    case LENGTH:
      return isSetLength();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FileSplit)
      return this.equals((FileSplit)that);
    return false;
  }

  public boolean equals(FileSplit that) {
    if (that == null)
      return false;

    boolean this_present_filename = true && this.isSetFilename();
    boolean that_present_filename = true && that.isSetFilename();
    if (this_present_filename || that_present_filename) {
      if (!(this_present_filename && that_present_filename))
        return false;
      if (!this.filename.equals(that.filename))
        return false;
    }

    boolean this_present_offset = true;
    boolean that_present_offset = true;
    if (this_present_offset || that_present_offset) {
      if (!(this_present_offset && that_present_offset))
        return false;
      if (this.offset != that.offset)
        return false;
    }

    boolean this_present_length = true;
    boolean that_present_length = true;
    if (this_present_length || that_present_length) {
      if (!(this_present_length && that_present_length))
        return false;
      if (this.length != that.length)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_filename = true && (isSetFilename());
    list.add(present_filename);
    if (present_filename)
      list.add(filename);

    boolean present_offset = true;
    list.add(present_offset);
    if (present_offset)
      list.add(offset);

    boolean present_length = true;
    list.add(present_length);
    if (present_length)
      list.add(length);

    return list.hashCode();
  }

  @Override
  public int compareTo(FileSplit other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFilename()).compareTo(other.isSetFilename());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFilename()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.filename, other.filename);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOffset()).compareTo(other.isSetOffset());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOffset()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.offset, other.offset);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLength()).compareTo(other.isSetLength());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLength()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.length, other.length);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FileSplit(");
    boolean first = true;

    sb.append("filename:");
    if (this.filename == null) {
      sb.append("null");
    } else {
      sb.append(this.filename);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("offset:");
    sb.append(this.offset);
    first = false;
    if (!first) sb.append(", ");
    sb.append("length:");
    sb.append(this.length);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (filename == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'filename' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'offset' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'length' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FileSplitStandardSchemeFactory implements SchemeFactory {
    public FileSplitStandardScheme getScheme() {
      return new FileSplitStandardScheme();
    }
  }

  private static class FileSplitStandardScheme extends StandardScheme<FileSplit> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FileSplit struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILENAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.filename = iprot.readString();
              struct.setFilenameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OFFSET
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.offset = iprot.readI64();
              struct.setOffsetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LENGTH
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.length = iprot.readI64();
              struct.setLengthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetOffset()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'offset' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetLength()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'length' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FileSplit struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.filename != null) {
        oprot.writeFieldBegin(FILENAME_FIELD_DESC);
        oprot.writeString(struct.filename);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(OFFSET_FIELD_DESC);
      oprot.writeI64(struct.offset);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LENGTH_FIELD_DESC);
      oprot.writeI64(struct.length);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FileSplitTupleSchemeFactory implements SchemeFactory {
    public FileSplitTupleScheme getScheme() {
      return new FileSplitTupleScheme();
    }
  }

  private static class FileSplitTupleScheme extends TupleScheme<FileSplit> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FileSplit struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.filename);
      oprot.writeI64(struct.offset);
      oprot.writeI64(struct.length);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FileSplit struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.filename = iprot.readString();
      struct.setFilenameIsSet(true);
      struct.offset = iprot.readI64();
      struct.setOffsetIsSet(true);
      struct.length = iprot.readI64();
      struct.setLengthIsSet(true);
    }
  }

}

