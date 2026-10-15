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
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a local variable increment instruction in the {@code code} array of a
 * {@code Code} attribute.  Corresponding opcodes will have a {@code kind} of
 * {@link Opcode.Kind#INCREMENT}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface IncrementInstruction extends Instruction
        permits AbstractInstruction.BoundIncrementInstruction,
                AbstractInstruction.UnboundIncrementInstruction {
    /**
     * {@return the local variable slot to increment}
     */
    int slot();

    /**
     * {@return the value to increment by}
     */
    int constant();

    /**
     * {@return an increment instruction}
     *
     * @param slot the local variable slot to increment
     * @param constant the value to increment by
     */
    static IncrementInstruction of(int slot, int constant) {
        return new AbstractInstruction.UnboundIncrementInstruction(slot, constant);
    }
}
