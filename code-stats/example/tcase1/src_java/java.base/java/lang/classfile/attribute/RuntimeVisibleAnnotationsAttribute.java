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

import java.lang.classfile.*;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;

import java.util.List;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code RuntimeVisibleAnnotations} attribute {@jvms 4.7.16}, which
 * can appear on classes, methods, and fields. Delivered as a
 * {@link java.lang.classfile.ClassElement}, {@link java.lang.classfile.FieldElement}, or
 * {@link java.lang.classfile.MethodElement} when traversing the corresponding model type.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 5.0.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface RuntimeVisibleAnnotationsAttribute
        extends Attribute<RuntimeVisibleAnnotationsAttribute>,
                ClassElement, MethodElement, FieldElement
        permits BoundAttribute.BoundRuntimeVisibleAnnotationsAttribute,
                UnboundAttribute.UnboundRuntimeVisibleAnnotationsAttribute {

    /**
     * {@return the runtime-visible annotations on this class, field, or method}
     */
    List<Annotation> annotations();

    /**
     * {@return a {@code RuntimeVisibleAnnotations} attribute}
     * @param annotations the annotations
     */
    static RuntimeVisibleAnnotationsAttribute of(List<Annotation> annotations) {
        return new UnboundAttribute.UnboundRuntimeVisibleAnnotationsAttribute(annotations);
    }

    /**
     * {@return a {@code RuntimeVisibleAnnotations} attribute}
     * @param annotations the annotations
     */
    static RuntimeVisibleAnnotationsAttribute of(Annotation... annotations) {
        return of(List.of(annotations));
    }
}
