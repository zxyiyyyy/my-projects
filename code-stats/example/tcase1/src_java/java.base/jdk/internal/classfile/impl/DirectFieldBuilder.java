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

import java.util.function.Consumer;

import java.lang.classfile.BufWriter;
import java.lang.classfile.CustomAttribute;
import java.lang.classfile.FieldBuilder;
import java.lang.classfile.FieldElement;
import java.lang.classfile.FieldModel;
import java.lang.classfile.WritableElement;
import java.lang.classfile.constantpool.Utf8Entry;

public final class DirectFieldBuilder
        extends AbstractDirectBuilder<FieldModel>
        implements TerminalFieldBuilder, WritableElement<FieldModel> {
    private final Utf8Entry name;
    private final Utf8Entry desc;
    private int flags;

    public DirectFieldBuilder(SplitConstantPool constantPool,
                              ClassFileImpl context,
                              Utf8Entry name,
                              Utf8Entry type,
                              FieldModel original) {
        super(constantPool, context);
        setOriginal(original);
        this.name = name;
        this.desc = type;
        this.flags = 0;
    }

    @Override
    public FieldBuilder with(FieldElement element) {
        if (element instanceof AbstractElement ae) {
            ae.writeTo(this);
        } else {
            writeAttribute((CustomAttribute)element);
        }
        return this;
    }

    public DirectFieldBuilder run(Consumer<? super FieldBuilder> handler) {
        handler.accept(this);
        return this;
    }

    void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public void writeTo(BufWriter buf) {
        buf.writeU2(flags);
        buf.writeIndex(name);
        buf.writeIndex(desc);
        attributes.writeTo(buf);
    }
}
