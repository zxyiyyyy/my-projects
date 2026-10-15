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
package java.lang.classfile.instruction;

import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.Instruction;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code multianewarray} invocation instruction in the {@code code}
 * array of a {@code Code} attribute.  Delivered as a {@link CodeElement}
 * when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface NewMultiArrayInstruction extends Instruction
        permits AbstractInstruction.BoundNewMultidimensionalArrayInstruction,
                AbstractInstruction.UnboundNewMultidimensionalArrayInstruction {

    /**
     * {@return the type of the array, as a symbolic descriptor}
     */
    ClassEntry arrayType();

    /**
     * {@return the number of dimensions of the array}
     */
    int dimensions();

    /**
     * {@return a new multi-dimensional array instruction}
     *
     * @param arrayTypeEntry the type of the array
     * @param dimensions the number of dimensions of the array
     */
    static NewMultiArrayInstruction of(ClassEntry arrayTypeEntry,
                                       int dimensions) {
        return new AbstractInstruction.UnboundNewMultidimensionalArrayInstruction(arrayTypeEntry, dimensions);
    }
}
