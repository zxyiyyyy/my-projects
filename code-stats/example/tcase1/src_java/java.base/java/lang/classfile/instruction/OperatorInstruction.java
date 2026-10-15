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
 * Models an arithmetic operator instruction in the {@code code} array of a
 * {@code Code} attribute.  Corresponding opcodes will have a {@code kind} of
 * {@link Opcode.Kind#OPERATOR}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface OperatorInstruction extends Instruction
        permits AbstractInstruction.UnboundOperatorInstruction {
    /**
     * {@return the operand type of the instruction}
     */
    TypeKind typeKind();

    /**
     * {@return an operator instruction}
     *
     * @param op the opcode for the specific type of array load instruction,
     *           which must be of kind {@link Opcode.Kind#OPERATOR}
     */
    static OperatorInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.OPERATOR);
        return new AbstractInstruction.UnboundOperatorInstruction(op);
    }
}
