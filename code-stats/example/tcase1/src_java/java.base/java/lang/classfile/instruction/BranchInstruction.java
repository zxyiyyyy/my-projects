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
import java.lang.classfile.Label;
import java.lang.classfile.Opcode;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a branching instruction (conditional or unconditional) in the {@code
 * code} array of a {@code Code} attribute.  Corresponding opcodes will have a
 * {@code kind} of {@link Opcode.Kind#BRANCH}.  Delivered as a {@link
 * CodeElement} when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface BranchInstruction extends Instruction
        permits AbstractInstruction.BoundBranchInstruction,
                AbstractInstruction.UnboundBranchInstruction {
    /**
     * {@return the target of the branch}
     */
    Label target();

    /**
     * {@return a branch instruction}
     *
     * @param op the opcode for the specific type of branch instruction,
     *           which must be of kind {@link Opcode.Kind#BRANCH}
     * @param target the target of the branch
     */
    static BranchInstruction of(Opcode op, Label target) {
        Util.checkKind(op, Opcode.Kind.BRANCH);
        return new AbstractInstruction.UnboundBranchInstruction(op, target);
    }
}
