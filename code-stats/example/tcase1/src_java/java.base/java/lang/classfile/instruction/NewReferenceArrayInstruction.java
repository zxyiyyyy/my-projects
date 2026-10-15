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
 * Models a {@code anewarray} invocation instruction in the {@code code}
 * array of a {@code Code} attribute.  Delivered as a {@link CodeElement}
 * when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface NewReferenceArrayInstruction extends Instruction
        permits AbstractInstruction.BoundNewReferenceArrayInstruction, AbstractInstruction.UnboundNewReferenceArrayInstruction {
    /**
     * {@return the component type of the array}
     */
    ClassEntry componentType();

    /**
     * {@return a new reference array instruction}
     *
     * @param componentType the component type of the array
     */
    static NewReferenceArrayInstruction of(ClassEntry componentType) {
        return new AbstractInstruction.UnboundNewReferenceArrayInstruction(componentType);
    }
}
