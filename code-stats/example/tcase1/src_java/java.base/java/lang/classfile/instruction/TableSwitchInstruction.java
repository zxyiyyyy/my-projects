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

import java.util.List;

import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Instruction;
import java.lang.classfile.Label;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code tableswitch} instruction in the {@code code} array of a
 * {@code Code} attribute.  Delivered as a {@link CodeElement} when traversing
 * the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface TableSwitchInstruction extends Instruction
        permits AbstractInstruction.BoundTableSwitchInstruction, AbstractInstruction.UnboundTableSwitchInstruction {
    /**
     * {@return the low value of the switch target range, inclusive}
     */
    int lowValue();

    /**
     * {@return the high value of the switch target range, inclusive}
     */
    int highValue();

    /**
     * {@return the default target of the switch}
     */
    Label defaultTarget();

    /**
     * {@return the cases of the switch}
     */
    List<SwitchCase> cases();

    /**
     * {@return a table switch instruction}
     *
     * @param lowValue the low value of the switch target range, inclusive
     * @param highValue the high value of the switch target range, inclusive
     * @param defaultTarget the default target of the switch
     * @param cases the cases of the switch
     */
    static TableSwitchInstruction of(int lowValue, int highValue, Label defaultTarget, List<SwitchCase> cases) {
        return new AbstractInstruction.UnboundTableSwitchInstruction(lowValue, highValue, defaultTarget, cases);
    }
}
