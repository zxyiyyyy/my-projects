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
package java.lang.classfile.constantpool;

import java.lang.constant.ConstantDesc;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a constant pool entry that can be used as the constant in a
 * {@code ConstantValue} attribute; this includes the four primitive constant
 * types and {@linkplain String} constants.
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ConstantValueEntry extends LoadableConstantEntry
        permits DoubleEntry, FloatEntry, IntegerEntry, LongEntry, StringEntry {

    /**
     * {@return the constant value}  The constant value will be an {@link Integer},
     * {@link Long}, {@link Float}, {@link Double}, or {@link String}.
     */
    @Override
    ConstantDesc constantValue();
}
