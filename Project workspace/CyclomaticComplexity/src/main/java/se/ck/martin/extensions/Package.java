package se.ck.martin.extensions;

import java.util.Set;
import java.util.TreeSet;

public class Package implements Comparable<Package>{
	
	private String name;
	private Set<Clazz> classes = new TreeSet<>();
	private Set<Clazz> extDependencies = new TreeSet<>();
	private Set<Clazz> intDependencies = new TreeSet<>();

	Package(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	String getNameWithQualifier() {
		return name + ".";
	}

	void add(Clazz clazz) {
		this.classes.add(clazz);
		for(Clazz importedCls : clazz.getImports())
			if(importedCls.getPackage() != this)
				extDependencies.add(importedCls);
	}

	public Set<Clazz> getClasses() {
		return classes;
	}

	void addExtDependency(Clazz cls) {
		extDependencies.add(cls);
	}

	void addIntDependency(Clazz cls) {
		intDependencies.add(cls);
	}

	Set<Clazz> getExtDependencies() {
		return extDependencies;
	}

	Set<Clazz> getIntDependencies() {
		return intDependencies;
	}
	
	public int afferentCoupling() {
		return extDependencies.size();
	}
	
	public int efferentCoupling() {
		return intDependencies.size();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Package p) {
		return this.toString().compareTo(p.toString());
	}
	
}
