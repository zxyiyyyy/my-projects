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
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code Synthetic} attribute {@jvms 4.7.8}, which can appear on
 * classes, methods, and fields.  Delivered as a  {@link ClassElement},
 * {@link MethodElement}, or  {@link FieldElement} when traversing the elements
 * of a corresponding model.
 * <p>
 * The attribute permits multiple instances in a given location.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface SyntheticAttribute
        extends Attribute<SyntheticAttribute>,
                ClassElement, MethodElement, FieldElement
        permits BoundAttribute.BoundSyntheticAttribute, UnboundAttribute.UnboundSyntheticAttribute {

    /**
     * {@return a {@code Synthetic} attribute}
     */
    static SyntheticAttribute of() {
        return new UnboundAttribute.UnboundSyntheticAttribute();
    }
}
