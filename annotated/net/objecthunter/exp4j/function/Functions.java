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
package net.objecthunter.exp4j.function;
import units.qual.Dimensionless;
import units.qual.rad;

/**
 * Class representing the builtin functions available for use in expressions
 */
public class Functions {
    private static final @Dimensionless int INDEX_SIN = ((@Dimensionless int) (0));
    private static final @Dimensionless int INDEX_COS = ((@Dimensionless int) (1));
    private static final @Dimensionless int INDEX_TAN = ((@Dimensionless int) (2));
    private static final @Dimensionless int INDEX_COT = ((@Dimensionless int) (3));
    private static final @Dimensionless int INDEX_LOG = ((@Dimensionless int) (4));
    private static final @Dimensionless int INDEX_LOG1P = ((@Dimensionless int) (5));
    private static final @Dimensionless int INDEX_ABS = ((@Dimensionless int) (6));
    private static final @Dimensionless int INDEX_ACOS = ((@Dimensionless int) (7));
    private static final @Dimensionless int INDEX_ASIN = ((@Dimensionless int) (8));
    private static final @Dimensionless int INDEX_ATAN = ((@Dimensionless int) (9));
    private static final @Dimensionless int INDEX_CBRT = ((@Dimensionless int) (10));
    private static final @Dimensionless int INDEX_CEIL = ((@Dimensionless int) (11));
    private static final @Dimensionless int INDEX_FLOOR = ((@Dimensionless int) (12));
    private static final @Dimensionless int INDEX_SINH = ((@Dimensionless int) (13));
    private static final @Dimensionless int INDEX_SQRT = ((@Dimensionless int) (14));
    private static final @Dimensionless int INDEX_TANH = ((@Dimensionless int) (15));
    private static final @Dimensionless int INDEX_COSH = ((@Dimensionless int) (16));
    private static final @Dimensionless int INDEX_POW = ((@Dimensionless int) (17));
    private static final @Dimensionless int INDEX_EXP = ((@Dimensionless int) (18));
    private static final @Dimensionless int INDEX_EXPM1 = ((@Dimensionless int) (19));
    private static final @Dimensionless int INDEX_LOG10 = ((@Dimensionless int) (20));
    private static final @Dimensionless int INDEX_LOG2 = ((@Dimensionless int) (21));
    private static final @Dimensionless int INDEX_SGN = ((@Dimensionless int) (22));

    private static final @Dimensionless Function @Dimensionless [] builtinFunctions = new @Dimensionless Function @Dimensionless [((@Dimensionless int) (23))];

