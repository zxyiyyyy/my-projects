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
package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;

import java.util.List;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code LocalVariableTable} attribute {@jvms 4.7.13}, which can appear
 * on a {@code Code} attribute, and records debug information about local
 * variables.
 * Delivered as a {@link java.lang.classfile.instruction.LocalVariable} when traversing the
 * elements of a {@link java.lang.classfile.CodeModel}, according to the setting of the
 * {@link java.lang.classfile.ClassFile.DebugElementsOption} option.
 * <p>
 * The attribute permits multiple instances in a given location.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LocalVariableTableAttribute
        extends Attribute<LocalVariableTableAttribute>
        permits BoundAttribute.BoundLocalVariableTableAttribute, UnboundAttribute.UnboundLocalVariableTableAttribute {

    /**
     * {@return debug information for the local variables in this method}
     */
    List<LocalVariableInfo> localVariables();

    /**
     * {@return a {@code LocalVariableTable} attribute}
     * @param locals the local variable descriptions
     */
    static LocalVariableTableAttribute of(List<LocalVariableInfo> locals) {
        return new UnboundAttribute.UnboundLocalVariableTableAttribute(locals);
    }
}
