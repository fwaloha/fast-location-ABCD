package com.itheima.quickindex.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	/**
	 * 将指定包含中文的字符串转换成拼音
	 * @param string 包含中文的字符串
	 * @return 对应的拼音
	 */
	public static String getPinyin(String string) {
		
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		
		// 输出大写
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		// 去除音调
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		StringBuffer sb = new StringBuffer();
		// 获取字符数据
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			
			// 去掉空格
			if(Character.isWhitespace(c)){
				continue;
			}

			if(c > -128 && c < 127){
				// 特殊字符,字母数字, 不需要转换
				sb.append(c);
			}else {
				// 可能是汉字
				try {
					// 将单个字母转换成拼音, 黑 -> HEI, 单 -> SHAN , DAN
					String str = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
					sb.append(str);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}

}
