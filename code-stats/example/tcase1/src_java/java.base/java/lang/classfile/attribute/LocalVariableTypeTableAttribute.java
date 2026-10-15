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
 * Models the {@code LocalVariableTypeTable} attribute {@jvms 4.7.14}, which can appear
 * on a {@code Code} attribute, and records debug information about local
 * variables.
 * Delivered as a {@link java.lang.classfile.instruction.LocalVariable} when traversing the
 * elements of a {@link java.lang.classfile.CodeModel}, according to the setting of the
 * {@link java.lang.classfile.ClassFile.LineNumbersOption} option.
 * <p>
 * The attribute permits multiple instances in a given location.
 * <p>
 * The attribute was introduced in the Java SE Platform version 5.0.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LocalVariableTypeTableAttribute
        extends Attribute<LocalVariableTypeTableAttribute>
        permits BoundAttribute.BoundLocalVariableTypeTableAttribute, UnboundAttribute.UnboundLocalVariableTypeTableAttribute {

    /**
     * {@return debug information for the local variables in this method}
     */
    List<LocalVariableTypeInfo> localVariableTypes();

    /**
     * {@return a {@code LocalVariableTypeTable} attribute}
     * @param locals the local variable descriptions
     */
    static LocalVariableTypeTableAttribute of(List<LocalVariableTypeInfo> locals) {
        return new UnboundAttribute.UnboundLocalVariableTypeTableAttribute(locals);
    }
}
