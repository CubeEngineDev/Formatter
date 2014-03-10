/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Anselm Brehme, Phillip Schichtel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.cubeisland.engine.messagecompositor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.cubeisland.engine.messagecompositor.macro.Macro;

/**
 * Contextual Information for a Macro
 */
public class MacroContext
{
    private final MessageCompositor compositor;
    private final Macro macro;
    private final Locale locale;
    private final String type;

    private Map<String, String> mappedArguments = new HashMap<String, String>();
    private List<String> arguments = new ArrayList<String>();

    public static final char MAP = '=';
    public static final char ESCAPE = '\\';

    MacroContext(MessageCompositor compositor, Macro macro, String type, Locale locale, List<String> typeArguments)
    {
        this.compositor = compositor;
        this.macro = macro;
        this.locale = locale;
        this.type = type;

        if (typeArguments != null)
        {
            for (String flag : typeArguments)
            {
                String readFlag = "";
                String key = null;
                boolean escape = false;
                char[] chars = flag.toCharArray();
                for (char curChar : chars)
                {
                    switch (curChar)
                    {
                    case ESCAPE:
                        if (escape)
                        {
                            readFlag += curChar;
                            escape = false;
                        }
                        else
                        {
                            escape = true;
                        }
                        break;
                    case MAP:
                        if (escape)
                        {
                            readFlag += curChar;
                            escape = false;
                        }
                        else if (key == null)
                        {
                            key = readFlag;
                            readFlag = "";
                        }
                        break;
                    default:
                        readFlag += curChar;
                        break;
                    }
                }
                if (key == null)
                {
                    this.arguments.add(readFlag);
                }
                else
                {
                    this.mappedArguments.put(key, readFlag);
                }
            }
        }
    }

    /**
     * Gets the Macro this context was created for
     *
     * @return the macro
     */
    public final Macro getMacro()
    {
        return macro;
    }

    /**
     * Gets the locale to use
     *
     * @return the locale to use
     */
    public final Locale getLocale()
    {
        return locale;
    }

    /**
     * Gets an indexed argument
     *
     * @param i the index
     * @return the value or null
     */
    public final String getArg(int i)
    {
        if (this.arguments.size() > i)
        {
            return this.arguments.get(i);
        }
        return null;
    }

    /**
     * Attempts to read an indexed argument
     *
     * @param i the index
     * @param clazz the class to cast into
     * @return the value or null
     */
    public final <T> T readArg(int i, Class<T> clazz)
    {
        String value = this.getArg(i);
        if (value == null)
        {
            return null;
        }
        return this.compositor.read(this.macro, value, clazz);
    }

    /**
     * Gets a mapped argument for given key
     *
     * @param key the key
     * @return the value or null
     */
    public final String getMapped(String key)
    {
        return this.mappedArguments.get(key);
    }

    /**
     * Attempts to read a mapped argument for given key
     *
     * @param key the key
     * @param clazz the class to cast into
     * @return the value or null
     */
    public final <T> T readMapped(String key, Class<T> clazz)
    {
        String value = this.getMapped(key);
        if (value == null)
        {
            return null;
        }
        T read = this.compositor.read(this.macro, key, value, clazz);
        if (read == null)
        {
            return this.compositor.read(key, value, clazz);
        }
        return read;
    }

    /**
     * Gets the name of the macro this context got created for
     *
     * @return the macros name
     */
    public final String getType()
    {
        return type;
    }
}