package com.geotmt.generator.provider.db.model;



import com.geotmt.generator.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author badQiu
 */
public class Column {
	/**
	 * Reference to the containing table
	 */
	private final Table _table;

	/**
	 * The java.sql.Types type
	 */
	private final int _sqlType;

	/**
	 * The sql typename. provided by JDBC driver
	 */
	private final String _sqlTypeName;

	/**
	 * The name of the column
	 */
	private final String _sqlName;

	/**
	 * True if the column is a primary key
	 */
	private boolean _isPk;

	/**
	 * True if the column is a foreign key
	 */
	private boolean _isFk;

	/**
	 * @todo-javadoc Describe the column
	 */
	private final int _size;

	/**
	 * @todo-javadoc Describe the column
	 */
	private final int _decimalDigits;

	/**
	 * True if the column is nullable
	 */
	private final boolean _isNullable;

	/**
	 * True if the column is indexed
	 */
	private final boolean _isIndexed;

	/**
	 * True if the column is unique
	 */
	private final boolean _isUnique;

	/**
	 * Null if the DB reports no default value
	 */
	private final String _defaultValue;
	
	/**
	 * The comments of column
	 */
	private final String _remarks;
	
	/**
	 * Get static reference to Log4J Logger
	 */
	private static Log _log = LogFactory.getLog(Column.class);

//	String description;
//
//	String humanName;
//
//	int order;
//
//	boolean isHtmlHidden;
//
//	String validateString;

	/**
	 *说明： Describe what the DbColumn constructor does
	 * 
	 * @param table
	 *            Describe what the parameter does
	 * @param sqlType
	 *            Describe what the parameter does
	 * @param sqlTypeName
	 *            Describe what the parameter does
	 * @param sqlName
	 *            Describe what the parameter does
	 * @param size
	 *            Describe what the parameter does
	 * @param decimalDigits
	 *            Describe what the parameter does
	 * @param isPk
	 *            Describe what the parameter does
	 * @param isNullable
	 *            Describe what the parameter does
	 * @param isIndexed
	 *            Describe what the parameter does
	 * @param defaultValue
	 *            Describe what the parameter does
	 * @param isUnique
	 *            Describe what the parameter does
	 */
	public Column(Table table, int sqlType, String sqlTypeName,
			String sqlName, int size, int decimalDigits, boolean isPk,boolean isFk,
			boolean isNullable, boolean isIndexed, boolean isUnique,
			String defaultValue,String remarks) {
		_table = table;
		_sqlType = sqlType;
		_sqlName = sqlName;
		_sqlTypeName = sqlTypeName;
		_size = size;
		_decimalDigits = decimalDigits;
		_isPk = isPk;
		_isFk = isFk;
		_isNullable = isNullable;
		_isIndexed = isIndexed;
		_isUnique = isUnique;
		_defaultValue = defaultValue;
		_remarks = remarks;
		
		_log.debug(sqlName + " isPk -> " + _isPk);

	}

	/**
	 * 说明：Gets the SqlType attribute of the Column object
	 * 
	 * @return The SqlType value
	 */
	public int getSqlType() {
		return _sqlType;
	}

	/**
	 * 说明：Gets the Table attribute of the DbColumn object
	 * 
	 * @return The Table value
	 */
	public Table getTable() {
		return _table;
	}

	/**
	 * 说明：Gets the Size attribute of the DbColumn object
	 * 
	 * @return The Size value
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * 说明：Gets the DecimalDigits attribute of the DbColumn object
	 * 
	 * @return The DecimalDigits value
	 */
	public int getDecimalDigits() {
		return _decimalDigits;
	}

	/**
	 * 说明：Gets the SqlTypeName attribute of the Column object
	 * 
	 * @return The SqlTypeName value
	 */
	public String getSqlTypeName() {
		return _sqlTypeName;
	}

	/**
	 * 说明：Gets the SqlName attribute of the Column object
	 * 
	 * @return The SqlName value
	 */
	public String getSqlName() {
		return _sqlName;
	}

	/**
	 * 说明：Gets the Pk attribute of the Column object
	 * 
	 * @return The Pk value
	 */
	public boolean isPk() {
		return _isPk;
	}

	/**
	 * 说明：Gets the Fk attribute of the Column object
	 * 
	 * @return The Fk value
	 */
	public boolean isFk() {
		return _isFk;
	}

	/**
	 * 说明：Gets the Nullable attribute of the Column object
	 * 
	 * @return The Nullable value
	 */
	public final boolean isNullable() {
		return _isNullable;
	}

