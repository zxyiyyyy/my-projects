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
 * Models a local variable load instruction in the {@code code} array of a
 * {@code Code} attribute.  Corresponding opcodes will have a {@code kind} of
 * {@link Opcode.Kind#LOAD}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LoadInstruction extends Instruction
        permits AbstractInstruction.BoundLoadInstruction,
                AbstractInstruction.UnboundLoadInstruction {

    /**
     * {@return the local variable slot to load from}
     */
    int slot();

    /**
     * {@return the type of the value to be loaded}
     */
    TypeKind typeKind();

    /**
     * {@return a local variable load instruction}
     *
     * @param kind the type of the value to be loaded
     * @param slot the local variable slot to load from
     */
    static LoadInstruction of(TypeKind kind, int slot) {
        return of(BytecodeHelpers.loadOpcode(kind, slot), slot);
    }

    /**
     * {@return a local variable load instruction}
     *
     * @param op the opcode for the specific type of load instruction,
     *           which must be of kind {@link Opcode.Kind#LOAD}
     * @param slot the local variable slot to load from
     */
    static LoadInstruction of(Opcode op, int slot) {
        Util.checkKind(op, Opcode.Kind.LOAD);
        return new AbstractInstruction.UnboundLoadInstruction(op, slot);
    }
}
