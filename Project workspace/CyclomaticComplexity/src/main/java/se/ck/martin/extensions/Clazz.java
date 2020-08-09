package se.ck.martin.extensions;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

import com.github.mauricioaniche.ck.CKClassResult;

public class Clazz implements Comparable<Clazz>{
	
	private String className;
	private int modifiers;
	private boolean isInterface;
	
	private int loc;
	private int wmc;

	private Package pckg;
	private Set<Clazz> imports = new TreeSet<>();
	
	Clazz(Application app, String name) {
		super();
		String packageName;
		int pos = name.lastIndexOf('.');
		if(pos == -1) {
			packageName = null;
			this.className = name;
		} else {
			packageName = name.substring(0, pos);
			this.className = name.substring(pos + 1);
		}
		this.pckg = app.addPackage(packageName);
		this.pckg.add(this);
	}
	
	void set(CKClassResult result) {
		this.modifiers = result.getModifiers();
		this.isInterface = result.getType().equals("interface");
		this.loc = result.getLoc();
		this.wmc = result.getWmc();
		
	}
	
	public int getLoc() {
		return loc;
	}

	public int getWmc() {
		return wmc;
	}

	public boolean isAbstract() {
		return Modifier.isAbstract(modifiers);
	}
	
	public boolean isInterface() {
		return isInterface;
	}

	public Package getPackage() {
		return this.pckg;
	}
	
	void addImport(Clazz cls) {
		imports.add(cls);
	}

	Set<Clazz> getImports() {
		return imports;
	}

	boolean importsFrom(Package p) {
		for(Clazz cls : imports)
			if(cls.getPackage() == p)
				return true;
		return false;
	}

	public String getName() {
		return pckg.getNameWithQualifier() + className;
	}

	@Override
	public int compareTo(Clazz cls) {
		return this.getName().compareTo(cls.getName());
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
