package se.ck.martin.extensions;

public class DefaultPackage extends Package {
	
	private static final String DEFAULT = "(default)";

	DefaultPackage() {
		super(DEFAULT);
	}

	@Override
	public String getNameWithQualifier() {
		return "";
	}
}
