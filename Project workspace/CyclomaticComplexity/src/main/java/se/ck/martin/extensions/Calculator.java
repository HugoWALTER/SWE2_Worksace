package se.ck.martin.extensions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;

public class Calculator {
	
	public Application run(String path) {

		BasicConfigurator.configure();
		LogManager.getRootLogger().setLevel(Level.OFF);

		boolean useJars = false;
		int maxAtOnce = 0;
		boolean variablesAndFields = true;
		
		List<CKClassResult> results = new ArrayList<>();
		
		new CK(useJars, maxAtOnce, variablesAndFields).calculate(path, result ->
			results.add(result));
		
		Application app = new Application();
		
		for(CKClassResult result : results) 
			app.addClass(result);
		
		return app;
	}
}
