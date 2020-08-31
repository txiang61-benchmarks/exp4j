package net.objecthunter.exp4j.tokenizer;
import units.qual.Dimensionless;

/**
 * This exception is being thrown whenever {@link Tokenizer} finds unknown function or variable.
 *
 * @author Bartosz Firyn (sarxos)
 */
public class UnknownFunctionOrVariableException extends IllegalArgumentException {

	/**
	 * Serial version UID.
	 */
	private static final @Dimensionless long serialVersionUID = ((@Dimensionless long) (1L));

	private final @Dimensionless String message;
	private final @Dimensionless String expression;
	private final @Dimensionless String token;
	private final @Dimensionless int position;

	public UnknownFunctionOrVariableException(String expression, int position, int length) {
		this.expression = expression;
		this.token = token(expression, position, length);
		this.position = position;
		this.message = "Unknown function or variable '" + token + "' at pos " + position + " in expression '" + expression + "'";
	}

	private static @Dimensionless String token(@Dimensionless String expression, @Dimensionless int position, @Dimensionless int length) {

		@Dimensionless
		int len = expression.length();
		@Dimensionless
		int end = position + length - ((@Dimensionless int) (1));

		if (len < end) {
			end = len;
		}

		return expression.substring(position, end);
	}

	@Override
	public @Dimensionless String getMessage(@Dimensionless UnknownFunctionOrVariableException this) {
		return message;
	}

	/**
	 * @return Expression which contains unknown function or variable
	 */
	public @Dimensionless String getExpression(@Dimensionless UnknownFunctionOrVariableException this) {
		return expression;
	}

	/**
	 * @return The name of unknown function or variable
	 */
	public @Dimensionless String getToken(@Dimensionless UnknownFunctionOrVariableException this) {
		return token;
	}

	/**
	 * @return The position of unknown function or variable
	 */
	public @Dimensionless int getPosition(@Dimensionless UnknownFunctionOrVariableException this) {
		return position;
	}
}
