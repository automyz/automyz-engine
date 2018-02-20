package com.automyz.utils;

import java.util.Collection;
import java.util.Map;

/**
 * This class provides all the utility methods for all the data types to check
 * weather the value is null/empty.
 * <p>
 * The methods which are started with the keyword <b>check</b> will only check
 * weather the value is null/empty.
 * 
 * <p>
 * The methods which are started with the keyword <b>assert</b> will check
 * weather the value is null/empty. If the argument is null/empty it throws an
 * exception called <b>PreconditionException</b>.
 * 
 * @exception PreconditionException
 * 
 * 
 * @author Amar
 *
 */
public class Preconditions {
	Preconditions() {
		// hide the constructor, not expected to instantiate
	}

	public static class PreconditionException extends IllegalArgumentException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PreconditionException() {
			super();
		}

		public PreconditionException(String message, Throwable cause) {
			super(message, cause);
		}

		public PreconditionException(String s) {
			super(s);
		}

		public PreconditionException(Throwable cause) {
			super(cause);
		}
	}

	/**
	 * It checks weather the given value is <b>null or not</b>. If it's <b>not
	 * null</b>, then it returns <b>true</b> or else <b>false</b>.
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean checkNotNull(Object value) {
		return value != null;
	}

	/**
	 * It checks weather the given value is <b>null or not</b>. If it's
	 * <b>null</b>, then it returns <b>true</b> or else <b>false</b>.
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean checkNull(Object value) {
		return value == null;
	}

	/**
	 * It asserts weather the given value is <b>null or not</b>. It its <b>not
	 * null</b>, then it returns a <b>value</b> or else <b>it throws an
	 * exception along with the message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static <T> T assertNotNull(T value, String paramName) throws PreconditionException {
		if (!checkNotNull(value)) {
			throw new PreconditionException("Expected not to be null: " + paramName);
		}
		return value;
	}

	/**
	 * It checks weather the given collection is <b> not null and not empty </b>
	 * . It it's <b> not null and not empty </b> then it returns <b>true</b> or
	 * else <b>false</b>.
	 * 
	 * @param argValue
	 * @return true/false
	 */
	public static boolean checkNotEmpty(Collection<?> argValue) {
		return checkNotNull(argValue) && !argValue.isEmpty();
	}

	/**
	 * It checks weather the given Map is <b> not null and not empty </b>. It
	 * it's <b> not null and not empty </b> then it returns <b>true</b> or else
	 * <b>false</b>.
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean checkNotEmpty(Map<?, ?> value) {
		return checkNotNull(value) && !value.isEmpty();
	}

	/**
	 * It checks weather the given array of objects are <b> not null and not
	 * empty </b>. It it's <b> not null and not empty </b> then it returns
	 * <b>true</b> or else <b>false</b>.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkNotEmpty(Object[] value) {
		return checkNotNull(value) && value.length > 0;
	}

	/**
	 * It asserts weather the given collection is <b>not null and not empty</b>.
	 * It its <b>not null and not empty</b>, then it returns a <b>collection of
	 * objects</b> or else <b>it throws an exception along with the message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static <T> Collection<T> assertNotEmpty(Collection<T> value, String paramName) throws PreconditionException {
		assertNotNull(value, paramName);
		if (value.isEmpty()) {
			throw new PreconditionException("Expected not to be empty: " + paramName);
		}
		return value;
	}

	/**
	 * It asserts weather the given map of objects are <b>not null and not
	 * empty</b>. It its <b>not null and not empty</b>, then it returns a <b>map
	 * of objects</b> or else <b>it throws an exception along with the
	 * message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static Map<?, ?> assertNotEmpty(Map<?, ?> argMap, String argParamName) {
		assertNotNull(argMap, argParamName);
		if (argMap.isEmpty()) {
			throw new PreconditionException("Expected not to be empty: " + argParamName);
		}
		return argMap;
	}

	/**
	 * It checks weather the given array of bytes are <b>not null and not
	 * empty</b>. It its <b>not null and not empty</b>, then it returns a
	 * <b>true</b> or else <b>false</b>.
	 * 
	 * @param array
	 *            of bytes
	 * @return true/false
	 */
	public static boolean checkNotEmpty(byte[] value) {
		return checkNotNull(value) && value.length > 0;
	}

	/**
	 * It asserts weather the given array of bytes are <b>not null and not
	 * empty</b>. It its <b>not null and not empty</b>, then it returns a
	 * <b>byte array</b> or else <b>it throws an exception along with the
	 * message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static byte[] assertNotEmpty(byte[] value, String paramName) throws PreconditionException {
		assertNotNull(value, paramName);
		if (value.length == 0) {
			throw new PreconditionException("Expected not to be empty: " + paramName);
		}
		return value;
	}

	/**
	 * It checks weather the given String is <b>not null and not empty</b>. It
	 * its <b>not null and not empty</b>, then it returns a <b>true</b> or else
	 * <b>false</b>.
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean checkNotEmpty(String value) {
		return checkNotNull(value) && !value.isEmpty();
	}

	/**
	 * It checks weather the given String is <b>not null and empty</b>. It its
	 * <b>not null and empty</b>, then it returns a <b>true</b> or else
	 * <b>false</b>.
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean checkEmpty(String value) {
		return checkNotNull(value) && value.isEmpty();
	}

	/**
	 * It asserts weather the given String is <b>not null and not empty</b>. It
	 * its <b>not null and not empty</b>, then it returns a <b>given string</b>
	 * or else <b>it throws an exception along with the message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static String assertNotEmpty(String value, String paramName) throws PreconditionException {
		assertNotNull(value, paramName);
		if (value.isEmpty()) {
			throw new PreconditionException("Expected not to be empty: " + paramName);
		}
		return value;
	}

	/**
	 * It asserts weather the given array of objects are <b>not null and not
	 * empty</b>. It its <b>not null and not empty</b>, then it returns a
	 * <b>object array</b> or else <b>it throws an exception along with the
	 * message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static Object[] assertNotEmpty(Object[] argValue, String argParamName) throws PreconditionException {
		assertNotNull(argValue, argParamName);
		if (argValue.length == 0) {
			throw new PreconditionException("Expected not to be empty: " + argParamName);
		}
		return argValue;
	}

	/**
	 * It asserts weather the given number is <b>equals to zero or not</b>. It
	 * its <b>not a zero</b>, then it returns the <b>given value</b> or else
	 * <b>it throws an exception along with the message</b>.
	 * 
	 * @param value
	 * @param paramName
	 * @return value
	 * @throws PreconditionException
	 */
	public static int assertNonZero(int value, String paramName) throws PreconditionException {
		if (value == 0) {
			throw new PreconditionException("Expected to be non-zero: " + paramName);
		}
		return value;
	}

	/**
	 * This method will check whether the both arguments are same or not. If
	 * both arguments are same then it will return <b> true </b> or else
	 * <b> false </b>.
	 * 
	 * @param argLeftOperand
	 * @param argRightOperand
	 * @return true/false
	 */
	public static boolean checkEquals(String argLeftOperand, String argRightOperand) {
		return checkNotEmpty(argLeftOperand) && checkNotEmpty(argRightOperand)
				&& argLeftOperand.equals(argRightOperand);
	}

	/**
	 * This method will check whether the given argument is equals to zero or
	 * not. If it is zero, then it return <b> true </b> or else <b> false </b>.
	 * 
	 * @param argValue
	 * @return true/false
	 */
	public static boolean checkZero(int argValue) {
		return argValue == 0;
	}

	/**
	 * This method will check whether the given argument is equals to zero or
	 * not. If it is zero, then it return <b> true </b> or else <b> false </b>.
	 * 
	 * @param argValue
	 * @return true/false
	 */
	public static boolean checkZero(double argValue) {
		return argValue == 0;
	}

	/**
	 * It checks weather the given Collection is <b>not null and empty</b>. It
	 * its <b>not null and empty</b>, then it returns a <b>true</b> or else
	 * <b>false</b>.
	 * 
	 * @param argValue
	 * @return true/false
	 */
	public static boolean checkEmpty(Collection<?> argValue) {
		return checkNotNull(argValue) && argValue.isEmpty();
	}

	/**
	 * It asserts weather the given number is <b>non negative</b>. It its <b>not
	 * a negative number</b>, then it returns the <b>given value</b> or else
	 * <b>it throws an exception along with the message</b>.
	 * 
	 * @param argValue
	 * @param argParamName
	 * @return value
	 * @throws PreconditionException
	 */
	public static int assertNonNegative(int argValue, String argParamName) throws PreconditionException {
		if (argValue == -1) {
			throw new PreconditionException("Expected to be non-negative: " + argParamName);
		}
		return argValue;
	}

	/**
	 * /** It asserts weather the given number is <b>non negative</b>. It its
	 * <b>not a negative number</b>, then it returns the <b>given value</b> or
	 * else <b>it throws an exception along with the message</b>.
	 * 
	 * @param argNumberOfLines
	 * @param argParamName
	 * @return
	 */
	public static long assertNonNegative(long argNumberOfLines, String argParamName) {
		if (argNumberOfLines == -1) {
			throw new PreconditionException("Expected to be non-negative: " + argParamName);
		}
		return argNumberOfLines;
	}

	/**
	 * It checks weather the given number is <b>non negative</b>. It its <b>not
	 * a negative number </b>, then it returns a <b>true</b> or else
	 * <b>false</b>.
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean checkNonNegative(int value) {
		return value > -1;
	}

	/**
	 * It checks weather the given number is <b>non negative</b>. It its <b>not
	 * a negative number </b>, then it returns a <b>true</b> or else
	 * <b>false</b>.
	 * 
	 * @param argNumberOfLines
	 * @return
	 */
	public static boolean checkNonNegative(long argNumberOfLines) {
		return argNumberOfLines > -1;
	}

	/**
	 * It checks weather the given number is <b>negative</b>. It its <b> a
	 * negative number </b>, then it returns a <b>true</b> or else <b>false</b>.
	 * 
	 * @param argNumberOfLines
	 * @return
	 */
	public static boolean checkNegative(long argNumberOfLines) {
		return argNumberOfLines < 0;
	}

	public static boolean checkEmpty(Object[] argValue) {
		return checkNotNull(argValue) && argValue.length == 0;
	}
}