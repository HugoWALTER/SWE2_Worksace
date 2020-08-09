package cc.calc.main;

import java.io.File;
import java.io.IOException;

import se.ck.martin.extensions.Application;
import se.ck.martin.extensions.Calculator;

public class CyclomaticComplexityProjectAnalysis {

	public static String getPath() throws IOException {
		String filePath;
		filePath = new File("..").getCanonicalPath();
		filePath = filePath.concat("/Games");
		return filePath;
	}
	
	public static void main(String[] args) throws IOException {
		Calculator calc = new Calculator();
		Application app = calc.run(getPath());
		System.out.println("++++++++++++ Calculating Cyclomatic Complexity... ++++++++++++");
		System.out.println();
		app.printPackageInfo();
		System.out.print("++++++++++++ Done ++++++++++++");
	}
}
