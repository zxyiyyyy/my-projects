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
 * Models the {@code InnerClasses} attribute {@jvms 4.7.6}, which can
 * appear on classes, and records which classes referenced by this classfile
 * are inner classes. Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing the elements of a {@link java.lang.classfile.ClassModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface InnerClassesAttribute
        extends Attribute<InnerClassesAttribute>, ClassElement
        permits BoundAttribute.BoundInnerClassesAttribute,
                UnboundAttribute.UnboundInnerClassesAttribute {

    /**
     * {@return the inner classes used by this class}
     */
    List<InnerClassInfo> classes();

    /**
     * {@return an {@code InnerClasses} attribute}
     * @param innerClasses descriptions of the inner classes
     */
    static InnerClassesAttribute of(List<InnerClassInfo> innerClasses) {
        return new UnboundAttribute.UnboundInnerClassesAttribute(innerClasses);
    }

    /**
     * {@return an {@code InnerClasses} attribute}
     * @param innerClasses descriptions of the inner classes
     */
    static InnerClassesAttribute of(InnerClassInfo... innerClasses) {
        return new UnboundAttribute.UnboundInnerClassesAttribute(List.of(innerClasses));
    }
}
