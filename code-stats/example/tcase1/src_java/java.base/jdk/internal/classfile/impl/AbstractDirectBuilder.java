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

import java.util.Optional;

import java.lang.classfile.Attribute;

public class AbstractDirectBuilder<M> {
    protected final SplitConstantPool constantPool;
    protected final ClassFileImpl context;
    protected final AttributeHolder attributes = new AttributeHolder();
    protected M original;

    public AbstractDirectBuilder(SplitConstantPool constantPool, ClassFileImpl context) {
        this.constantPool = constantPool;
        this.context = context;
    }

    public SplitConstantPool constantPool() {
        return constantPool;
    }

    public Optional<M> original() {
        return Optional.ofNullable(original);
    }

    public void setOriginal(M original) {
        this.original = original;
    }

    public void writeAttribute(Attribute<?> a) {
        if (Util.isAttributeAllowed(a, context.attributesProcessingOption())) {
            attributes.withAttribute(a);
        }
    }
}
