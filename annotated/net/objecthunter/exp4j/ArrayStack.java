/*
 * Copyright 2015 Federico Vera
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
import java.util.EmptyStackException;

/**
 * Simple double stack using a double array as data storage
 *
 * @author Federico Vera (dktcoding [at] gmail)
 */
class ArrayStack {

    private @Dimensionless double @Dimensionless [] data;

    private @Dimensionless int idx;

    ArrayStack() {
        this(((@Dimensionless int) (5)));
    }

    ArrayStack(@Dimensionless int initialCapacity) {
        if (initialCapacity <= ((@Dimensionless int) (0))) {
            throw new @Dimensionless IllegalArgumentException(
                    "Stack's capacity must be positive");
        }

        data = new @Dimensionless double @Dimensionless [initialCapacity];
        idx = ((@Dimensionless int) (-1));
    }

    void push(@Dimensionless ArrayStack this, double value) {
        if (idx + ((@Dimensionless int) (1)) == data.length) {
            @Dimensionless
            double @Dimensionless [] temp = new @Dimensionless double @Dimensionless [(@Dimensionless int) (data.length * ((@Dimensionless double) (1.2))) + ((@Dimensionless int) (1))];
            System.arraycopy(data, ((@Dimensionless int) (0)), temp, ((@Dimensionless int) (0)), data.length);
            data = temp;
        }

        data[++idx] = value;
    }

    @Dimensionless
    double peek(@Dimensionless ArrayStack this) {
        if (idx == ((@Dimensionless int) (-1))) {
            throw new @Dimensionless EmptyStackException();
        }
        return data[idx];
    }

    double pop(@Dimensionless ArrayStack this) {
        if (idx == ((@Dimensionless int) (-1))) {
            throw new @Dimensionless EmptyStackException();
        }
        return data[idx--];
    }

    @Dimensionless
    boolean isEmpty(@Dimensionless ArrayStack this) {
        return idx == ((@Dimensionless int) (-1));
    }

    int size(@Dimensionless ArrayStack this) {
        return idx + ((@Dimensionless int) (1));
    }
}
