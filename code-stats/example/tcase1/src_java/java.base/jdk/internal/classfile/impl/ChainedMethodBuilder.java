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
import java.util.function.Consumer;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeModel;
import java.lang.classfile.CodeTransform;
import java.lang.classfile.MethodBuilder;
import java.lang.classfile.MethodElement;
import java.lang.classfile.MethodModel;
import java.lang.classfile.constantpool.ConstantPoolBuilder;

public final class ChainedMethodBuilder implements MethodBuilder {
    final MethodBuilder downstream;
    final TerminalMethodBuilder terminal;
    final Consumer<MethodElement> consumer;

    public ChainedMethodBuilder(MethodBuilder downstream,
                                Consumer<MethodElement> consumer) {
        this.downstream = downstream;
        this.consumer = consumer;
        this.terminal = switch (downstream) {
            case ChainedMethodBuilder cb -> cb.terminal;
            case TerminalMethodBuilder tb -> tb;
        };
    }

    @Override
    public MethodBuilder with(MethodElement element) {
        consumer.accept(element);
        return this;
    }

    @Override
    public MethodBuilder withCode(Consumer<? super CodeBuilder> handler) {
        return downstream.with(terminal.bufferedCodeBuilder(null)
                                       .run(handler)
                                       .toModel());
    }

    @Override
    public MethodBuilder transformCode(CodeModel code, CodeTransform transform) {
        BufferedCodeBuilder builder = terminal.bufferedCodeBuilder(code);
        builder.transform(code, transform);
        return downstream.with(builder.toModel());
    }

    @Override
    public ConstantPoolBuilder constantPool() {
        return terminal.constantPool();
    }

    @Override
    public Optional<MethodModel> original() {
        return terminal.original();
    }

}
