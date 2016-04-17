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
public class JobStats implements org.apache.thrift.TBase<JobStats, JobStats._Fields>, java.io.Serializable, Cloneable, Comparable<JobStats> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("JobStats");

  private static final org.apache.thrift.protocol.TField NUM_SPLITS_FIELD_DESC = new org.apache.thrift.protocol.TField("numSplits", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField TOTAL_SORT_JOBS_FIELD_DESC = new org.apache.thrift.protocol.TField("totalSortJobs", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TOTAL_SUCCESSFUL_FIELD_DESC = new org.apache.thrift.protocol.TField("totalSuccessful", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField TOTAL_KILLED_FIELD_DESC = new org.apache.thrift.protocol.TField("totalKilled", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField TOTAL_FAILED_FIELD_DESC = new org.apache.thrift.protocol.TField("totalFailed", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new JobStatsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new JobStatsTupleSchemeFactory());
  }

  public int numSplits; // required
  public int totalSortJobs; // required
  public int totalSuccessful; // required
  public int totalKilled; // required
  public int totalFailed; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NUM_SPLITS((short)1, "numSplits"),
    TOTAL_SORT_JOBS((short)2, "totalSortJobs"),
    TOTAL_SUCCESSFUL((short)3, "totalSuccessful"),
    TOTAL_KILLED((short)4, "totalKilled"),
    TOTAL_FAILED((short)5, "totalFailed");

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
        case 1: // NUM_SPLITS
          return NUM_SPLITS;
        case 2: // TOTAL_SORT_JOBS
          return TOTAL_SORT_JOBS;
        case 3: // TOTAL_SUCCESSFUL
          return TOTAL_SUCCESSFUL;
        case 4: // TOTAL_KILLED
          return TOTAL_KILLED;
        case 5: // TOTAL_FAILED
          return TOTAL_FAILED;
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
  private static final int __NUMSPLITS_ISSET_ID = 0;
  private static final int __TOTALSORTJOBS_ISSET_ID = 1;
  private static final int __TOTALSUCCESSFUL_ISSET_ID = 2;
  private static final int __TOTALKILLED_ISSET_ID = 3;
  private static final int __TOTALFAILED_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NUM_SPLITS, new org.apache.thrift.meta_data.FieldMetaData("numSplits", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_SORT_JOBS, new org.apache.thrift.meta_data.FieldMetaData("totalSortJobs", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_SUCCESSFUL, new org.apache.thrift.meta_data.FieldMetaData("totalSuccessful", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_KILLED, new org.apache.thrift.meta_data.FieldMetaData("totalKilled", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_FAILED, new org.apache.thrift.meta_data.FieldMetaData("totalFailed", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(JobStats.class, metaDataMap);
  }

  public JobStats() {
  }

  public JobStats(
    int numSplits,
    int totalSortJobs,
    int totalSuccessful,
    int totalKilled,
    int totalFailed)
  {
    this();
    this.numSplits = numSplits;
    setNumSplitsIsSet(true);
    this.totalSortJobs = totalSortJobs;
    setTotalSortJobsIsSet(true);
    this.totalSuccessful = totalSuccessful;
    setTotalSuccessfulIsSet(true);
    this.totalKilled = totalKilled;
    setTotalKilledIsSet(true);
    this.totalFailed = totalFailed;
    setTotalFailedIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public JobStats(JobStats other) {
    __isset_bitfield = other.__isset_bitfield;
    this.numSplits = other.numSplits;
    this.totalSortJobs = other.totalSortJobs;
    this.totalSuccessful = other.totalSuccessful;
    this.totalKilled = other.totalKilled;
    this.totalFailed = other.totalFailed;
  }

  public JobStats deepCopy() {
    return new JobStats(this);
  }

  @Override
  public void clear() {
    setNumSplitsIsSet(false);
    this.numSplits = 0;
    setTotalSortJobsIsSet(false);
    this.totalSortJobs = 0;
    setTotalSuccessfulIsSet(false);
    this.totalSuccessful = 0;
    setTotalKilledIsSet(false);
    this.totalKilled = 0;
    setTotalFailedIsSet(false);
    this.totalFailed = 0;
  }

  public int getNumSplits() {
    return this.numSplits;
  }

  public JobStats setNumSplits(int numSplits) {
    this.numSplits = numSplits;
    setNumSplitsIsSet(true);
    return this;
  }

  public void unsetNumSplits() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NUMSPLITS_ISSET_ID);
  }

  /** Returns true if field numSplits is set (has been assigned a value) and false otherwise */
  public boolean isSetNumSplits() {
    return EncodingUtils.testBit(__isset_bitfield, __NUMSPLITS_ISSET_ID);
  }

  public void setNumSplitsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NUMSPLITS_ISSET_ID, value);
  }

  public int getTotalSortJobs() {
    return this.totalSortJobs;
  }

  public JobStats setTotalSortJobs(int totalSortJobs) {
    this.totalSortJobs = totalSortJobs;
    setTotalSortJobsIsSet(true);
    return this;
  }

  public void unsetTotalSortJobs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALSORTJOBS_ISSET_ID);
  }

  /** Returns true if field totalSortJobs is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalSortJobs() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALSORTJOBS_ISSET_ID);
  }

  public void setTotalSortJobsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALSORTJOBS_ISSET_ID, value);
  }

  public int getTotalSuccessful() {
    return this.totalSuccessful;
  }

  public JobStats setTotalSuccessful(int totalSuccessful) {
    this.totalSuccessful = totalSuccessful;
    setTotalSuccessfulIsSet(true);
    return this;
  }

  public void unsetTotalSuccessful() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALSUCCESSFUL_ISSET_ID);
  }

  /** Returns true if field totalSuccessful is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalSuccessful() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALSUCCESSFUL_ISSET_ID);
  }

  public void setTotalSuccessfulIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALSUCCESSFUL_ISSET_ID, value);
  }

  public int getTotalKilled() {
    return this.totalKilled;
  }

  public JobStats setTotalKilled(int totalKilled) {
    this.totalKilled = totalKilled;
    setTotalKilledIsSet(true);
    return this;
  }

  public void unsetTotalKilled() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALKILLED_ISSET_ID);
  }

  /** Returns true if field totalKilled is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalKilled() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALKILLED_ISSET_ID);
  }

  public void setTotalKilledIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALKILLED_ISSET_ID, value);
  }

  public int getTotalFailed() {
    return this.totalFailed;
  }

  public JobStats setTotalFailed(int totalFailed) {
    this.totalFailed = totalFailed;
    setTotalFailedIsSet(true);
    return this;
  }

  public void unsetTotalFailed() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALFAILED_ISSET_ID);
  }

  /** Returns true if field totalFailed is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalFailed() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALFAILED_ISSET_ID);
  }

  public void setTotalFailedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALFAILED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NUM_SPLITS:
      if (value == null) {
        unsetNumSplits();
      } else {
        setNumSplits((Integer)value);
      }
      break;

    case TOTAL_SORT_JOBS:
      if (value == null) {
        unsetTotalSortJobs();
      } else {
        setTotalSortJobs((Integer)value);
      }
      break;

    case TOTAL_SUCCESSFUL:
      if (value == null) {
        unsetTotalSuccessful();
      } else {
        setTotalSuccessful((Integer)value);
      }
      break;

    case TOTAL_KILLED:
      if (value == null) {
        unsetTotalKilled();
      } else {
        setTotalKilled((Integer)value);
      }
      break;

    case TOTAL_FAILED:
      if (value == null) {
        unsetTotalFailed();
      } else {
        setTotalFailed((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NUM_SPLITS:
      return getNumSplits();

    case TOTAL_SORT_JOBS:
      return getTotalSortJobs();

    case TOTAL_SUCCESSFUL:
      return getTotalSuccessful();

    case TOTAL_KILLED:
      return getTotalKilled();

    case TOTAL_FAILED:
      return getTotalFailed();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NUM_SPLITS:
      return isSetNumSplits();
    case TOTAL_SORT_JOBS:
      return isSetTotalSortJobs();
    case TOTAL_SUCCESSFUL:
      return isSetTotalSuccessful();
    case TOTAL_KILLED:
      return isSetTotalKilled();
    case TOTAL_FAILED:
      return isSetTotalFailed();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof JobStats)
      return this.equals((JobStats)that);
    return false;
  }

  public boolean equals(JobStats that) {
    if (that == null)
      return false;

    boolean this_present_numSplits = true;
    boolean that_present_numSplits = true;
    if (this_present_numSplits || that_present_numSplits) {
      if (!(this_present_numSplits && that_present_numSplits))
        return false;
      if (this.numSplits != that.numSplits)
        return false;
    }

    boolean this_present_totalSortJobs = true;
    boolean that_present_totalSortJobs = true;
    if (this_present_totalSortJobs || that_present_totalSortJobs) {
      if (!(this_present_totalSortJobs && that_present_totalSortJobs))
        return false;
      if (this.totalSortJobs != that.totalSortJobs)
        return false;
    }

    boolean this_present_totalSuccessful = true;
    boolean that_present_totalSuccessful = true;
    if (this_present_totalSuccessful || that_present_totalSuccessful) {
      if (!(this_present_totalSuccessful && that_present_totalSuccessful))
        return false;
      if (this.totalSuccessful != that.totalSuccessful)
        return false;
    }

    boolean this_present_totalKilled = true;
    boolean that_present_totalKilled = true;
    if (this_present_totalKilled || that_present_totalKilled) {
      if (!(this_present_totalKilled && that_present_totalKilled))
        return false;
      if (this.totalKilled != that.totalKilled)
        return false;
    }

    boolean this_present_totalFailed = true;
    boolean that_present_totalFailed = true;
    if (this_present_totalFailed || that_present_totalFailed) {
      if (!(this_present_totalFailed && that_present_totalFailed))
        return false;
      if (this.totalFailed != that.totalFailed)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_numSplits = true;
    list.add(present_numSplits);
    if (present_numSplits)
      list.add(numSplits);

    boolean present_totalSortJobs = true;
    list.add(present_totalSortJobs);
    if (present_totalSortJobs)
      list.add(totalSortJobs);

    boolean present_totalSuccessful = true;
    list.add(present_totalSuccessful);
    if (present_totalSuccessful)
      list.add(totalSuccessful);

    boolean present_totalKilled = true;
    list.add(present_totalKilled);
    if (present_totalKilled)
      list.add(totalKilled);

    boolean present_totalFailed = true;
    list.add(present_totalFailed);
    if (present_totalFailed)
      list.add(totalFailed);

    return list.hashCode();
  }

  @Override
  public int compareTo(JobStats other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetNumSplits()).compareTo(other.isSetNumSplits());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumSplits()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numSplits, other.numSplits);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalSortJobs()).compareTo(other.isSetTotalSortJobs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalSortJobs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalSortJobs, other.totalSortJobs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalSuccessful()).compareTo(other.isSetTotalSuccessful());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalSuccessful()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalSuccessful, other.totalSuccessful);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalKilled()).compareTo(other.isSetTotalKilled());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalKilled()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalKilled, other.totalKilled);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalFailed()).compareTo(other.isSetTotalFailed());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalFailed()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalFailed, other.totalFailed);
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
    StringBuilder sb = new StringBuilder("JobStats(");
    boolean first = true;

    sb.append("numSplits:");
    sb.append(this.numSplits);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalSortJobs:");
    sb.append(this.totalSortJobs);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalSuccessful:");
    sb.append(this.totalSuccessful);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalKilled:");
    sb.append(this.totalKilled);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalFailed:");
    sb.append(this.totalFailed);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'numSplits' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'totalSortJobs' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'totalSuccessful' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'totalKilled' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'totalFailed' because it's a primitive and you chose the non-beans generator.
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

  private static class JobStatsStandardSchemeFactory implements SchemeFactory {
    public JobStatsStandardScheme getScheme() {
      return new JobStatsStandardScheme();
    }
  }

  private static class JobStatsStandardScheme extends StandardScheme<JobStats> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, JobStats struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NUM_SPLITS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.numSplits = iprot.readI32();
              struct.setNumSplitsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOTAL_SORT_JOBS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalSortJobs = iprot.readI32();
              struct.setTotalSortJobsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOTAL_SUCCESSFUL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalSuccessful = iprot.readI32();
              struct.setTotalSuccessfulIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TOTAL_KILLED
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalKilled = iprot.readI32();
              struct.setTotalKilledIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TOTAL_FAILED
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalFailed = iprot.readI32();
              struct.setTotalFailedIsSet(true);
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
      if (!struct.isSetNumSplits()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'numSplits' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTotalSortJobs()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalSortJobs' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTotalSuccessful()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalSuccessful' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTotalKilled()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalKilled' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTotalFailed()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalFailed' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, JobStats struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(NUM_SPLITS_FIELD_DESC);
      oprot.writeI32(struct.numSplits);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_SORT_JOBS_FIELD_DESC);
      oprot.writeI32(struct.totalSortJobs);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_SUCCESSFUL_FIELD_DESC);
      oprot.writeI32(struct.totalSuccessful);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_KILLED_FIELD_DESC);
      oprot.writeI32(struct.totalKilled);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_FAILED_FIELD_DESC);
      oprot.writeI32(struct.totalFailed);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class JobStatsTupleSchemeFactory implements SchemeFactory {
    public JobStatsTupleScheme getScheme() {
      return new JobStatsTupleScheme();
    }
  }

  private static class JobStatsTupleScheme extends TupleScheme<JobStats> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, JobStats struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.numSplits);
      oprot.writeI32(struct.totalSortJobs);
      oprot.writeI32(struct.totalSuccessful);
      oprot.writeI32(struct.totalKilled);
      oprot.writeI32(struct.totalFailed);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, JobStats struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.numSplits = iprot.readI32();
      struct.setNumSplitsIsSet(true);
      struct.totalSortJobs = iprot.readI32();
      struct.setTotalSortJobsIsSet(true);
      struct.totalSuccessful = iprot.readI32();
      struct.setTotalSuccessfulIsSet(true);
      struct.totalKilled = iprot.readI32();
      struct.setTotalKilledIsSet(true);
      struct.totalFailed = iprot.readI32();
      struct.setTotalFailedIsSet(true);
    }
  }

}

