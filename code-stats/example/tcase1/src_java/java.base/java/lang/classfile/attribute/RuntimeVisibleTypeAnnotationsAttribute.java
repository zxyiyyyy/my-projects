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
import java.lang.classfile.CodeElement;
import java.lang.classfile.FieldElement;
import java.lang.classfile.MethodElement;
import java.lang.classfile.TypeAnnotation;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code RuntimeVisibleTypeAnnotations} attribute {@jvms 4.7.20}, which
 * can appear on classes, methods, fields, and code attributes. Delivered as a
 * {@link java.lang.classfile.ClassElement}, {@link java.lang.classfile.FieldElement},
 * {@link java.lang.classfile.MethodElement}, or {@link CodeElement} when traversing
 * the corresponding model type.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 8.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface RuntimeVisibleTypeAnnotationsAttribute
        extends Attribute<RuntimeVisibleTypeAnnotationsAttribute>,
                ClassElement, MethodElement, FieldElement, CodeElement
        permits BoundAttribute.BoundRuntimeVisibleTypeAnnotationsAttribute,
                UnboundAttribute.UnboundRuntimeVisibleTypeAnnotationsAttribute {

    /**
     * {@return the runtime-visible type annotations on parts of this class, field, or method}
     */
    List<TypeAnnotation> annotations();

    /**
     * {@return a {@code RuntimeVisibleTypeAnnotations} attribute}
     * @param annotations the annotations
     */
    static RuntimeVisibleTypeAnnotationsAttribute of(List<TypeAnnotation> annotations) {
        return new UnboundAttribute.UnboundRuntimeVisibleTypeAnnotationsAttribute(annotations);
    }

    /**
     * {@return a {@code RuntimeVisibleTypeAnnotations} attribute}
     * @param annotations the annotations
     */
    static RuntimeVisibleTypeAnnotationsAttribute of(TypeAnnotation... annotations) {
        return of(List.of(annotations));
    }
}
