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
import java.lang.classfile.Label;
import java.lang.classfile.PseudoInstruction;
import jdk.internal.classfile.impl.LabelImpl;
import jdk.internal.javac.PreviewFeature;

/**
 * A pseudo-instruction which indicates that the specified label corresponds to
 * the current position in the {@code Code} attribute.  Delivered as a {@link
 * CodeElement} during traversal of the elements of a {@link CodeModel}.
 *
 * @see PseudoInstruction
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LabelTarget extends PseudoInstruction
        permits LabelImpl {

    /**
     * {@return the label corresponding to this target}
     */
    Label label();
}
