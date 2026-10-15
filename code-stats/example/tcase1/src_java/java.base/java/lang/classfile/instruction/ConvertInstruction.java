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
 * Models a primitive conversion instruction in the {@code code} array of a
 * {@code Code} attribute, such as {@code i2l}.  Corresponding opcodes will have
 * a {@code kind} of {@link Opcode.Kind#CONVERT}.  Delivered as a {@link
 * CodeElement} when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ConvertInstruction extends Instruction
        permits AbstractInstruction.UnboundConvertInstruction {
    /**
     * {@return the source type to convert from}
     */
    TypeKind fromType();

    /**
     * {@return the destination type to convert to}
     */
    TypeKind toType();

    /**
     * {@return A conversion instruction}
     *
     * @param fromType the type to convert from
     * @param toType the type to convert to
     */
    static ConvertInstruction of(TypeKind fromType, TypeKind toType) {
        return of(BytecodeHelpers.convertOpcode(fromType, toType));
    }

    /**
     * {@return a conversion instruction}
     *
     * @param op the opcode for the specific type of conversion instruction,
     *           which must be of kind {@link Opcode.Kind#CONVERT}
     */
    static ConvertInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.CONVERT);
        return new AbstractInstruction.UnboundConvertInstruction(op);
    }
}
