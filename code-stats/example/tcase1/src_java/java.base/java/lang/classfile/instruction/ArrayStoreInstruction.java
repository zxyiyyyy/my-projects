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
import java.lang.classfile.Opcode;
import java.lang.classfile.TypeKind;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models an array store instruction in the {@code code} array of a {@code Code}
 * attribute.  Corresponding opcodes will have a {@code kind} of {@link
 * Opcode.Kind#ARRAY_STORE}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ArrayStoreInstruction extends Instruction
        permits AbstractInstruction.UnboundArrayStoreInstruction {
    /**
     * {@return the component type of the array}
     */
    TypeKind typeKind();

    /**
     * {@return an array store instruction}
     *
     * @param op the opcode for the specific type of array store instruction,
     *           which must be of kind {@link Opcode.Kind#ARRAY_STORE}
     */
    static ArrayStoreInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.ARRAY_STORE);
        return new AbstractInstruction.UnboundArrayStoreInstruction(op);
    }
}
