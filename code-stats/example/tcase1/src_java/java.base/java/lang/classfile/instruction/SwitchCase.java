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

import java.lang.classfile.Label;
import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a single case in a {@code lookupswitch} or {@code tableswitch}
 * instruction.
 *
 * @see LookupSwitchInstruction
 * @see TableSwitchInstruction
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface SwitchCase
        permits AbstractInstruction.SwitchCaseImpl {

    /** {@return the integer value corresponding to this case} */
    int caseValue();

    /** {@return the branch target corresponding to this case} */
    Label target();

    /**
     * Create a {@linkplain SwitchCase}
     *
     * @param caseValue the integer value for the case
     * @param target the branch target for the case
     * @return the {@linkplain SwitchCase}
     */
    static SwitchCase of(int caseValue, Label target) {
        return new AbstractInstruction.SwitchCaseImpl(caseValue, target);
    }
}
