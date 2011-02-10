package org.greatage.ioc.access;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class MockObject {
	private final String message;
	private int type = 10;

	public MockObject(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}
}
