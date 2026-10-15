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

import java.lang.constant.ClassDesc;

import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.Instruction;
import java.lang.classfile.Opcode;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models an {@code instanceof} or {@code checkcast} instruction in the {@code
 * code} array of a {@code Code} attribute.  Delivered as a {@link CodeElement}
 * when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface TypeCheckInstruction extends Instruction
        permits AbstractInstruction.BoundTypeCheckInstruction,
                AbstractInstruction.UnboundTypeCheckInstruction {

    /**
     * {@return the type against which the instruction checks or casts}
     */
    ClassEntry type();

    /**
     * {@return a type check instruction}
     *
     * @param op the opcode for the specific type of type check instruction,
     *           which must be of kind {@link Opcode.Kind#TYPE_CHECK}
     * @param type the type against which to check or cast
     */
    static TypeCheckInstruction of(Opcode op, ClassEntry type) {
        Util.checkKind(op, Opcode.Kind.TYPE_CHECK);
        return new AbstractInstruction.UnboundTypeCheckInstruction(op, type);
    }

    /**
     * {@return a type check instruction}
     *
     * @param op the opcode for the specific type of type check instruction,
     *           which must be of kind {@link Opcode.Kind#TYPE_CHECK}
     * @param type the type against which to check or cast
     */
    static TypeCheckInstruction of(Opcode op, ClassDesc type) {
        return of(op, TemporaryConstantPool.INSTANCE.classEntry(type));
    }
}
