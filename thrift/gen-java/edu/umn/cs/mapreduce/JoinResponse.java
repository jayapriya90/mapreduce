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
public class JoinResponse implements org.apache.thrift.TBase<JoinResponse, JoinResponse._Fields>, java.io.Serializable, Cloneable, Comparable<JoinResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("JoinResponse");

  private static final org.apache.thrift.protocol.TField FAIL_PROBABILITY_FIELD_DESC = new org.apache.thrift.protocol.TField("failProbability", org.apache.thrift.protocol.TType.DOUBLE, (short)1);
  private static final org.apache.thrift.protocol.TField HEARTBEAT_INTERVAL_FIELD_DESC = new org.apache.thrift.protocol.TField("heartbeatInterval", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new JoinResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new JoinResponseTupleSchemeFactory());
  }

  public double failProbability; // required
  public int heartbeatInterval; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FAIL_PROBABILITY((short)1, "failProbability"),
    HEARTBEAT_INTERVAL((short)2, "heartbeatInterval");

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
        case 1: // FAIL_PROBABILITY
          return FAIL_PROBABILITY;
        case 2: // HEARTBEAT_INTERVAL
          return HEARTBEAT_INTERVAL;
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
  private static final int __FAILPROBABILITY_ISSET_ID = 0;
  private static final int __HEARTBEATINTERVAL_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FAIL_PROBABILITY, new org.apache.thrift.meta_data.FieldMetaData("failProbability", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.HEARTBEAT_INTERVAL, new org.apache.thrift.meta_data.FieldMetaData("heartbeatInterval", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(JoinResponse.class, metaDataMap);
  }

  public JoinResponse() {
  }

  public JoinResponse(
    double failProbability,
    int heartbeatInterval)
  {
    this();
    this.failProbability = failProbability;
    setFailProbabilityIsSet(true);
    this.heartbeatInterval = heartbeatInterval;
    setHeartbeatIntervalIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public JoinResponse(JoinResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    this.failProbability = other.failProbability;
    this.heartbeatInterval = other.heartbeatInterval;
  }

  public JoinResponse deepCopy() {
    return new JoinResponse(this);
  }

  @Override
  public void clear() {
    setFailProbabilityIsSet(false);
    this.failProbability = 0.0;
    setHeartbeatIntervalIsSet(false);
    this.heartbeatInterval = 0;
  }

  public double getFailProbability() {
    return this.failProbability;
  }

  public JoinResponse setFailProbability(double failProbability) {
    this.failProbability = failProbability;
    setFailProbabilityIsSet(true);
    return this;
  }

  public void unsetFailProbability() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FAILPROBABILITY_ISSET_ID);
  }

  /** Returns true if field failProbability is set (has been assigned a value) and false otherwise */
  public boolean isSetFailProbability() {
    return EncodingUtils.testBit(__isset_bitfield, __FAILPROBABILITY_ISSET_ID);
  }

  public void setFailProbabilityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FAILPROBABILITY_ISSET_ID, value);
  }

  public int getHeartbeatInterval() {
    return this.heartbeatInterval;
  }

  public JoinResponse setHeartbeatInterval(int heartbeatInterval) {
    this.heartbeatInterval = heartbeatInterval;
    setHeartbeatIntervalIsSet(true);
    return this;
  }

  public void unsetHeartbeatInterval() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __HEARTBEATINTERVAL_ISSET_ID);
  }

  /** Returns true if field heartbeatInterval is set (has been assigned a value) and false otherwise */
  public boolean isSetHeartbeatInterval() {
    return EncodingUtils.testBit(__isset_bitfield, __HEARTBEATINTERVAL_ISSET_ID);
  }

  public void setHeartbeatIntervalIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __HEARTBEATINTERVAL_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FAIL_PROBABILITY:
      if (value == null) {
        unsetFailProbability();
      } else {
        setFailProbability((Double)value);
      }
      break;

    case HEARTBEAT_INTERVAL:
      if (value == null) {
        unsetHeartbeatInterval();
      } else {
        setHeartbeatInterval((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FAIL_PROBABILITY:
      return getFailProbability();

    case HEARTBEAT_INTERVAL:
      return getHeartbeatInterval();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FAIL_PROBABILITY:
      return isSetFailProbability();
    case HEARTBEAT_INTERVAL:
      return isSetHeartbeatInterval();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof JoinResponse)
      return this.equals((JoinResponse)that);
    return false;
  }

  public boolean equals(JoinResponse that) {
    if (that == null)
      return false;

    boolean this_present_failProbability = true;
    boolean that_present_failProbability = true;
    if (this_present_failProbability || that_present_failProbability) {
      if (!(this_present_failProbability && that_present_failProbability))
        return false;
      if (this.failProbability != that.failProbability)
        return false;
    }

    boolean this_present_heartbeatInterval = true;
    boolean that_present_heartbeatInterval = true;
    if (this_present_heartbeatInterval || that_present_heartbeatInterval) {
      if (!(this_present_heartbeatInterval && that_present_heartbeatInterval))
        return false;
      if (this.heartbeatInterval != that.heartbeatInterval)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_failProbability = true;
    list.add(present_failProbability);
    if (present_failProbability)
      list.add(failProbability);

    boolean present_heartbeatInterval = true;
    list.add(present_heartbeatInterval);
    if (present_heartbeatInterval)
      list.add(heartbeatInterval);

    return list.hashCode();
  }

  @Override
  public int compareTo(JoinResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFailProbability()).compareTo(other.isSetFailProbability());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFailProbability()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.failProbability, other.failProbability);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHeartbeatInterval()).compareTo(other.isSetHeartbeatInterval());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHeartbeatInterval()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.heartbeatInterval, other.heartbeatInterval);
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
    StringBuilder sb = new StringBuilder("JoinResponse(");
    boolean first = true;

    sb.append("failProbability:");
    sb.append(this.failProbability);
    first = false;
    if (!first) sb.append(", ");
    sb.append("heartbeatInterval:");
    sb.append(this.heartbeatInterval);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'failProbability' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'heartbeatInterval' because it's a primitive and you chose the non-beans generator.
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

  private static class JoinResponseStandardSchemeFactory implements SchemeFactory {
    public JoinResponseStandardScheme getScheme() {
      return new JoinResponseStandardScheme();
    }
  }

  private static class JoinResponseStandardScheme extends StandardScheme<JoinResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, JoinResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FAIL_PROBABILITY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.failProbability = iprot.readDouble();
              struct.setFailProbabilityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HEARTBEAT_INTERVAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.heartbeatInterval = iprot.readI32();
              struct.setHeartbeatIntervalIsSet(true);
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
      if (!struct.isSetFailProbability()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'failProbability' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetHeartbeatInterval()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'heartbeatInterval' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, JoinResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(FAIL_PROBABILITY_FIELD_DESC);
      oprot.writeDouble(struct.failProbability);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(HEARTBEAT_INTERVAL_FIELD_DESC);
      oprot.writeI32(struct.heartbeatInterval);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class JoinResponseTupleSchemeFactory implements SchemeFactory {
    public JoinResponseTupleScheme getScheme() {
      return new JoinResponseTupleScheme();
    }
  }

  private static class JoinResponseTupleScheme extends TupleScheme<JoinResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, JoinResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeDouble(struct.failProbability);
      oprot.writeI32(struct.heartbeatInterval);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, JoinResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.failProbability = iprot.readDouble();
      struct.setFailProbabilityIsSet(true);
      struct.heartbeatInterval = iprot.readI32();
      struct.setHeartbeatIntervalIsSet(true);
    }
  }

}
