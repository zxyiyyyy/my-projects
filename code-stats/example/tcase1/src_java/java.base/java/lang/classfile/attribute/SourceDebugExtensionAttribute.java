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
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code SourceDebugExtension} attribute.
 * Delivered as a {@link java.lang.classfile.ClassElement} when traversing the elements of
 * a {@link java.lang.classfile.ClassModel}.
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
public sealed interface SourceDebugExtensionAttribute
        extends Attribute<SourceDebugExtensionAttribute>, ClassElement
        permits BoundAttribute.BoundSourceDebugExtensionAttribute, UnboundAttribute.UnboundSourceDebugExtensionAttribute {

    /**
     * {@return the debug extension payload}
     */
    byte[] contents();

    /**
     * {@return a {@code SourceDebugExtension} attribute}
     * @param contents the extension contents
     */
    static SourceDebugExtensionAttribute of(byte[] contents) {
        return new UnboundAttribute.UnboundSourceDebugExtensionAttribute(contents);
    }
}
