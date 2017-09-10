package com.caijia.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
  *
  * @version 1.0
  * @author liuxinyuan
  * @Description 
  * @date 2014-3-4
  */ 
public class StringUtil extends org.apache.commons.lang.StringUtils{
	private static final Pattern STANDARD_SPLIT = Pattern.compile("[\\s,]+");

	public static String[] standardSplit(String input) {
		if (input == null) {
			return new String[0];
		} else {
			return STANDARD_SPLIT.split(input.trim());
		}
	}
	

	public static boolean isBlank(Object s) {
		return s == null || s.toString().trim().equalsIgnoreCase("")
				|| "null".equalsIgnoreCase(s.toString());
	}

	public static boolean isNull(Object s) {
		return s == null;
	}

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		int pos = s.indexOf(delimiter + text + delimiter);

		if (pos == -1) {
			if (s.startsWith(text + delimiter)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static String trim(int i) {
		return String.valueOf(i);
	}

	public static String trim(String s) {
		if (s != null)
			return s.trim();
		else
			return "";
	}

	public static String trim(Object o) {
		if (o != null)
			return o.toString().trim();
		else
			return "";
	}
	
	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());
			count++;
		}

		return count;
	}

	public static boolean endsWith(String s, char end) {
		return startsWith(s, (new Character(end)).toString());
	}

	public static boolean endsWith(String s, String end) {
		if ((s == null) || (end == null)) {
			return false;
		}

		if (end.length() > s.length()) {
			return false;
		}

		String temp = s.substring(s.length() - end.length(), s.length());

		if (temp.equalsIgnoreCase(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static String merge(String array[], String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String read(ClassLoader classLoader, String name)
			throws IOException {

		return read(classLoader.getResourceAsStream(name));
	}

	public static String getRandomStr() {
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q',
				'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random(); // ��ʼ������������
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < c.length; i++) {
			sb.append(c[Math.abs(random.nextInt()) % c.length]);
		}
		return sb.toString();
	}

	public static String read(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}

		br.close();

		return sb.toString().trim();
	}

	public static String replace(String s, char oldSub, char newSub) {
		return replace(s, oldSub, new Character(newSub).toString());
	}

	public static String replace(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		char[] c = s.toCharArray();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == oldSub) {
				sb.append(newSub);
			} else {
				sb.append(c[i]);
			}
		}

		return sb.toString();
	}

	public static String replace(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		int y = s.indexOf(oldSub);

		if (y >= 0) {
			StringBuffer sb = new StringBuffer();
			int length = oldSub.length();
			int x = 0;

			while (x <= y) {
				sb.append(s.substring(x, y));
				sb.append(newSub);
				x = y + length;
				y = s.indexOf(oldSub, x);
			}

			sb.append(s.substring(x));

			return sb.toString();
		} else {
			return s;
		}
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "...");
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static String shorten(String s, int length, String suffix) {
		if (s == null || suffix == null) {
			return null;
		}

		if (s.length() > length) {
			s = s.substring(0, length) + suffix;
		}

		return s;
	}

	public static String[] split(String s) {
		return split(s, StringPool.COMMA);
	}

	public static String[] split(String s, String delimiter) {
		if (s == null || delimiter == null) {
			return new String[0];
		}

		s = s.trim();

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		if (s.equals(delimiter)) {
			return new String[0];
		}

		List nodeValues = new ArrayList();

		if (delimiter.equals("\n") || delimiter.equals("\r")) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line = null;

				while ((line = br.readLine()) != null) {
					nodeValues.add(line);
				}

				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);

			while (pos != -1) {
				nodeValues.add(s.substring(offset, pos));

				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return (String[]) nodeValues.toArray(new String[0]);
	}

	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}


	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static final String stackTrace(Throwable t) {
		String s = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			t.printStackTrace(new PrintWriter(baos, true));
			s = baos.toString();
		} catch (Exception e) {
		}

		return s;
	}

	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	public static boolean startsWith(String s, String start) {
		if ((s == null) || (start == null)) {
			return false;
		}

		if (start.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, start.length());

		if (temp.equalsIgnoreCase(start)) {
			return true;
		} else {
			return false;
		}
	}

	

	public static String trimTrailing(String s) {
		for (int i = s.length() - 1; i >= 0; i--) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return s.substring(0, i + 1);
			}
		}

		return StringPool.BLANK;
	}

	public static String wrap(String text) {
		return wrap(text, 80, "\n");
	}

	public static String getTxtWithoutHTMLElement(String element) {
		// String reg="<[^<|^>]+>";
		// return element.replaceAll(reg,"");

		if (null == element || "".equals(element.trim())) {
			return element;
		}

		// Pattern pattern = Pattern.compile("<[^<|^>]*>");
		Pattern pattern = Pattern.compile("\\<[^<|^>]*>");
		Matcher matcher = pattern.matcher(element);
		StringBuffer txt = new StringBuffer();
		while (matcher.find()) {
			String group = matcher.group();
			if (group.matches("<[\\s]*>")) {
				matcher.appendReplacement(txt, group);
			} else {
				matcher.appendReplacement(txt, "");
			}
		}
		matcher.appendTail(txt);
		String s = txt.toString();
		s = StringUtils.replace(s, "\'", "");
		return s;
	}

	public static void main(String[] args) {
		// String ss = "<iframe>sssss</iframe>";
		// System.out.println(StringUtil.getTxtWithoutHTMLElement(ss));
		for (int i = 0; i < 10; i++) {
			System.out.println(StringUtil.getRandomStr());
		}

	}

	public static String wrap(String text, int width, String lineSeparator) {
		if (text == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = StringPool.BLANK;

			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					sb.append(lineSeparator);
				} else {
					String[] tokens = s.split(StringPool.SPACE);
					boolean firstWord = true;
					int curLineLength = 0;

					for (int i = 0; i < tokens.length; i++) {
						if (!firstWord) {
							sb.append(StringPool.SPACE);
							curLineLength++;
						}

						if (firstWord) {
							sb.append(lineSeparator);
						}

						sb.append(tokens[i]);

						curLineLength += tokens[i].length();

						if (curLineLength >= width) {
							firstWord = true;
							curLineLength = 0;
						} else {
							firstWord = false;
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param tmpStr
	 *            临时字符串
	 * @return
	 */
	public static boolean isNull(String tmpStr) {

		if (tmpStr == null) {
			return true;
		}
		tmpStr = tmpStr.trim();
		if (tmpStr.equals("")) {
			return true;
		}
		return false;

	}

	/**
	 * 根据arr数组来和分隔符将数组转化为字符串
	 * 
	 * @param arr
	 * @param spitFlag
	 * @return
	 */
	public static String transArr2String(String[] arr, String spitFlag) {
		String ret = "";
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				ret = arr[i];
			} else {
				ret = ret + spitFlag + arr[i];
			}
		}
		return ret;

	}

	/**
	 * 根据arr数组来和分隔符将数组转化为字符串并且加上''方便sql查询
	 * 
	 * @param arr
	 * @param spitFlag
	 * @return
	 */
	public static String transArr2String4sql(String[] arr, String spitFlag) {
		String ret = "";
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				ret = "'" + arr[i] + "'";
			} else {
				ret = ret + spitFlag + "'" + arr[i] + "'";
			}
		}
		return ret;

	}
	
	
	/**
	 * <p>Title: ****</p>
	 * <p>Description:截取字符串后加...<p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String subStrIgnore(String str, int len) {
		String ret = "";
		if (str != null && len != 0) {
			if (str.length() > len) {
				ret = str.substring(0, len) + "...";
			} else {
				ret = str;
			}
		}
		return ret;
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:转义成GBK</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String charCn(String s) {
		if (s != null)
			try {
				return new String(s.trim().getBytes("ISO-8859-1"), "GBK");
			} catch (UnsupportedEncodingException unsupportedencodingexception) {
			}
		return "";
	}
	/**
	 * <p>Title: ****</p>
	 * <p>Description:转义成GBK</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String charCn(Object o) {
		if (o != null)
			try {
				return new String(o.toString().trim().getBytes("GBK"),
						"ISO-8859-1");
			} catch (UnsupportedEncodingException unsupportedencodingexception) {
			}
		return "";
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:转义成EN</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String charEn(String s) {
		if (s != null)
			try {
				return new String(s.trim().getBytes("GBK"), "ISO-8859-1");
			} catch (UnsupportedEncodingException unsupportedencodingexception) {
			}
		return "";
	}
	/**
	 * <p>Title: ****</p>
	 * <p>Description:转义成EN</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String charEn(Object o) {
		if (o != null)
			try {
				return new String(o.toString().trim().getBytes("GBK"),
						"ISO-8859-1");
			} catch (UnsupportedEncodingException unsupportedencodingexception) {
			}
		return "";
	}

	public static int stoi(String s) {
		int i = -1;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			i = -1;
		}
		return i;
	}

	public static int stoi(Object o) {
		int i = -1;
		try {
			i = Integer.parseInt(o.toString());
		} catch (Exception e) {
			i = -1;
		}
		return i;
	}

	public static String upperFirst(String str) {
		if (str == null)
			return "";
		if (str.length() <= 1)
			return str.toUpperCase();
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public String convertDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(date);
	}
}
