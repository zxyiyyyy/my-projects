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

import java.util.List;

import java.lang.classfile.Attribute;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code LineNumberTable} attribute {@jvms 4.7.12}, which can appear
 * on a {@code Code} attribute, and records the mapping between indexes into
 * the code table and line numbers in the source file.
 * Delivered as a {@link java.lang.classfile.instruction.LineNumber} when traversing the
 * elements of a {@link java.lang.classfile.CodeModel}, according to the setting of the
 * {@link java.lang.classfile.ClassFile.LineNumbersOption} option.
 * <p>
 * The attribute permits multiple instances in a given location.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LineNumberTableAttribute
        extends Attribute<LineNumberTableAttribute>
        permits BoundAttribute.BoundLineNumberTableAttribute,
                UnboundAttribute.UnboundLineNumberTableAttribute {

    /**
     * {@return the table mapping bytecode offsets to source line numbers}
     */
    List<LineNumberInfo> lineNumbers();

    /**
     * {@return a {@code LineNumberTable} attribute}
     * @param lines the line number descriptions
     */
    static LineNumberTableAttribute of(List<LineNumberInfo> lines) {
        return new UnboundAttribute.UnboundLineNumberTableAttribute(lines);
    }
}
