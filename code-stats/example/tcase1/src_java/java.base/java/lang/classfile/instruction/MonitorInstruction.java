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
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code monitorenter} or {@code monitorexit} instruction in the
 * {@code code} array of a {@code Code} attribute.  Delivered as a {@link
 * CodeElement} when traversing the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface MonitorInstruction extends Instruction
        permits AbstractInstruction.UnboundMonitorInstruction {

    /**
     * {@return a monitor instruction}
     *
     * @param op the opcode for the specific type of monitor instruction,
     *           which must be of kind {@link Opcode.Kind#MONITOR}
     */
    static MonitorInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.MONITOR);
        return new AbstractInstruction.UnboundMonitorInstruction(op);
    }
}
