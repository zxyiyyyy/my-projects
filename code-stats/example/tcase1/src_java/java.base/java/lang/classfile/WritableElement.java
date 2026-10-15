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
package java.lang.classfile;

import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.classfile.constantpool.PoolEntry;
import jdk.internal.classfile.impl.DirectFieldBuilder;
import jdk.internal.classfile.impl.DirectMethodBuilder;
import jdk.internal.javac.PreviewFeature;

/**
 * A classfile element that can encode itself as a stream of bytes in the
 * encoding expected by the classfile format.
 *
 * @param <T> the type of the entity
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface WritableElement<T> extends ClassFileElement
        permits Annotation, AnnotationElement, AnnotationValue, Attribute,
                PoolEntry, BootstrapMethodEntry, FieldModel, MethodModel,
                ConstantPoolBuilder, DirectFieldBuilder, DirectMethodBuilder {
    /**
     * Writes the element to the specified writer
     *
     * @param buf the writer
     */
    void writeTo(BufWriter buf);
}
