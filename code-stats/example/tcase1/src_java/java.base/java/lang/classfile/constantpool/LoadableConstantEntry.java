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
package java.lang.classfile.constantpool;

import java.lang.constant.ConstantDesc;
import java.lang.classfile.TypeKind;
import jdk.internal.javac.PreviewFeature;

/**
 * Marker interface for constant pool entries suitable for loading via the
 * {@code LDC} instructions.
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LoadableConstantEntry extends PoolEntry
        permits ClassEntry, ConstantDynamicEntry, ConstantValueEntry, MethodHandleEntry, MethodTypeEntry {

    /**
     * {@return the constant described by this entry}
     */
    ConstantDesc constantValue();

    /**
     * {@return the type of the constant}
     */
    default TypeKind typeKind() {
        return TypeKind.ReferenceType;
    }
}
