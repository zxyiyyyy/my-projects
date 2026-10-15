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
import java.lang.classfile.Instruction;
import java.lang.classfile.TypeKind;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code newarray} invocation instruction in the {@code code}
 * array of a {@code Code} attribute.  Delivered as a {@link CodeElement}
 * when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface NewPrimitiveArrayInstruction extends Instruction
        permits AbstractInstruction.BoundNewPrimitiveArrayInstruction,
                AbstractInstruction.UnboundNewPrimitiveArrayInstruction {
    /**
     * {@return the component type of the array}
     */
    TypeKind typeKind();

    /**
     * {@return a new primitive array instruction}
     *
     * @param typeKind the component type of the array
     */
    static NewPrimitiveArrayInstruction of(TypeKind typeKind) {
        return new AbstractInstruction.UnboundNewPrimitiveArrayInstruction(typeKind);
    }
}
