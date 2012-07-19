package com.jstest;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.jstest.util.JSFunctions;

/**
 * Main class that initializes scripting context
 * Contains methods to delegate actions to script
 * 
 * @author sinduja
 */

public class JSTestTemplateGenerator {
	
	/**
	 * Script context
	 */
	private Context context = null;
	
	/**
	 * Script scope
	 */
	private ScriptableObject scope = null;
	
	public JSTestTemplateGenerator() throws Exception {
		context = context.enter();
		scope = context.initStandardObjects();
		context.setOptimizationLevel(-1);
		String[] functions = {"load", "print", "getPossibleClassNames", "createJSTestFile"}; //global functions
		scope.defineFunctionProperties(functions, JSFunctions.class, 2);
		InputStream parserFile = this.getClass().getResourceAsStream("/web/parser.js");
	    context.evaluateReader(scope, new InputStreamReader(parserFile), "cmd", 1, null);
	}
	
	/**
	 * Calls the javascript parse method
	 * @param jsFile
	 */
	public void parseFile(String jsFile) {		
	    context.evaluateString(scope, "Parser.parse('" + jsFile + "')", "cmd", 1, null);
	}
	
	/**
	 * Calls the javascript load methods
	 * @param jsFile
	 */
	public void loadFile(String jsFile) {
		context.evaluateString(scope, "load('" + jsFile + "')", "cmd", 1, null);
	}

}
