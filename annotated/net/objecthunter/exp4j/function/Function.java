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

/**
 * A class representing a Function which can be used in an expression
 */
public abstract class Function <R extends Double, P extends Double> {

    protected final @Dimensionless String name;

    protected final @Dimensionless int numArguments;

    /**
     * Create a new Function with a given name and number of arguments
     * 
     * @param name the name of the Function
     * @param numArguments the number of arguments the function takes
     */
    public Function(String name, int numArguments) {
        if (numArguments < ((@Dimensionless int) (0))) {
            throw new @Dimensionless IllegalArgumentException("The number of function arguments can not be less than 0 for '" +
                    name + "'");
        }
        if (!isValidFunctionName(name)) {
            throw new @Dimensionless IllegalArgumentException("The function name '" + name + "' is invalid");
        }
        this.name = name;
        this.numArguments = numArguments;

    }

    /**
     * Create a new Function with a given name that takes a single argument
     * 
     * @param name the name of the Function
     */
    public Function(String name) {
        this(name, ((@Dimensionless int) (1)));
    }

    /**
     * Get the name of the Function
     * 
     * @return the name
     */
    public String getName(@Dimensionless Function<@Dimensionless R, @Dimensionless P> this) {
        return name;
    }

    /**
     * Get the number of arguments for this function
     * 
     * @return the number of arguments
     */
    public int getNumArguments(@Dimensionless Function<@Dimensionless R, @Dimensionless P> this) {
        return numArguments;
    }

    /**
     * Method that does the actual calculation of the function value given the arguments
     * 
     * @param args the set of arguments used for calculating the function
     * @return the result of the function evaluation
     */
    public abstract @Dimensionless R apply(@Dimensionless Function<@Dimensionless R, @Dimensionless P> this, P... args);

    /**
     * Get the set of characters which are allowed for use in Function names.
     * 
     * @return the set of characters allowed
     * @deprecated since 0.4.5 All unicode letters are allowed to be used in function names since 0.4.3. This API
     *             Function can be safely ignored. Checks for function name validity can be done using Character.isLetter() et al.
     */
    public static @Dimensionless char @Dimensionless [] getAllowedFunctionCharacters() {
        @Dimensionless
        char @Dimensionless [] chars = new @Dimensionless char @Dimensionless [((@Dimensionless int) (53))];
        @Dimensionless
        int count = ((@Dimensionless int) (0));
        for (@Dimensionless int i = ((@Dimensionless int) (65)); i < ((@Dimensionless int) (91)); i++) {
            chars[count++] = (@Dimensionless char) i;
        }
        for (@Dimensionless int i = ((@Dimensionless int) (97)); i < ((@Dimensionless int) (123)); i++) {
            chars[count++] = (@Dimensionless char) i;
        }
        chars[count] = '_';
        return chars;
    }

    public static @Dimensionless boolean isValidFunctionName(final @Dimensionless String name) {
        if (name == null) {
            return false;
        }

        final @Dimensionless int size = name.length();

        if (size == ((@Dimensionless int) (0))) {
            return false;
        }

        for (@Dimensionless int i = ((@Dimensionless int) (0)); i < size; i++) {
            final @Dimensionless char c = name.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            } else if (Character.isDigit(c) && i > ((@Dimensionless int) (0))) {
                continue;
            }
            return false;
        }
        return true;
    }
}