	/**
	 * 说明：Gets the Indexed attribute of the DbColumn object
	 * 
	 * @return The Indexed value
	 */
	public final boolean isIndexed() {
		return _isIndexed;
	}

	/**
	 * 说明：Gets the Unique attribute of the DbColumn object
	 * 
	 * @return The Unique value
	 */
	public boolean isUnique() {
		return _isUnique;
	}

	/**
	 * 说明：Gets the DefaultValue attribute of the DbColumn object
	 * 
	 * @return The DefaultValue value
	 */
	public final String getDefaultValue() {
		return _defaultValue;
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public final String getRemarks() {
		return _remarks;
	}

	/**
	 * 说明：Describe what the method does
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	public int hashCode() {
		return (getTable().getSqlName() + "#" + getSqlName()).hashCode();
	}

	/**
	 * 说明：Describe what the method does
	 * 
	 * @param o
	 *            Describe what the parameter does
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for method parameter
	 * @todo-javadoc Write javadocs for return value
	 */
	public boolean equals(Object o) {
		// we can compare by identity, since there won't be dupes
		return this == o;
	}

	/**
	 * 说明：Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	public String toString() {
		return getSqlName();
	}

	/**
	 * 说明：Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	protected final String prefsPrefix() {
		return "tables/" + getTable().getSqlName() + "/columns/" + getSqlName();
	}

	/**
	 * 说明：Sets the Pk attribute of the DbColumn object
	 * 
	 * @param flag
	 *            The new Pk value
	 */
	void setFk(boolean flag) {
		_isFk = flag;
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getColumnName() {
		return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(getSqlName()));
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getColumnNameFirstLower() {
		return StringHelper.uncapitalize(getColumnName());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getColumnNameLowerCase() {
		return getColumnName().toLowerCase();
	}
	/**
	 * 说明：
	 * @deprecated
	 */
	public String getColumnNameLower() {
		return getColumnNameFirstLower();
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getJdbcSqlTypeName() {
		String result = JdbcType.getJdbcSqlTypeName(getSqlType());
		//if(result == null) throw new RuntimeException("jdbcSqlTypeName is null column:"+getSqlName()+" sqlType:"+getSqlType());
		return result;
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getColumnAlias() {
		return StringHelper.emptyIf(getRemarks(), getColumnNameFirstLower());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getConstantName() {
		return StringHelper.toUnderscoreName(getSqlName()).toUpperCase();
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public boolean getIsNotIdOrVersionField() {
		return !isPk();
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getValidateString() {
		String result = getNoRequiredValidateString();
		if(!isNullable()) {
			result = "required " + result;
		}
		return result;
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getNoRequiredValidateString() {
		String result = "";
		if(getSqlName().indexOf("mail") >= 0) {
			result += "validate-email ";
		}
		if(DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits())) {
			result += "validate-number ";
		}
		if(DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits())) {
			result += "validate-integer ";
			if(getJavaType().indexOf("Short") >= 0) {
				result += "max-value-"+Short.MAX_VALUE;
			}else if(getJavaType().indexOf("Integer") >= 0) {
				result += "max-value-"+Integer.MAX_VALUE;
			}else if(getJavaType().indexOf("Byte") >= 0) {
				result += "max-value-"+Byte.MAX_VALUE;
			}
		}
		if(DatabaseDataTypesUtils.isDate(getSqlType(), getSize(), getDecimalDigits())) {
			result += "validate-date ";
		}
		if(DatabaseDataTypesUtils.isString(getSqlType(), getSize(), getDecimalDigits())) {
			result += "max-length-"+getSize()/2;
		}
		return result;
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public boolean getIsStringColumn() {
		return DatabaseDataTypesUtils.isString(getSqlType(), getSize(), getDecimalDigits());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public boolean getIsDateTimeColumn() {
		return DatabaseDataTypesUtils.isDate(getSqlType(), getSize(), getDecimalDigits());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public boolean getIsNumberColumn() {
		return DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits()) || DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public boolean isHtmlHidden() {
		return isPk() && _table.isSingleId();
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getJavaType() {
		return DatabaseDataTypesUtils.getPreferredJavaType(getSqlType(), getSize(), getDecimalDigits());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getAsType() {
		return ActionScriptDataTypesUtils.getPreferredAsType(getJavaType());
	}
	
	/**
	 * 说明：
	 * @return
	 */
	public String getTestData() {
		return new TestDataGenerator().getTestData(getColumnName(),getJavaType(),getSize());
	}
}
