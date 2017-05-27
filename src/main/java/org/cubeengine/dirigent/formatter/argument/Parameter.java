/*
 * The MIT License
 * Copyright © 2013 Cube Island
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cubeengine.dirigent.formatter.argument;

/**
 * A name-value argument
 */
public class Parameter implements Argument
{
    private final String name;
    private final String value;

    public Parameter(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Retrieves the name of this pair.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Retrieves the value of this pair.
     *
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Parameter))
        {
            return false;
        }

        final Parameter parameter = (Parameter)o;

        if (!getName().equals(parameter.getName()))
        {
            return false;
        }
        return getValue().equals(parameter.getValue());
    }

    @Override
    public int hashCode()
    {
        int result = getName().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Parameter{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
    }
}