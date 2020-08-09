package se.ck.martin.extensions;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.mauricioaniche.ck.CKClassResult;

public class Application {

	private Package defPckg = null;
	private Map<String, Package> packages = new TreeMap<>();
	private Map<String, Clazz> classes = new TreeMap<>();

	private Set<Clazz> outsideClasses(Package pckg){
		Set<Clazz> outside = new TreeSet<>();
		for(Clazz cls : classes.values())
			if(cls.getPackage() != pckg)
				outside.add(cls);
		return outside;
	}

	private Set<Package> outsidePackages(Package pckg){
		Set<Package> outside = new TreeSet<>();
		for(Package p : packages.values())
			if(p != pckg)
				outside.add(p);
		return outside;
	}
		
	private void updateDependencies() {
		for(Package p : packages.values()) {
			for(Clazz out : outsideClasses(p))
				if(out.importsFrom(p))
					p.addExtDependency(out);
			for(Clazz in : p.getClasses())
				for(Package out : outsidePackages(p))
					if(in.importsFrom(out))
						p.addIntDependency(in);
		}
	}

	public void addClass(CKClassResult result) {
		Clazz cls = this.lookupClass(result.getClassName());
		for(String importing : result.getCoupling()) {
			Clazz impCls = this.lookupClass(importing);
			cls.addImport(impCls);
		}
		cls.set(result);
		updateDependencies();
	}		
	
	public Clazz lookupClass(String className) {
		Clazz cls = classes.get(className);
		if(cls == null) {
			cls = new Clazz(this, className);
			classes.put(className, cls);
		}
		return cls;
	}
	
	public Package addPackage(String packageName) {
		Package pckg;
		if(packageName == null) {
			if(defPckg == null) {
				defPckg = new DefaultPackage();
				packages.put(defPckg.getName(), defPckg);
			}
			pckg = defPckg;
		} else {
			pckg = packages.get(packageName);
			if(pckg == null) {
				pckg = new Package(packageName);
				packages.put(packageName, pckg);
			}
		}
		return pckg;
	}
	
	public void printPackageInfo() {
		for(String name : packages.keySet()) {
			Package pckg = packages.get(name);
			System.out.println("++++++++++++++++ PACKAGE ++++++++++++++++");
			System.out.println("++++++++++++++++ " + pckg + " ++++++++++++++++");
			System.out.println();
			System.out.println(" Ca: " + pckg.afferentCoupling() +
					" Ce: " + pckg.efferentCoupling());
			System.out.println("\tcontains:\t" + pckg.getClasses());
			System.out.println("\text. dep.:\t" + pckg.getExtDependencies());
			System.out.println("\tint. dep.:\t" + pckg.getIntDependencies());
			System.out.println();
			printClassInfo(pckg);
		}
	}
	
	public void printClassInfo(Package pckg) {
		System.out.println("++++++++++++++++ CLASSES ++++++++++++++++");
		System.out.println();
		for(String name : classes.keySet()) {
			if (pckg.getClasses().toString().contains(name)) {
			Clazz cls = classes.get(name);
			System.out.println(cls);
			if(cls.isInterface())
				System.out.println("interface");
			else if(cls.isAbstract())
				System.out.println("abstract class");
			else
				System.out.println("concrete class");
			System.out.println("LOC: " + cls.getLoc() + " WMC: " + cls.getWmc());
			System.out.println("\timport:\t" + cls.getImports());
			System.out.println();
			}
		}
	}

}
