/* 
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package net.objecthunter.exp4j.tokenizer;

import units.qual.Dimensionless;
import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

public class Tokenizer {

    private final @Dimensionless char @Dimensionless [] expression;

    private final @Dimensionless int expressionLength;

    private final @Dimensionless Map<@Dimensionless String, @Dimensionless Function> userFunctions;

    private final @Dimensionless Map<@Dimensionless String, @Dimensionless Operator> userOperators;

    private final @Dimensionless Set<@Dimensionless String> variableNames;

    private final @Dimensionless boolean implicitMultiplication;

    private @Dimensionless int pos = ((@Dimensionless int) (0));

    private @Dimensionless Token lastToken;


    public Tokenizer(String expression, final Map<String, Function> userFunctions,
            final Map<String, Operator> userOperators, final Set<String> variableNames, final boolean implicitMultiplication) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
        this.variableNames = variableNames;
        this.implicitMultiplication = implicitMultiplication;
    }

    public Tokenizer(@Dimensionless String expression, final @Dimensionless Map<@Dimensionless String, @Dimensionless Function> userFunctions,
                     final @Dimensionless Map<@Dimensionless String, @Dimensionless Operator> userOperators, final @Dimensionless Set<@Dimensionless String> variableNames) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
        this.variableNames = variableNames;
        this.implicitMultiplication = true;
    }

    public boolean hasNext(@Dimensionless Tokenizer this) {
        return this.expression.length > pos;
    }

    public Token nextToken(@Dimensionless Tokenizer this){
        @Dimensionless
        char ch = expression[pos];
        while (Character.isWhitespace(ch)) {
            ch = expression[++pos];
        }
        if (Character.isDigit(ch) || ch == '.') {
            if (lastToken != null) {
                if (lastToken.getType() == Token.TOKEN_NUMBER) {
                    throw new @Dimensionless IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + (@Dimensionless int) ch + ") at [" + pos + "]");
                } else if (implicitMultiplication && (lastToken.getType() != Token.TOKEN_OPERATOR
                        && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                        && lastToken.getType() != Token.TOKEN_FUNCTION
                        && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                    // insert an implicit multiplication token
                    lastToken = new @Dimensionless OperatorToken(Operators.getBuiltinOperator('*', ((@Dimensionless int) (2))));
                    return lastToken;
                }
            }
            return parseNumberToken(ch);
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken(ch);
        } else if (isOpenParentheses(ch)) {
            if (lastToken != null && implicitMultiplication &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR
                            && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                            && lastToken.getType() != Token.TOKEN_FUNCTION
                            && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new @Dimensionless OperatorToken(Operators.getBuiltinOperator('*', ((@Dimensionless int) (2))));
                return lastToken;
            }
            return parseParentheses(true);
        } else if (isCloseParentheses(ch)) {
            return parseParentheses(false);
        } else if (Operator.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        } else if (isAlphabetic(ch) || ch == '_') {
            // parse the name which can be a setVariable or a function
            if (lastToken != null && implicitMultiplication &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR
                            && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                            && lastToken.getType() != Token.TOKEN_FUNCTION
                            && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new @Dimensionless OperatorToken(Operators.getBuiltinOperator('*', ((@Dimensionless int) (2))));
                return lastToken;
            }
            return parseFunctionOrVariable();

        }
        throw new @Dimensionless IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + (@Dimensionless int) ch + ") at [" + pos + "]");
    }

    private @Dimensionless Token parseArgumentSeparatorToken(@Dimensionless Tokenizer this, @Dimensionless char ch) {
        this.pos++;
        this.lastToken = new @Dimensionless ArgumentSeparatorToken();
        return lastToken;
    }

    private @Dimensionless boolean isArgumentSeparator(@Dimensionless Tokenizer this, @Dimensionless char ch) {
        return ch == ',';
    }

    private @Dimensionless Token parseParentheses(@Dimensionless Tokenizer this, final @Dimensionless boolean open) {
        if (open) {
            this.lastToken = new @Dimensionless OpenParenthesesToken();
        } else {
            this.lastToken = new @Dimensionless CloseParenthesesToken();
        }
        this.pos++;
        return lastToken;
    }

    private @Dimensionless boolean isOpenParentheses(@Dimensionless Tokenizer this, @Dimensionless char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    private @Dimensionless boolean isCloseParentheses(@Dimensionless Tokenizer this, @Dimensionless char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    private @Dimensionless Token parseFunctionOrVariable(@Dimensionless Tokenizer this) {
        final @Dimensionless int offset = this.pos;
        @Dimensionless
        int testPos;
        @Dimensionless
        int lastValidLen = ((@Dimensionless int) (1));
        @Dimensionless
        Token lastValidToken = null;
        @Dimensionless
        int len = ((@Dimensionless int) (1));
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        testPos = offset + len - ((@Dimensionless int) (1));
        while (!isEndOfExpression(testPos) &&
                isVariableOrFunctionCharacter(expression[testPos])) {
            @Dimensionless
            String name = new @Dimensionless String(expression, offset, len);
            if (variableNames != null && variableNames.contains(name)) {
                lastValidLen = len;
                lastValidToken = new @Dimensionless VariableToken(name);
            } else {
                final @Dimensionless Function f = getFunction(name);
                if (f != null) {
                    lastValidLen = len;
                    lastValidToken = new @Dimensionless FunctionToken(f);
                }
            }
            len++;
            testPos = offset + len - ((@Dimensionless int) (1));
        }
        if (lastValidToken == null) {
            throw new @Dimensionless UnknownFunctionOrVariableException(new @Dimensionless String(expression), pos, len);
        }
        pos += lastValidLen;
        lastToken = lastValidToken;
        return lastToken;
    }

    private @Dimensionless Function getFunction(@Dimensionless Tokenizer this, @Dimensionless String name) {
        @Dimensionless
        Function f = null;
        if (this.userFunctions != null) {
            f = this.userFunctions.get(name);
        }
        if (f == null) {
            f = Functions.getBuiltinFunction(name);
        }
        return f;
    }

    private @Dimensionless Token parseOperatorToken(@Dimensionless Tokenizer this, @Dimensionless char firstChar) {
        final @Dimensionless int offset = this.pos;
        @Dimensionless
        int len = ((@Dimensionless int) (1));
        final @Dimensionless StringBuilder symbol = new @Dimensionless StringBuilder();
        @Dimensionless
        Operator lastValid = null;
        symbol.append(firstChar);

        while (!isEndOfExpression(offset + len)  && Operator.isAllowedOperatorChar(expression[offset + len])) {
            symbol.append(expression[offset + len++]);
        }

        while (symbol.length() > ((@Dimensionless int) (0))) {
            @Dimensionless
            Operator op = this.getOperator(symbol.toString());
            if (op == null) {
                symbol.setLength(symbol.length() - ((@Dimensionless int) (1)));
            }else{
                lastValid = op;
                break;
            }
        }

        pos += symbol.length();
        lastToken = new @Dimensionless OperatorToken(lastValid);
        return lastToken;
    }

    private @Dimensionless Operator getOperator(@Dimensionless Tokenizer this, @Dimensionless String symbol) {
        @Dimensionless
        Operator op = null;
        if (this.userOperators != null) {
            op = this.userOperators.get(symbol);
        }
        if (op == null && symbol.length() == ((@Dimensionless int) (1))) {
            @Dimensionless
            int argc = ((@Dimensionless int) (2));
            if (lastToken == null) {
                argc = ((@Dimensionless int) (1));
            } else {
                @Dimensionless
                int lastTokenType = lastToken.getType();
                if (lastTokenType == Token.TOKEN_PARENTHESES_OPEN || lastTokenType == Token.TOKEN_SEPARATOR) {
                    argc = ((@Dimensionless int) (1));
                } else if (lastTokenType == Token.TOKEN_OPERATOR) {
                    final @Dimensionless Operator lastOp = ((@Dimensionless OperatorToken) lastToken).getOperator();
                    if (lastOp.getNumOperands() == ((@Dimensionless int) (2)) || (lastOp.getNumOperands() == ((@Dimensionless int) (1)) && !lastOp.isLeftAssociative())) {
                        argc = ((@Dimensionless int) (1));
                    }
                }

            }
            op = Operators.getBuiltinOperator(symbol.charAt(((@Dimensionless int) (0))), argc);
        }
        return op;
    }

    private @Dimensionless Token parseNumberToken(@Dimensionless Tokenizer this, final @Dimensionless char firstChar) {
        final @Dimensionless int offset = this.pos;
        @Dimensionless
        int len = ((@Dimensionless int) (1));
        this.pos++;
        if (isEndOfExpression(offset + len)) {
            lastToken = new @Dimensionless NumberToken(Double.parseDouble(String.valueOf(firstChar)));
            return lastToken;
        }
        while (!isEndOfExpression(offset + len) &&
                isNumeric(expression[offset + len], expression[offset + len - ((@Dimensionless int) (1))] == 'e' ||
                        expression[offset + len - ((@Dimensionless int) (1))] == 'E')) {
            len++;
            this.pos++;
        }
        // check if the e is at the end
        if (expression[offset + len - ((@Dimensionless int) (1))] == 'e' || expression[offset + len - ((@Dimensionless int) (1))] == 'E') {
            // since the e is at the end it's not part of the number and a rollback is necessary
            len--;
            pos--;
        }
        lastToken = new @Dimensionless NumberToken(expression, offset, len);
        return lastToken;
    }

    private static @Dimensionless boolean isNumeric(@Dimensionless char ch, @Dimensionless boolean lastCharE) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' ||
                (lastCharE && (ch == '-' || ch == '+'));
    }

    public static @Dimensionless boolean isAlphabetic(@Dimensionless int codePoint) {
        return Character.isLetter(codePoint);
    }

    public static @Dimensionless boolean isVariableOrFunctionCharacter(@Dimensionless int codePoint) {
        return isAlphabetic(codePoint) ||
                Character.isDigit(codePoint) ||
                codePoint == '_' ||
                codePoint == '.';
    }

    private @Dimensionless boolean isEndOfExpression(@Dimensionless Tokenizer this, @Dimensionless int offset) {
        return this.expressionLength <= offset;
    }
}
