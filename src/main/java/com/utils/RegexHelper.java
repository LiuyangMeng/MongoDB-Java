package com.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式帮助类
 * 
 * @author DLHT 2015-06-30 14:17:29
 * 
 */
public class RegexHelper {

	/**
	 * 整数 ^-?\d+$
	 */
	public static final String INTEGER = "^-?\\d+$";
	/**
	 * 正整数 ^[0-9]*[1-9][0-9]*$
	 */
	public static final String PINTEGER = "^[0-9]*[1-9][0-9]*$";
	/**
	 * 负整数 ^-[0-9]*[1-9][0-9]*$
	 */
	public static final String NINTEGER = "^-[0-9]*[1-9][0-9]*$";
	/**
	 * 非正整数 ^((-\d+)|(0+))$
	 */
	public static final String NOTPINTEGER = "^((-\\d+)|(0+))$";
	/**
	 * 非负整数 ^[0-9]*$
	 */
	public static final String NOTNINTEGER = "^[0-9]+$";
	/**
	 * 浮点数 ^(-?\d+)(\.\d+)?$
	 */
	public static final String FLOAT = "^(-?\\d+)(\\.\\d+)?$";
	/**
	 * 正浮点数
	 * ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-
	 * 9]*))$
	 */
	public static final String PFLOAT = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
	/**
	 * 负浮点数
	 * ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][
	 * 0-9]*)))$
	 */
	public static final String NFLOAT = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";
	/**
	 * 非正浮点数 ^((-\d+(\.\d+)?)|(0+(\.0+)?))$
	 */
	public static final String NOTPFLOAT = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";
	/**
	 * 非负浮点数 ^\d+(\.\d+)?$
	 */
	public static final String NOTNFLOAT = "^\\d+(\\.\\d+)?$";
	/**
	 * 由26个英文字母组成 ^[A-Za-z]+$
	 */
	public static final String LETTER = "^[A-Za-z]+$";
	/**
	 * 由26个大写英文字母组成 ^[A-Z]+$
	 */
	public static final String UPPERLETTER = "^[A-Z]+$";
	/**
	 * 由26个小写英文字母组成 ^[a-z]+$
	 */
	public static final String LOWERLETTER = "^[A-Z]+$";
	/**
	 * 由数字或26个英文字母组成 ^[A-Za-z0-9]+$
	 */
	public static final String LETTERORINTEGER = "^[A-Za-z0-9]+$";
	/**
	 * 由数字、26个英文字母或者下划线组成 ^\w+$ 或 ^[a-zA-Z0-9_]+$
	 */
	public static final String LETTERORINTEGERORUNDERLINE = "^\\w+$";
	/**
	 * email地址 ^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$
	 */
	public static final String EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	/**
	 * 简单手机号,1开头并且11位 ^1\d{10}$
	 */
	public static final String SIMPLEPHONENUM = "^((\\+86)|(86))?(1)\\d{10}$";
	/**
	 * url,暂定 ^[a-zA-z]+://(\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\S*)?$
	 */
	public static final String URL = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";
	/**
	 * ip地址 /d+/./d+/./d+/./d+
	 */
	public static final String IP = "\\d+.\\d+.\\d+.\\d+";

	/**
	 * 判断字符串格式是否正确
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @param regex
	 *            校验的正则字符串
	 * @return true 格式正确 ； false 格式不正确
	 */
	public static boolean isMatch(String str, String regex) {
		if (null == str)
			return false;
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(str).matches();
	}

	public static void main(String[] args) {
		// System.out.println(RegexHelper.isNumber("",
		// RegexHelper.ISNUMORNULL));
		System.out.println(RegexHelper.isMatch("+8613051545705", RegexHelper.SIMPLEPHONENUM));
	}

}
