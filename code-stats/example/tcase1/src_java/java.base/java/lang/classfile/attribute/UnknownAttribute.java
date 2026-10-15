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
import java.lang.classfile.ClassElement;
import java.lang.classfile.FieldElement;
import java.lang.classfile.MethodElement;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models an unknown attribute on a class, method, or field.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface UnknownAttribute
        extends Attribute<UnknownAttribute>,
                ClassElement, MethodElement, FieldElement
        permits BoundAttribute.BoundUnknownAttribute {

    /**
     * {@return the uninterpreted contents of the attribute payload}
     */
    byte[] contents();
}
