/*
 * Copyright (c) 2022, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.BufWriter;

public abstract class AbstractAttributeMapper<T extends Attribute<T>>
        implements AttributeMapper<T> {

    private final String name;
    private final boolean allowMultiple;

    protected abstract void writeBody(BufWriter buf, T attr);

    public AbstractAttributeMapper(String name) {
        this(name, false);
    }

    public AbstractAttributeMapper(String name,
                                   boolean allowMultiple) {
        this.name = name;
        this.allowMultiple = allowMultiple;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void writeAttribute(BufWriter buf, T attr) {
        buf.writeIndex(buf.constantPool().utf8Entry(name));
        buf.writeInt(0);
        int start = buf.size();
        writeBody(buf, attr);
        int written = buf.size() - start;
        buf.patchInt(start - 4, 4, written);
    }

    @Override
    public boolean allowMultiple() {
        return allowMultiple;
    }

    @Override
    public String toString() {
        return String.format("AttributeMapper[name=%s, allowMultiple=%b, stability=%s]",
                name, allowMultiple, stability());
    }
}
