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

/**
 * Abstract class for tokens used by exp4j to tokenize expressions
 */
public abstract class Token {
    public static final @Dimensionless short TOKEN_NUMBER = ((@Dimensionless int) (1));
    public static final short TOKEN_OPERATOR = ((@Dimensionless int) (2));
    public static final @Dimensionless short TOKEN_FUNCTION = ((@Dimensionless int) (3));
    public static final @Dimensionless short TOKEN_PARENTHESES_OPEN = ((@Dimensionless int) (4));
    public static final @Dimensionless short TOKEN_PARENTHESES_CLOSE = ((@Dimensionless int) (5));
    public static final @Dimensionless short TOKEN_VARIABLE = ((@Dimensionless int) (6));
    public static final @Dimensionless short TOKEN_SEPARATOR = ((@Dimensionless int) (7));

    protected final @Dimensionless int type;

    Token(int type) {
        this.type = type;
    }

    public @Dimensionless int getType(@Dimensionless Token this) {
        return type;
    }

}
