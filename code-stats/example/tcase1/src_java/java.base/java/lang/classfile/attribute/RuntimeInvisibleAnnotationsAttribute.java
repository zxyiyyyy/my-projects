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
 * Models the {@code RuntimeInvisibleAnnotations} attribute {@jvms 4.7.17}, which
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
public sealed interface RuntimeInvisibleAnnotationsAttribute
        extends Attribute<RuntimeInvisibleAnnotationsAttribute>,
                ClassElement, MethodElement, FieldElement
        permits BoundAttribute.BoundRuntimeInvisibleAnnotationsAttribute,
                UnboundAttribute.UnboundRuntimeInvisibleAnnotationsAttribute {

    /**
     * {@return the non-runtime-visible annotations on this class, field, or method}
     */
    List<Annotation> annotations();

    /**
     * {@return a {@code RuntimeInvisibleAnnotations} attribute}
     * @param annotations the annotations
     */
    static RuntimeInvisibleAnnotationsAttribute of(List<Annotation> annotations) {
        return new UnboundAttribute.UnboundRuntimeInvisibleAnnotationsAttribute(annotations);
    }

    /**
     * {@return a {@code RuntimeInvisibleAnnotations} attribute}
     * @param annotations the annotations
     */
    static RuntimeInvisibleAnnotationsAttribute of(Annotation... annotations) {
        return of(List.of(annotations));
    }
}
