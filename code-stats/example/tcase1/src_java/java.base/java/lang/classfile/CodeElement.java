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
package java.lang.classfile;

import java.lang.classfile.attribute.RuntimeInvisibleTypeAnnotationsAttribute;
import java.lang.classfile.attribute.RuntimeVisibleTypeAnnotationsAttribute;
import java.lang.classfile.attribute.StackMapTableAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * A marker interface for elements that can appear when traversing
 * a {@link CodeModel} or be presented to a {@link CodeBuilder}. Code elements
 * are either an {@link Instruction}, which models an instruction in the body
 * of a method, or a {@link PseudoInstruction}, which models metadata from
 * the code attribute, such as line number metadata, local variable metadata,
 * exception metadata, label target metadata, etc.
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface CodeElement extends ClassFileElement
        permits Instruction, PseudoInstruction,
                CustomAttribute, RuntimeVisibleTypeAnnotationsAttribute, RuntimeInvisibleTypeAnnotationsAttribute,
                StackMapTableAttribute {
}
