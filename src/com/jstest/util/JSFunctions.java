package com.jstest.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

/**
 * This class contans methods that are globally exposed to javascript
 * 
 * @author sinduja
 *
 */
public class JSFunctions {

	/**
	 * Loads a script file in context
	 * @param ctx script context
	 * @param thisObj script scope
	 * @param args includes the path to file
	 * @param funObj
	 * @throws IOException
	 */
	public static void load(Context ctx, Scriptable thisObj, Object[] args, Function funObj) throws IOException {
		FileReader fr = null;
	    try {
	      fr = new FileReader(args[0].toString());
	      ctx.evaluateReader(thisObj, fr, args[0].toString(), 1, null);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      fr.close();
	    }
	}
	  
	/**
	 * Parses the script file and finds possible class names
	 * @param ctx script context
	 * @param thisObj script scope
	 * @param args includes the path to file
	 * @param funObj
	 * @return
	 */
	public static Object[] getPossibleClassNames(Context ctx, Scriptable thisObj, Object[] args, Function funObj) {
		JSParser ttg  = new JSParser();
		List<String> classNames = ttg.parseJSFileForPossibleClasses(args[0].toString());
		return classNames.toArray();
	}
	
	/**
	 * Called from script to create the test template file
	 * @param ctx script context
	 * @param thisObj script scope
	 * @param args contains a map of classes to members in the class
	 * @param funObj
	 * @throws IOException
	 */
	public static void createJSTestFile(Context ctx, Scriptable thisObj, Object[] args, Function funObj) throws IOException {
		Map<String, NativeArray> classMemberMap = (Map<String, NativeArray>)args[0];
		if (!classMemberMap.isEmpty()) {
			File file = new File("output/output.js");
			if (!file.exists()) {
				file.createNewFile();				
			}
						
			StringBuilder sb = new StringBuilder();
			String testClass = null;
			String member = null;
			for (String className : classMemberMap.keySet()) {
				testClass = className + "Test";
				sb.append("function " + testClass + "() {}\n\n");
				NativeArray members = (NativeArray) classMemberMap.get(className);
				for (int idx = 0; idx < members.size(); idx++) {
					member = members.get(idx).toString();
					if (member.startsWith("_")) {
						continue;
					}
					sb.append(testClass + ".prototype.test" + (member.charAt(0) + "").toUpperCase() + member.substring(1)  + " = function() {\n}\n\n");
				}
				sb.append("\n");
			}
			//write content to file
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(sb.toString().getBytes());
			fos.close();
		}
	}
	
	/**
	 * Prints the text to console
	 * @param ctx script context
	 * @param thisObj script scope
	 * @param args array of messages to be printed
	 * @param funObj
	 */
	public static void print(Context ctx, Scriptable thisObj, Object[] args, Function funObj) {
		for (Object arg : args) {
			System.out.print(arg);
		}
		System.out.println();
	}
}
