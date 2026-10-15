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

import java.lang.classfile.Annotation;
import java.lang.classfile.Attribute;
import java.lang.classfile.MethodElement;
import java.lang.classfile.MethodModel;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code RuntimeInvisibleParameterAnnotations} attribute
 * {@jvms 4.7.19}, which can appear on methods. Delivered as a {@link
 * java.lang.classfile.MethodElement} when traversing a {@link MethodModel}.
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
public sealed interface RuntimeInvisibleParameterAnnotationsAttribute
        extends Attribute<RuntimeInvisibleParameterAnnotationsAttribute>, MethodElement
        permits BoundAttribute.BoundRuntimeInvisibleParameterAnnotationsAttribute,
                UnboundAttribute.UnboundRuntimeInvisibleParameterAnnotationsAttribute {

    /**
     * {@return the list of annotations corresponding to each method parameter}
     * The element at the i'th index corresponds to the annotations on the i'th
     * parameter.
     */
    List<List<Annotation>> parameterAnnotations();

    /**
     * {@return a {@code RuntimeInvisibleParameterAnnotations} attribute}
     * @param parameterAnnotations a list of parameter annotations for each parameter
     */
    static RuntimeInvisibleParameterAnnotationsAttribute of(List<List<Annotation>> parameterAnnotations) {
        return new UnboundAttribute.UnboundRuntimeInvisibleParameterAnnotationsAttribute(parameterAnnotations);
    }
}
