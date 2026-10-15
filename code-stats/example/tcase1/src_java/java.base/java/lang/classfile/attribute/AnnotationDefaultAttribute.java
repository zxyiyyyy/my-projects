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

import java.lang.classfile.AnnotationValue;
import java.lang.classfile.Attribute;
import java.lang.classfile.MethodElement;
import java.lang.classfile.MethodModel;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code AnnotationDefault} attribute {@jvms 4.7.22}, which can
 * appear on methods of annotation types, and records the default value
 * {@jls 9.6.2} for the element corresponding to this method.  Delivered as a
 * {@link MethodElement} when traversing the elements of a {@link MethodModel}.
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
public sealed interface AnnotationDefaultAttribute
        extends Attribute<AnnotationDefaultAttribute>, MethodElement
        permits BoundAttribute.BoundAnnotationDefaultAttr,
                UnboundAttribute.UnboundAnnotationDefaultAttribute {

    /**
     * {@return the default value of the annotation type element represented by
     * this method}
     */
    AnnotationValue defaultValue();

    /**
     * {@return an {@code AnnotationDefault} attribute}
     * @param annotationDefault the default value of the annotation type element
     */
    static AnnotationDefaultAttribute of(AnnotationValue annotationDefault) {
        return new UnboundAttribute.UnboundAnnotationDefaultAttribute(annotationDefault);
    }

}
