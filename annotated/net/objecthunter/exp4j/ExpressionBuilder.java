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

package net.objecthunter.exp4j;

import units.qual.Dimensionless;
import java.util.*;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;

/**
 * Factory class for {@link Expression} instances. This class is the main API entrypoint. Users should create new
 * {@link Expression} instances using this factory class.
 */
@Dimensionless
public class ExpressionBuilder {

    private final @Dimensionless String expression;

    private final @Dimensionless Map<@Dimensionless String, @Dimensionless Function> userFunctions;

    private final @Dimensionless Map<@Dimensionless String, @Dimensionless Operator> userOperators;

    private final @Dimensionless Set<@Dimensionless String> variableNames;

    private @Dimensionless boolean implicitMultiplication = true;

    /**
     * Create a new ExpressionBuilder instance and initialize it with a given expression string.
     * @param expression the expression to be parsed
     */
    public ExpressionBuilder(@Dimensionless String expression) {
        if (expression == null || expression.trim().length() == ((@Dimensionless int) (0))) {
            throw new @Dimensionless IllegalArgumentException("Expression can not be empty");
        }
        this.expression = expression;
        this.userOperators = new @Dimensionless HashMap<@Dimensionless String, @Dimensionless Operator>(((@Dimensionless int) (4)));
        this.userFunctions = new @Dimensionless HashMap<@Dimensionless String, @Dimensionless Function>(((@Dimensionless int) (4)));
        this.variableNames = new @Dimensionless HashSet<@Dimensionless String>(((@Dimensionless int) (4)));
    }

    /**
     * Add a {@link net.objecthunter.exp4j.function.Function} implementation available for use in the expression
     * @param function the custom {@link net.objecthunter.exp4j.function.Function} implementation that should be available for use in the expression.
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder function(@Dimensionless ExpressionBuilder this, @Dimensionless Function function) {
        this.userFunctions.put(function.getName(), function);
        return this;
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.function.Function} implementations available for use in the expression
     * @param functions the custom {@link net.objecthunter.exp4j.function.Function} implementations
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder functions(@Dimensionless ExpressionBuilder this, @Dimensionless Function @Dimensionless ... functions) {
        for (@Dimensionless Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.function.Function} implementations available for use in the expression
     * @param functions A {@link java.util.List} of custom {@link net.objecthunter.exp4j.function.Function} implementations
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder functions(@Dimensionless ExpressionBuilder this, @Dimensionless List<@Dimensionless Function> functions) {
        for (@Dimensionless Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    /**
     * Declare variable names used in the expression
     * @param variableNames the variables used in the expression
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder variables(@Dimensionless ExpressionBuilder this, @Dimensionless Set<@Dimensionless String> variableNames) {
        this.variableNames.addAll(variableNames);
        return this;
    }

    /**
     * Declare variable names used in the expression
     * @param variableNames the variables used in the expression
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder variables(@Dimensionless ExpressionBuilder this, @Dimensionless String @Dimensionless ... variableNames) {
        Collections.addAll(this.variableNames, variableNames);
        return this;
    }

    /**
     * Declare a variable used in the expression
     * @param variableName the variable used in the expression
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder variable(@Dimensionless ExpressionBuilder this, @Dimensionless String variableName) {
        this.variableNames.add(variableName);
        return this;
    }

    public @Dimensionless ExpressionBuilder implicitMultiplication(@Dimensionless ExpressionBuilder this, @Dimensionless boolean enabled) {
        this.implicitMultiplication = enabled;
        return this;
    }

    /**
     * Add an {@link net.objecthunter.exp4j.operator.Operator} which should be available for use in the expression
     * @param operator the custom {@link net.objecthunter.exp4j.operator.Operator} to add
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder operator(@Dimensionless ExpressionBuilder this, @Dimensionless Operator operator) {
        this.checkOperatorSymbol(operator);
        this.userOperators.put(operator.getSymbol(), operator);
        return this;
    }

    private void checkOperatorSymbol(@Dimensionless ExpressionBuilder this, @Dimensionless Operator op) {
        @Dimensionless
        String name = op.getSymbol();
        for (@Dimensionless char ch : name.toCharArray()) {
            if (!Operator.isAllowedOperatorChar(ch)) {
                throw new @Dimensionless IllegalArgumentException("The operator symbol '" + name + "' is invalid");
            }
        }
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.operator.Operator} implementations which should be available for use in the expression
     * @param operators the set of custom {@link net.objecthunter.exp4j.operator.Operator} implementations to add
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder operator(@Dimensionless ExpressionBuilder this, @Dimensionless Operator @Dimensionless ... operators) {
        for (@Dimensionless Operator o : operators) {
            this.operator(o);
        }
        return this;
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.operator.Operator} implementations which should be available for use in the expression
     * @param operators the {@link java.util.List} of custom {@link net.objecthunter.exp4j.operator.Operator} implementations to add
     * @return the ExpressionBuilder instance
     */
    public @Dimensionless ExpressionBuilder operator(@Dimensionless ExpressionBuilder this, @Dimensionless List<@Dimensionless Operator> operators) {
        for (@Dimensionless Operator o : operators) {
            this.operator(o);
        }
        return this;
    }

    /**
     * Build the {@link Expression} instance using the custom operators and functions set.
     * @return an {@link Expression} instance which can be used to evaluate the result of the expression
     */
    public @Dimensionless Expression build(@Dimensionless ExpressionBuilder this) {
        if (expression.length() == ((@Dimensionless int) (0))) {
            throw new @Dimensionless IllegalArgumentException("The expression can not be empty");
        }
        /* set the contants' varibale names */
        variableNames.add("pi");
        variableNames.add("π");
        variableNames.add("e");
        variableNames.add("φ");
        /* Check if there are duplicate vars/functions */
        for (@Dimensionless String var : variableNames) {
            if (Functions.getBuiltinFunction(var) != null || userFunctions.containsKey(var)) {
                throw new @Dimensionless IllegalArgumentException("A variable can not have the same name as a function [" + var + "]");
            }
        }
        return new @Dimensionless Expression(ShuntingYard.convertToRPN(this.expression, this.userFunctions, this.userOperators,
                this.variableNames, this.implicitMultiplication), this.userFunctions.keySet());
    }

}
