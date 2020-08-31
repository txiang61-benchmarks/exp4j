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
package net.objecthunter.exp4j.shuntingyard;

import units.qual.Dimensionless;
import java.util.*;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

/**
 * Shunting yard implementation to convert infix to reverse polish notation
 */
@Dimensionless
public class ShuntingYard {

    /**
     * Convert a Set of tokens from infix to reverse polish notation
     * @param expression the expression to convert
     * @param userFunctions the custom functions used
     * @param userOperators the custom operators used
     * @param variableNames the variable names used in the expression
     * @param implicitMultiplication set to fasle to turn off implicit multiplication
     * @return a {@link net.objecthunter.exp4j.tokenizer.Token} array containing the result
     */
    public static @Dimensionless Token @Dimensionless [] convertToRPN(final @Dimensionless String expression, final @Dimensionless Map<@Dimensionless String, @Dimensionless Function> userFunctions,
            final @Dimensionless Map<@Dimensionless String, @Dimensionless Operator> userOperators, final @Dimensionless Set<@Dimensionless String> variableNames, final @Dimensionless boolean implicitMultiplication){
        final @Dimensionless Stack<@Dimensionless Token> stack = new @Dimensionless Stack<@Dimensionless Token>();
        final @Dimensionless List<@Dimensionless Token> output = new @Dimensionless ArrayList<@Dimensionless Token>();

        final @Dimensionless Tokenizer tokenizer = new @Dimensionless Tokenizer(expression, userFunctions, userOperators, variableNames, implicitMultiplication);
        while (tokenizer.hasNext()) {
            @Dimensionless
            Token token = tokenizer.nextToken();
            switch (token.getType()) {
            case Token.TOKEN_NUMBER:
            case Token.TOKEN_VARIABLE:
                output.add(token);
                break;
            case Token.TOKEN_FUNCTION:
                stack.add(token);
                break;
            case Token.TOKEN_SEPARATOR:
                while (!stack.empty() && stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    output.add(stack.pop());
                }
                if (stack.empty() || stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    throw new @Dimensionless IllegalArgumentException("Misplaced function separator ',' or mismatched parentheses");
                }
                break;
            case Token.TOKEN_OPERATOR:
                while (!stack.empty() && stack.peek().getType() == Token.TOKEN_OPERATOR) {
                    @Dimensionless
                    OperatorToken o1 = (@Dimensionless OperatorToken) token;
                    @Dimensionless
                    OperatorToken o2 = (@Dimensionless OperatorToken) stack.peek();
                    if (o1.getOperator().getNumOperands() == ((@Dimensionless int) (1)) && o2.getOperator().getNumOperands() == ((@Dimensionless int) (2))) {
                        break;
                    } else if ((o1.getOperator().isLeftAssociative() && o1.getOperator().getPrecedence() <= o2.getOperator().getPrecedence())
                            || (o1.getOperator().getPrecedence() < o2.getOperator().getPrecedence())) {
                        output.add(stack.pop());
                    }else {
                        break;
                    }
                }
                stack.push(token);
                break;
            case Token.TOKEN_PARENTHESES_OPEN:
                stack.push(token);
                break;
            case Token.TOKEN_PARENTHESES_CLOSE:
                while (stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    output.add(stack.pop());
                }
                stack.pop();
                if (!stack.isEmpty() && stack.peek().getType() == Token.TOKEN_FUNCTION) {
                    output.add(stack.pop());
                }
                break;
            default:
                throw new @Dimensionless IllegalArgumentException("Unknown Token type encountered. This should not happen");
            }
        }
        while (!stack.empty()) {
            @Dimensionless
            Token t = stack.pop();
            if (t.getType() == Token.TOKEN_PARENTHESES_CLOSE || t.getType() == Token.TOKEN_PARENTHESES_OPEN) {
                throw new @Dimensionless IllegalArgumentException("Mismatched parentheses detected. Please check the expression");
            } else {
                output.add(t);
            }
        }
        return (@Dimensionless Token @Dimensionless []) output.toArray(new Token @Dimensionless [output.size()]);
    }
}
