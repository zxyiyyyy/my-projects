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
import java.lang.classfile.ClassElement;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code Record} attribute {@jvms 4.7.30}, which can
 * appear on classes to indicate that this class is a record class.
 * Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing the elements of a {@link java.lang.classfile.ClassModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 16.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface RecordAttribute extends Attribute<RecordAttribute>, ClassElement
        permits BoundAttribute.BoundRecordAttribute, UnboundAttribute.UnboundRecordAttribute {

    /**
     * {@return the components of this record class}
     */
    List<RecordComponentInfo> components();

    /**
     * {@return a {@code Record} attribute}
     * @param components the record components
     */
    static RecordAttribute of(List<RecordComponentInfo> components) {
        return new UnboundAttribute.UnboundRecordAttribute(components);
    }

    /**
     * {@return a {@code Record} attribute}
     * @param components the record components
     */
    static RecordAttribute of(RecordComponentInfo... components) {
        return of(List.of(components));
    }
}