    static {
        builtinFunctions[INDEX_SIN] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("sin") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                return Math.sin(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_COS] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("cos") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                return Math.cos(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_TAN] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("tan") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                return Math.tan(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_COT] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("cot") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                @Dimensionless
                double tan = Math.tan(args[((@Dimensionless int) (0))]);
                if (tan == ((@Dimensionless double) (0d))) {
                    throw new @Dimensionless ArithmeticException("Division by zero in cotangent!");
                }
                return ((@Dimensionless double) (1d))/Math.tan(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_LOG] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("log") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.log(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_LOG2] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("log2") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.log(args[((@Dimensionless int) (0))]) / Math.log(((@Dimensionless double) (2d)));
            }
        };
        builtinFunctions[INDEX_LOG10] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("log10") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.log10(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_LOG1P] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("log1p") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.log1p(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_ABS] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("abs") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.abs(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_ACOS] = new @Dimensionless Function<@rad Double, @Dimensionless Double>("acos") {
            @Override
            public @rad Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.acos(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_ASIN] = new @Dimensionless Function<@rad Double, @Dimensionless Double>("asin") {
            @Override
            public @rad Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.asin(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_ATAN] = new @Dimensionless Function<@rad Double, @Dimensionless Double>("atan") {
            @Override
            public @rad Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.atan(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_CBRT] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("cbrt") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.cbrt(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_FLOOR] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("floor") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.floor(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_SINH] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("sinh") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                return Math.sinh(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_SQRT] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("sqrt") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.sqrt(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_TANH] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("tanh") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                return Math.tanh(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_COSH] = new @Dimensionless Function<@Dimensionless Double, @rad Double>("cosh") {
            @Override
            public @Dimensionless Double apply(@rad Double @Dimensionless ... args) {
                return Math.cosh(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_CEIL] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("ceil") {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.ceil(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_POW] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("pow", ((@Dimensionless int) (2))) {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.pow(args[((@Dimensionless int) (0))], args[((@Dimensionless int) (1))]);
            }
        };
        builtinFunctions[INDEX_EXP] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("exp", ((@Dimensionless int) (1))) {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.exp(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_EXPM1] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("expm1", ((@Dimensionless int) (1))) {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                return Math.expm1(args[((@Dimensionless int) (0))]);
            }
        };
        builtinFunctions[INDEX_SGN] = new @Dimensionless Function<@Dimensionless Double, @Dimensionless Double>("signum", ((@Dimensionless int) (1))) {
            @Override
            public @Dimensionless Double apply(@Dimensionless Double @Dimensionless ... args) {
                if (args[((@Dimensionless int) (0))] > ((@Dimensionless int) (0))) {
                    return ((@Dimensionless double) (1d));
                } else if (args[((@Dimensionless int) (0))] < ((@Dimensionless int) (0))) {
                    return - ((@Dimensionless double) (1d));
                } else {
                    return ((@Dimensionless double) (0d));
                }
            }
        };
    }

    /**
     * Get the builtin function for a given name
     * @param name te name of the function
     * @return a Function instance
     */
    public static Function getBuiltinFunction(final String name) {

        if (name.equals("sin")) {
            return builtinFunctions[INDEX_SIN];
        } else if (name.equals("cos")) {
            return builtinFunctions[INDEX_COS];
        } else if (name.equals("tan")) {
            return builtinFunctions[INDEX_TAN];
        } else if (name.equals("cot")) {
            return builtinFunctions[INDEX_COT];
        } else if (name.equals("asin")) {
            return builtinFunctions[INDEX_ASIN];
        } else if (name.equals("acos")) {
            return builtinFunctions[INDEX_ACOS];
        } else if (name.equals("atan")) {
            return builtinFunctions[INDEX_ATAN];
        } else if (name.equals("sinh")) {
            return builtinFunctions[INDEX_SINH];
        } else if (name.equals("cosh")) {
            return builtinFunctions[INDEX_COSH];
        } else if (name.equals("tanh")) {
            return builtinFunctions[INDEX_TANH];
        } else if (name.equals("abs")) {
            return builtinFunctions[INDEX_ABS];
        } else if (name.equals("log")) {
            return builtinFunctions[INDEX_LOG];
        } else if (name.equals("log10")) {
            return builtinFunctions[INDEX_LOG10];
        } else if (name.equals("log2")) {
            return builtinFunctions[INDEX_LOG2];
        } else if (name.equals("log1p")) {
            return builtinFunctions[INDEX_LOG1P];
        } else if (name.equals("ceil")) {
            return builtinFunctions[INDEX_CEIL];
        } else if (name.equals("floor")) {
            return builtinFunctions[INDEX_FLOOR];
        } else if (name.equals("sqrt")) {
            return builtinFunctions[INDEX_SQRT];
        } else if (name.equals("cbrt")) {
            return builtinFunctions[INDEX_CBRT];
        } else if (name.equals("pow")) {
            return builtinFunctions[INDEX_POW];
        } else if (name.equals("exp")) {
            return builtinFunctions[INDEX_EXP];
        } else if (name.equals("expm1")) {
            return builtinFunctions[INDEX_EXPM1];
        } else if (name.equals("signum")) {
            return builtinFunctions[INDEX_SGN];
        } else {
            return null;
        }
    }

}
