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
import java.lang.classfile.MethodElement;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code MethodParameters} attribute {@jvms 4.7.24}, which can
 * appear on methods, and records optional information about the method's
 * parameters.  Delivered as a {@link java.lang.classfile.MethodElement} when
 * traversing the elements of a {@link java.lang.classfile.MethodModel}.
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
public sealed interface MethodParametersAttribute
        extends Attribute<MethodParametersAttribute>, MethodElement
        permits BoundAttribute.BoundMethodParametersAttribute,
                UnboundAttribute.UnboundMethodParametersAttribute {

    /**
     * {@return information about the parameters of the method}  The i'th entry
     * in the list corresponds to the i'th parameter in the method declaration.
     */
    List<MethodParameterInfo> parameters();

    /**
     * {@return a {@code MethodParameters} attribute}
     * @param parameters the method parameter descriptions
     */
    static MethodParametersAttribute of(List<MethodParameterInfo> parameters) {
        return new UnboundAttribute.UnboundMethodParametersAttribute(parameters);
    }

    /**
     * {@return a {@code MethodParameters} attribute}
     * @param parameters the method parameter descriptions
     */
    static MethodParametersAttribute of(MethodParameterInfo... parameters) {
        return of(List.of(parameters));
    }
}
