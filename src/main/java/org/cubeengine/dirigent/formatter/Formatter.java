/**
 * The MIT License
 * Copyright (c) 2013 Cube Island
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
package org.cubeengine.dirigent.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.cubeengine.dirigent.Component;

public abstract class Formatter<T>
{
    private List<PostProcessor> postProcessors = new ArrayList<PostProcessor>();

    public abstract boolean isApplicable(Object arg);

    protected abstract Component format(T arg, Context context);

    public final Component process(T arg, Context context)
    {
        Component result = format(arg, context);
        for (PostProcessor processor : postProcessors)
        {
            result = processor.process(result, context);
        }
        return result;
    }

    public final void addPostProcessor(PostProcessor pp)
    {
        postProcessors.add(pp);
    }

    public abstract Set<String> names();
}