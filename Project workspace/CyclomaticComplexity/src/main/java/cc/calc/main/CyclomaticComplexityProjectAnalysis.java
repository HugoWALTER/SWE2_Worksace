package cc.calc.main;

import se.ck.martin.extensions.Application;
import se.ck.martin.extensions.Calculator;

public class CyclomaticComplexityProjectAnalysis {

	public static void main(String[] args){
		Calculator calc = new Calculator();
		Application app = calc.run("C:\\Users\\walter_h\\Desktop\\SWE PROJET\\Project workspace\\Games");
		System.out.println("++++++++++++ Calculating Cyclomatic Complexity... ++++++++++++");
		System.out.println();
		app.printPackageInfo();
		System.out.print("++++++++++++ Done ++++++++++++");
	}
}
