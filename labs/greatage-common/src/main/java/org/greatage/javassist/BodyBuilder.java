package org.greatage.javassist;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BodyBuilder {
	final StringBuilder builder = new StringBuilder("{");

	final BodyBuilder parent;

	BodyBuilder() {
		this(null);
	}

	BodyBuilder(final BodyBuilder parent) {
		this.parent = parent;
	}

	public BodyBuilder add(final String code) {
		builder.append(code);
		return this;
	}

	public BodyBuilder addIf(final String condition) {
		builder.append("if (").append(condition).append(")");
		return new BodyBuilder(this);
	}

	public BodyBuilder addElseIf(final String condition) {
		builder.append("else if (").append(condition).append(")");
		return new BodyBuilder(this);
	}

	public BodyBuilder addElse() {
		builder.append("else ");
		return new BodyBuilder(this);
	}

	public BodyBuilder end() {
		builder.append("}");
		if (parent != null) {
			parent.add(builder.toString());
		} else {

		}
		return parent;
	}
}
