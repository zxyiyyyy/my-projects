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

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeModel;
import java.util.Optional;
import java.util.function.Consumer;
import java.lang.classfile.CodeElement;
import java.lang.classfile.Label;
import java.lang.classfile.TypeKind;
import java.lang.classfile.constantpool.ConstantPoolBuilder;

public final class TransformingCodeBuilder implements TerminalCodeBuilder {

    final CodeBuilder delegate;
    final Consumer<CodeElement> consumer;

    public TransformingCodeBuilder(CodeBuilder delegate, Consumer<CodeElement> consumer) {
        this.delegate = delegate;
        this.consumer = consumer;
    }

    @Override
    public CodeBuilder with(CodeElement e) {
        consumer.accept(e);
        return this;
    }

    @Override
    public Optional<CodeModel> original() {
        return delegate.original();
    }

    @Override
    public Label newLabel() {
        return delegate.newLabel();
    }

    @Override
    public Label startLabel() {
        return delegate.startLabel();
    }

    @Override
    public Label endLabel() {
        return delegate.endLabel();
    }

    @Override
    public int receiverSlot() {
        return delegate.receiverSlot();
    }

    @Override
    public int parameterSlot(int paramNo) {
        return delegate.parameterSlot(paramNo);
    }

    @Override
    public int allocateLocal(TypeKind typeKind) {
        return delegate.allocateLocal(typeKind);
    }

    @Override
    public ConstantPoolBuilder constantPool() {
        return delegate.constantPool();
    }
}
