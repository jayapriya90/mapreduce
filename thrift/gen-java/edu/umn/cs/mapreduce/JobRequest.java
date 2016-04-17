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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-04-17")
public class JobRequest implements org.apache.thrift.TBase<JobRequest, JobRequest._Fields>, java.io.Serializable, Cloneable, Comparable<JobRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("JobRequest");

  private static final org.apache.thrift.protocol.TField INPUT_DIR_FIELD_DESC = new org.apache.thrift.protocol.TField("inputDir", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField OUTPUT_DIR_FIELD_DESC = new org.apache.thrift.protocol.TField("outputDir", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CHUNK_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("chunkSize", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new JobRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new JobRequestTupleSchemeFactory());
  }

  public String inputDir; // required
  public String outputDir; // required
  public long chunkSize; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    INPUT_DIR((short)1, "inputDir"),
    OUTPUT_DIR((short)2, "outputDir"),
    CHUNK_SIZE((short)3, "chunkSize");

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
        case 1: // INPUT_DIR
          return INPUT_DIR;
        case 2: // OUTPUT_DIR
          return OUTPUT_DIR;
        case 3: // CHUNK_SIZE
          return CHUNK_SIZE;
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
  private static final int __CHUNKSIZE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.CHUNK_SIZE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INPUT_DIR, new org.apache.thrift.meta_data.FieldMetaData("inputDir", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OUTPUT_DIR, new org.apache.thrift.meta_data.FieldMetaData("outputDir", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHUNK_SIZE, new org.apache.thrift.meta_data.FieldMetaData("chunkSize", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(JobRequest.class, metaDataMap);
  }

  public JobRequest() {
  }

  public JobRequest(
    String inputDir,
    String outputDir)
  {
    this();
    this.inputDir = inputDir;
    this.outputDir = outputDir;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public JobRequest(JobRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetInputDir()) {
      this.inputDir = other.inputDir;
    }
    if (other.isSetOutputDir()) {
      this.outputDir = other.outputDir;
    }
    this.chunkSize = other.chunkSize;
  }

  public JobRequest deepCopy() {
    return new JobRequest(this);
  }

  @Override
  public void clear() {
    this.inputDir = null;
    this.outputDir = null;
    setChunkSizeIsSet(false);
    this.chunkSize = 0;
  }

  public String getInputDir() {
    return this.inputDir;
  }

  public JobRequest setInputDir(String inputDir) {
    this.inputDir = inputDir;
    return this;
  }

  public void unsetInputDir() {
    this.inputDir = null;
  }

  /** Returns true if field inputDir is set (has been assigned a value) and false otherwise */
  public boolean isSetInputDir() {
    return this.inputDir != null;
  }

  public void setInputDirIsSet(boolean value) {
    if (!value) {
      this.inputDir = null;
    }
  }

  public String getOutputDir() {
    return this.outputDir;
  }

  public JobRequest setOutputDir(String outputDir) {
    this.outputDir = outputDir;
    return this;
  }

  public void unsetOutputDir() {
    this.outputDir = null;
  }

  /** Returns true if field outputDir is set (has been assigned a value) and false otherwise */
  public boolean isSetOutputDir() {
    return this.outputDir != null;
  }

  public void setOutputDirIsSet(boolean value) {
    if (!value) {
      this.outputDir = null;
    }
  }

  public long getChunkSize() {
    return this.chunkSize;
  }

  public JobRequest setChunkSize(long chunkSize) {
    this.chunkSize = chunkSize;
    setChunkSizeIsSet(true);
    return this;
  }

  public void unsetChunkSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CHUNKSIZE_ISSET_ID);
  }

  /** Returns true if field chunkSize is set (has been assigned a value) and false otherwise */
  public boolean isSetChunkSize() {
    return EncodingUtils.testBit(__isset_bitfield, __CHUNKSIZE_ISSET_ID);
  }

  public void setChunkSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CHUNKSIZE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case INPUT_DIR:
      if (value == null) {
        unsetInputDir();
      } else {
        setInputDir((String)value);
      }
      break;

    case OUTPUT_DIR:
      if (value == null) {
        unsetOutputDir();
      } else {
        setOutputDir((String)value);
      }
      break;

    case CHUNK_SIZE:
      if (value == null) {
        unsetChunkSize();
      } else {
        setChunkSize((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case INPUT_DIR:
      return getInputDir();

    case OUTPUT_DIR:
      return getOutputDir();

    case CHUNK_SIZE:
      return getChunkSize();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case INPUT_DIR:
      return isSetInputDir();
    case OUTPUT_DIR:
      return isSetOutputDir();
    case CHUNK_SIZE:
      return isSetChunkSize();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof JobRequest)
      return this.equals((JobRequest)that);
    return false;
  }

  public boolean equals(JobRequest that) {
    if (that == null)
      return false;

    boolean this_present_inputDir = true && this.isSetInputDir();
    boolean that_present_inputDir = true && that.isSetInputDir();
    if (this_present_inputDir || that_present_inputDir) {
      if (!(this_present_inputDir && that_present_inputDir))
        return false;
      if (!this.inputDir.equals(that.inputDir))
        return false;
    }

    boolean this_present_outputDir = true && this.isSetOutputDir();
    boolean that_present_outputDir = true && that.isSetOutputDir();
    if (this_present_outputDir || that_present_outputDir) {
      if (!(this_present_outputDir && that_present_outputDir))
        return false;
      if (!this.outputDir.equals(that.outputDir))
        return false;
    }

    boolean this_present_chunkSize = true && this.isSetChunkSize();
    boolean that_present_chunkSize = true && that.isSetChunkSize();
    if (this_present_chunkSize || that_present_chunkSize) {
      if (!(this_present_chunkSize && that_present_chunkSize))
        return false;
      if (this.chunkSize != that.chunkSize)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_inputDir = true && (isSetInputDir());
    list.add(present_inputDir);
    if (present_inputDir)
      list.add(inputDir);

    boolean present_outputDir = true && (isSetOutputDir());
    list.add(present_outputDir);
    if (present_outputDir)
      list.add(outputDir);

    boolean present_chunkSize = true && (isSetChunkSize());
    list.add(present_chunkSize);
    if (present_chunkSize)
      list.add(chunkSize);

    return list.hashCode();
  }

  @Override
  public int compareTo(JobRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetInputDir()).compareTo(other.isSetInputDir());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInputDir()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.inputDir, other.inputDir);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOutputDir()).compareTo(other.isSetOutputDir());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutputDir()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outputDir, other.outputDir);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChunkSize()).compareTo(other.isSetChunkSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChunkSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.chunkSize, other.chunkSize);
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
    StringBuilder sb = new StringBuilder("JobRequest(");
    boolean first = true;

    sb.append("inputDir:");
    if (this.inputDir == null) {
      sb.append("null");
    } else {
      sb.append(this.inputDir);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("outputDir:");
    if (this.outputDir == null) {
      sb.append("null");
    } else {
      sb.append(this.outputDir);
    }
    first = false;
    if (isSetChunkSize()) {
      if (!first) sb.append(", ");
      sb.append("chunkSize:");
      sb.append(this.chunkSize);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (inputDir == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'inputDir' was not present! Struct: " + toString());
    }
    if (outputDir == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'outputDir' was not present! Struct: " + toString());
    }
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

  private static class JobRequestStandardSchemeFactory implements SchemeFactory {
    public JobRequestStandardScheme getScheme() {
      return new JobRequestStandardScheme();
    }
  }

  private static class JobRequestStandardScheme extends StandardScheme<JobRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, JobRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // INPUT_DIR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.inputDir = iprot.readString();
              struct.setInputDirIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OUTPUT_DIR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.outputDir = iprot.readString();
              struct.setOutputDirIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CHUNK_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.chunkSize = iprot.readI64();
              struct.setChunkSizeIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, JobRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.inputDir != null) {
        oprot.writeFieldBegin(INPUT_DIR_FIELD_DESC);
        oprot.writeString(struct.inputDir);
        oprot.writeFieldEnd();
      }
      if (struct.outputDir != null) {
        oprot.writeFieldBegin(OUTPUT_DIR_FIELD_DESC);
        oprot.writeString(struct.outputDir);
        oprot.writeFieldEnd();
      }
      if (struct.isSetChunkSize()) {
        oprot.writeFieldBegin(CHUNK_SIZE_FIELD_DESC);
        oprot.writeI64(struct.chunkSize);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class JobRequestTupleSchemeFactory implements SchemeFactory {
    public JobRequestTupleScheme getScheme() {
      return new JobRequestTupleScheme();
    }
  }

  private static class JobRequestTupleScheme extends TupleScheme<JobRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, JobRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.inputDir);
      oprot.writeString(struct.outputDir);
      BitSet optionals = new BitSet();
      if (struct.isSetChunkSize()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetChunkSize()) {
        oprot.writeI64(struct.chunkSize);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, JobRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.inputDir = iprot.readString();
      struct.setInputDirIsSet(true);
      struct.outputDir = iprot.readString();
      struct.setOutputDirIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.chunkSize = iprot.readI64();
        struct.setChunkSizeIsSet(true);
      }
    }
  }

}

