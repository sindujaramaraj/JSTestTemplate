package com.jstest.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A util class containing parsing methods
 * 
 * @author sinduja
 */

public class JSParser {
	
	/**
	 * Regex to identify function
	 */
	private static final String CLASS_MATCHER = "function[\\s]+([a-zA-Z0-9]+)[\\s]*[\\(]";
	
	/**
	 * Parses the script file and finds the possible classes through regex
	 * @param filePath path to the file to be parsed
	 * @return
	 */
	public List<String> parseJSFileForPossibleClasses(String filePath) {
		File file = new File(filePath);
		StringBuilder sb = new StringBuilder();
		String line;
		if (file.canRead()) {
			try {
				BufferedReader br = new BufferedReader( new FileReader(file));
				while ((line =  br.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				
			}
			String fileContent = sb.toString();
			Pattern jsClassPattern = Pattern.compile(CLASS_MATCHER);
			Matcher matcher = jsClassPattern.matcher(fileContent);
			List<String> classNames = new ArrayList<String>();
			String matchText = null;
			while (matcher.find()) {
				matchText = matcher.group().replaceAll(CLASS_MATCHER, "$1");
				classNames.add(matchText);
			}
			return classNames;
		}
		return null;
	}

}
