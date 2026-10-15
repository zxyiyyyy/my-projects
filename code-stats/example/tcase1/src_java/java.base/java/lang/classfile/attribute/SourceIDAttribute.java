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
import java.lang.classfile.ClassModel;
import java.lang.classfile.constantpool.Utf8Entry;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code SourceID} attribute, which can
 * appear on classes. Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing a {@link ClassModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface SourceIDAttribute
        extends Attribute<SourceIDAttribute>, ClassElement
        permits BoundAttribute.BoundSourceIDAttribute, UnboundAttribute.UnboundSourceIDAttribute {

    /**
     * {@return the source id}  The source id is the last modified time of the
     * source file (as reported by the filesystem, in milliseconds) when the
     * classfile is compiled.
     */
    Utf8Entry sourceId();

    /**
     * {@return a {@code SourceID} attribute}
     * @param sourceId the source id
     */
    static SourceIDAttribute of(Utf8Entry sourceId) {
        return new UnboundAttribute.UnboundSourceIDAttribute(sourceId);
    }

    /**
     * {@return a {@code SourceID} attribute}
     * @param sourceId the source id
     */
    static SourceIDAttribute of(String sourceId) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(sourceId));
    }
}
