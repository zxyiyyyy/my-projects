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
 * Models a {@code lookupswitch} instruction in the {@code code} array of a
 * {@code Code} attribute.  Delivered as a {@link CodeElement} when traversing
 * the elements of a {@link CodeModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LookupSwitchInstruction extends Instruction
        permits AbstractInstruction.BoundLookupSwitchInstruction,
                AbstractInstruction.UnboundLookupSwitchInstruction {
    /**
     * {@return the target of the default case}
     */
    Label defaultTarget();

    /**
     * {@return the cases of the switch}
     */
    List<SwitchCase> cases();

    /**
     * {@return a lookup switch instruction}
     *
     * @param defaultTarget the default target of the switch
     * @param cases the cases of the switch
     */
    static LookupSwitchInstruction of(Label defaultTarget, List<SwitchCase> cases) {
        return new AbstractInstruction.UnboundLookupSwitchInstruction(defaultTarget, cases);
    }
}
