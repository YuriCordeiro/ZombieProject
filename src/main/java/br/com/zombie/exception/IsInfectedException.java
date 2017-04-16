package br.com.zombie.exception;

public class IsInfectedException extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	

	public IsInfectedException() {
		super();
	}

	public IsInfectedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public IsInfectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public IsInfectedException(String message) {
		super(message);
	}

	public IsInfectedException(Throwable cause) {
		super(cause);
	}

}
