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
package net.objecthunter.exp4j.operator;
import units.qual.Dimensionless;

@Dimensionless
public abstract class Operators {
    private static final @Dimensionless int INDEX_ADDITION = ((@Dimensionless int) (0));
    private static final @Dimensionless int INDEX_SUBTRACTION = ((@Dimensionless int) (1));
    private static final @Dimensionless int INDEX_MUTLIPLICATION = ((@Dimensionless int) (2));
    private static final @Dimensionless int INDEX_DIVISION = ((@Dimensionless int) (3));
    private static final @Dimensionless int INDEX_POWER = ((@Dimensionless int) (4));
    private static final @Dimensionless int INDEX_MODULO = ((@Dimensionless int) (5));
    private static final @Dimensionless int INDEX_UNARYMINUS = ((@Dimensionless int) (6));
    private static final @Dimensionless int INDEX_UNARYPLUS = ((@Dimensionless int) (7));

    private static final @Dimensionless Operator @Dimensionless [] builtinOperators = new @Dimensionless Operator @Dimensionless [((@Dimensionless int) (8))];

    static {
        builtinOperators[INDEX_ADDITION]= new @Dimensionless Operator("+", ((@Dimensionless int) (2)), true, Operator.PRECEDENCE_ADDITION) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                return args[((@Dimensionless int) (0))] + args[((@Dimensionless int) (1))];
            }
        };
        builtinOperators[INDEX_SUBTRACTION]= new @Dimensionless Operator("-", ((@Dimensionless int) (2)), true, Operator.PRECEDENCE_ADDITION) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                return args[((@Dimensionless int) (0))] - args[((@Dimensionless int) (1))];
            }
        };
        builtinOperators[INDEX_UNARYMINUS]= new @Dimensionless Operator("-", ((@Dimensionless int) (1)), false, Operator.PRECEDENCE_UNARY_MINUS) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                return -args[((@Dimensionless int) (0))];
            }
        };
        builtinOperators[INDEX_UNARYPLUS]= new @Dimensionless Operator("+", ((@Dimensionless int) (1)), false, Operator.PRECEDENCE_UNARY_PLUS) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                return args[((@Dimensionless int) (0))];
            }
        };
        builtinOperators[INDEX_MUTLIPLICATION]= new @Dimensionless Operator("*", ((@Dimensionless int) (2)), true, Operator.PRECEDENCE_MULTIPLICATION) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                return args[((@Dimensionless int) (0))] * args[((@Dimensionless int) (1))];
            }
        };
        builtinOperators[INDEX_DIVISION]= new @Dimensionless Operator("/", ((@Dimensionless int) (2)), true, Operator.PRECEDENCE_DIVISION) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                if (args[((@Dimensionless int) (1))] == ((@Dimensionless double) (0d))) {
                    throw new @Dimensionless ArithmeticException("Division by zero!");
                }
                return args[((@Dimensionless int) (0))] / args[((@Dimensionless int) (1))];
            }
        };
        builtinOperators[INDEX_POWER]= new @Dimensionless Operator("^", ((@Dimensionless int) (2)), false, Operator.PRECEDENCE_POWER) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                return Math.pow(args[((@Dimensionless int) (0))], args[((@Dimensionless int) (1))]);
            }
        };
        builtinOperators[INDEX_MODULO]= new @Dimensionless Operator("%", ((@Dimensionless int) (2)), true, Operator.PRECEDENCE_MODULO) @Dimensionless {
            @Override
            public @Dimensionless double apply(final @Dimensionless double @Dimensionless ... args) {
                if (args[((@Dimensionless int) (1))] == ((@Dimensionless double) (0d))) {
                    throw new @Dimensionless ArithmeticException("Division by zero!");
                }
                return args[((@Dimensionless int) (0))] % args[((@Dimensionless int) (1))];
            }
        };
    }

    public static @Dimensionless Operator getBuiltinOperator(final @Dimensionless char symbol, final @Dimensionless int numArguments) {
        switch(symbol) {
            case '+':
                if (numArguments != ((@Dimensionless int) (1))) {
                    return builtinOperators[INDEX_ADDITION];
                }else{
                    return builtinOperators[INDEX_UNARYPLUS];
                }
            case '-':
                if (numArguments != ((@Dimensionless int) (1))) {
                    return builtinOperators[INDEX_SUBTRACTION];
                }else{
                    return builtinOperators[INDEX_UNARYMINUS];
                }
            case '*':
                return builtinOperators[INDEX_MUTLIPLICATION];
            case '/':
                return builtinOperators[INDEX_DIVISION];
            case '^':
                return builtinOperators[INDEX_POWER];
            case '%':
                return builtinOperators[INDEX_MODULO];
            default:
                return null;
        }
    }

}
