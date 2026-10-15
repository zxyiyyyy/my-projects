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
import jdk.internal.classfile.impl.BytecodeHelpers;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a return-from-method instruction in the {@code code} array of a
 * {@code Code} attribute.  Corresponding opcodes will have a {@code kind} of
 * {@link Opcode.Kind#RETURN}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ReturnInstruction extends Instruction
        permits AbstractInstruction.UnboundReturnInstruction {

    /**
     * {@return the type of the return instruction}
     */
    TypeKind typeKind();

    /**
     * {@return a return instruction}
     *
     * @param typeKind the type of the return instruction
     */
    static ReturnInstruction of(TypeKind typeKind) {
        return of(BytecodeHelpers.returnOpcode(typeKind));
    }

    /**
     * {@return a return instruction}
     *
     * @param op the opcode for the specific type of return instruction,
     *           which must be of kind {@link Opcode.Kind#RETURN}
     */
    static ReturnInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.RETURN);
        return new AbstractInstruction.UnboundReturnInstruction(op);
    }
}
