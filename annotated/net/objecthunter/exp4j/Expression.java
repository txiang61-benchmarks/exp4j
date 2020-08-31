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
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.VariableToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Dimensionless
public class Expression {

    private final @Dimensionless Token @Dimensionless [] tokens;

    private final @Dimensionless Map<@Dimensionless String, @Dimensionless Double> variables;

    private final @Dimensionless Set<@Dimensionless String> userFunctionNames;

    private static @Dimensionless Map<@Dimensionless String, @Dimensionless Double> createDefaultVariables() {
        final @Dimensionless Map<@Dimensionless String, @Dimensionless Double> vars = new @Dimensionless HashMap<@Dimensionless String, @Dimensionless Double>(((@Dimensionless int) (4)));
        vars.put("pi", ((@Dimensionless double) (Math.PI)));
        vars.put("π", ((@Dimensionless double) (Math.PI)));
        vars.put("φ", ((@Dimensionless double) (1.61803398874d)));
        vars.put("e", ((@Dimensionless double) (Math.E)));
        return vars;
    }
    
    /**
     * Creates a new expression that is a copy of the existing one.
     * 
     * @param existing the expression to copy
     */
    public Expression(final @Dimensionless Expression existing) {
    	this.tokens = Arrays.copyOf(existing.tokens, existing.tokens.length);
    	this.variables = new @Dimensionless HashMap<@Dimensionless String, @Dimensionless Double>();
    	this.variables.putAll(existing.variables);
    	this.userFunctionNames = new @Dimensionless HashSet<@Dimensionless String>(existing.userFunctionNames);
    }

    Expression(final @Dimensionless Token @Dimensionless [] tokens) {
        this.tokens = tokens;
        this.variables = createDefaultVariables();
        this.userFunctionNames = Collections.<String>emptySet();
    }

    Expression(final @Dimensionless Token @Dimensionless [] tokens, @Dimensionless Set<@Dimensionless String> userFunctionNames) {
        this.tokens = tokens;
        this.variables = createDefaultVariables();
        this.userFunctionNames = userFunctionNames;
    }

    public @Dimensionless Expression setVariable(@Dimensionless Expression this, final @Dimensionless String name, final @Dimensionless double value) {
        this.checkVariableName(name);
        this.variables.put(name, Double.valueOf(value));
        return this;
    }

    private void checkVariableName(@Dimensionless Expression this, @Dimensionless String name) {
        if (this.userFunctionNames.contains(name) || Functions.getBuiltinFunction(name) != null) {
            throw new @Dimensionless IllegalArgumentException("The variable name '" + name + "' is invalid. Since there exists a function with the same name");
        }
    }

    public @Dimensionless Expression setVariables(@Dimensionless Expression this, @Dimensionless Map<@Dimensionless String, @Dimensionless Double> variables) {
        for (Map.@Dimensionless Entry<@Dimensionless String, @Dimensionless Double> v : variables.entrySet()) {
            this.setVariable(v.getKey(), v.getValue());
        }
        return this;
    }

    public @Dimensionless Set<@Dimensionless String> getVariableNames(@Dimensionless Expression this) {
        final @Dimensionless Set<@Dimensionless String> variables = new @Dimensionless HashSet<@Dimensionless String>();
        for (final @Dimensionless Token t: tokens) {
            if (t.getType() == Token.TOKEN_VARIABLE)
                variables.add(((@Dimensionless VariableToken)t).getName());
        }
        return variables;
    }

