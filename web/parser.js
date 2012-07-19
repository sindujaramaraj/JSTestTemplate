function Parser() {
	
}

(function() {
	/**
	 * Loads the file and based on possible classes,
	 * evals the class names and finds out the available
	 * prototype methods
	 */
	Parser.parse = function(filePath) {
		var possibleClassesArr = getPossibleClassNames(filePath);
		if (!possibleClassesArr) {
			return null;
		}
		var classMap = {};
		var className, clazz;
		load(filePath);
		for (var idx = 0, len = possibleClassesArr.length; idx < len; idx++) {
			className = possibleClassesArr[idx];
			try {
				clazz = eval("(" + className + ")");				
			} catch (e) {
				clazz = null;
				print(e);
			}
			
			if (clazz && clazz.prototype) {
				classMap[className] = [];
				for (var member in clazz.prototype) {
					if (clazz.prototype.hasOwnProperty(member)) {
						classMap[className].push(member);						
					}					
				}
			}
		}
		createJSTestFile(classMap);
	}
})();