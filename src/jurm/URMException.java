package jurm;

public class URMException extends Exception {
	private final String message;

	public URMException(Object object,String message) {
		if (object == null)
			this.message = message;
		else
			this.message = object
				.getClass()
				.getSimpleName() + " : " + message + "\n";
	}

	@Override
	public String toString() {
		return this.message;
	}
}