    public @Dimensionless ValidationResult validate(@Dimensionless Expression this, @Dimensionless boolean checkVariablesSet) {
        final @Dimensionless List<@Dimensionless String> errors = new @Dimensionless ArrayList<@Dimensionless String>(((@Dimensionless int) (0)));
        if (checkVariablesSet) {
            /* check that all vars have a value set */
            for (final @Dimensionless Token t : this.tokens) {
                if (t.getType() == Token.TOKEN_VARIABLE) {
                    final @Dimensionless String var = ((@Dimensionless VariableToken) t).getName();
                    if (!variables.containsKey(var)) {
                        errors.add("The setVariable '" + var + "' has not been set");
                    }
                }
            }
        }

        /* Check if the number of operands, functions and operators match.
           The idea is to increment a counter for operands and decrease it for operators.
           When a function occurs the number of available arguments has to be greater
           than or equals to the function's expected number of arguments.
           The count has to be larger than 1 at all times and exactly 1 after all tokens
           have been processed */
        @Dimensionless
        int count = ((@Dimensionless int) (0));
        for (@Dimensionless Token tok : this.tokens) {
            switch (tok.getType()) {
                case Token.TOKEN_NUMBER:
                case Token.TOKEN_VARIABLE:
                    count++;
                    break;
                case Token.TOKEN_FUNCTION:
                    final @Dimensionless Function func = ((@Dimensionless FunctionToken) tok).getFunction();
                    final @Dimensionless int argsNum = func.getNumArguments(); 
                    if (argsNum > count) {
                        errors.add("Not enough arguments for '" + func.getName() + "'");
                    }
                    if (argsNum > ((@Dimensionless int) (1))) {
                        count -= argsNum - ((@Dimensionless int) (1));
                    } else if (argsNum == ((@Dimensionless int) (0))) {
                        // see https://github.com/fasseg/exp4j/issues/59
                        count++;
                    }
                    break;
                case Token.TOKEN_OPERATOR:
                    @Dimensionless
                    Operator op = ((@Dimensionless OperatorToken) tok).getOperator();
                    if (op.getNumOperands() == ((@Dimensionless int) (2))) {
                        count--;
                    }
                    break;
            }
            if (count < ((@Dimensionless int) (1))) {
                errors.add("Too many operators");
                return new @Dimensionless ValidationResult(false, errors);
            }
        }
        if (count > ((@Dimensionless int) (1))) {
            errors.add("Too many operands");
        }
        return errors.size() == ((@Dimensionless int) (0)) ? ValidationResult.SUCCESS : new @Dimensionless ValidationResult(false, errors);

    }

    public @Dimensionless ValidationResult validate(@Dimensionless Expression this) {
        return validate(true);
    }

    public @Dimensionless Future<@Dimensionless Double> evaluateAsync(@Dimensionless Expression this, @Dimensionless ExecutorService executor) {
        return executor.submit(new @Dimensionless Callable<@Dimensionless Double>() {
            @Override
            public @Dimensionless Double call() throws Exception {
                return evaluate();
            }
        });
    }

    public @Dimensionless double evaluate(@Dimensionless Expression this) {
        final @Dimensionless ArrayStack output = new @Dimensionless ArrayStack();
        for (@Dimensionless int i = ((@Dimensionless int) (0)); i < tokens.length; i++) {
            @Dimensionless
            Token t = tokens[i];
            if (t.getType() == Token.TOKEN_NUMBER) {
                output.push(((@Dimensionless NumberToken) t).getValue());
            } else if (t.getType() == Token.TOKEN_VARIABLE) {
                final @Dimensionless String name = ((@Dimensionless VariableToken) t).getName();
                final @Dimensionless Double value = this.variables.get(name);
                if (value == null) {
                    throw new @Dimensionless IllegalArgumentException("No value has been set for the setVariable '" + name + "'.");
                }
                output.push(value);
            } else if (t.getType() == Token.TOKEN_OPERATOR) {
                @Dimensionless
                OperatorToken op = (@Dimensionless OperatorToken) t;
                if (output.size() < op.getOperator().getNumOperands()) {
                    throw new @Dimensionless IllegalArgumentException("Invalid number of operands available for '" + op.getOperator().getSymbol() + "' operator");
                }
                if (op.getOperator().getNumOperands() == ((@Dimensionless int) (2))) {
                    /* pop the operands and push the result of the operation */
                    @Dimensionless
                    double rightArg = output.pop();
                    @Dimensionless
                    double leftArg = output.pop();
                    output.push(op.getOperator().apply(leftArg, rightArg));
                } else if (op.getOperator().getNumOperands() == ((@Dimensionless int) (1))) {
                    /* pop the operand and push the result of the operation */
                    @Dimensionless
                    double arg = output.pop();
                    output.push(op.getOperator().apply(arg));
                }
            } else if (t.getType() == Token.TOKEN_FUNCTION) {
                @Dimensionless
                FunctionToken func = (@Dimensionless FunctionToken) t;
                final @Dimensionless int numArguments = func.getFunction().getNumArguments();
                if (output.size() < numArguments) {
                    throw new @Dimensionless IllegalArgumentException("Invalid number of arguments available for '" + func.getFunction().getName() + "' function");
                }
                /* collect the arguments from the stack */
                @Dimensionless
                Double @Dimensionless [] args = new @Dimensionless Double @Dimensionless [numArguments];
                for (@Dimensionless int j = numArguments - ((@Dimensionless int) (1)); j >= ((@Dimensionless int) (0)); j--) {
                    args[j] = output.pop();
                }
                output.push(func.getFunction().apply(args));
            }
        }
        if (output.size() > ((@Dimensionless int) (1))) {
            throw new @Dimensionless IllegalArgumentException("Invalid number of items on the output queue. Might be caused by an invalid number of arguments for a function.");
        }
        return output.pop();
    }
}
