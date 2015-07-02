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
package de.cubeisland.engine.messagecompositor.parser.formatter.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import de.cubeisland.engine.messagecompositor.macro.MacroContext;
import de.cubeisland.engine.messagecompositor.parser.component.MessageComponent;
import de.cubeisland.engine.messagecompositor.parser.component.Text;
import de.cubeisland.engine.messagecompositor.parser.formatter.AbstractFormatter;
import de.cubeisland.engine.messagecompositor.parser.formatter.Context;

import static java.text.DateFormat.*;

public class DateFormatter extends AbstractFormatter<Date>
{
    public DateFormatter()
    {
        super(Date.class, "date");
    }

    public String process(Date object, MacroContext context)
    {
        SimpleDateFormat sdf = context.readMapped("format", SimpleDateFormat.class);
        if (sdf == null)
        {
            DateFormat instance = getDateTimeInstance(SHORT, SHORT, context.getLocale());
            if ("notime".equalsIgnoreCase(context.getArg(0)))
            {
                instance = getDateInstance(SHORT, context.getLocale());
            }
            return instance.format(object);
        }
        return sdf.format(object);
    }


    @Override
    protected MessageComponent format(Date arg, Context c)
    {
        String formatString = c.get("format");
        boolean notime = c.has("notime");
        DateFormat format = null;
        if (formatString != null)
        {
            format = new SimpleDateFormat(formatString);
        }
        if (format == null)
        {
            format = notime ? getDateInstance(SHORT, c.getLocale()) : getDateTimeInstance(SHORT, SHORT, c.getLocale());
        }
        return new Text(format.format(arg));
    }
}
