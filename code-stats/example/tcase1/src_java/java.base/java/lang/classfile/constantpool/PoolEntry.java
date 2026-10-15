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

import java.lang.classfile.WritableElement;
import jdk.internal.javac.PreviewFeature;

/**
 * Models an entry in the constant pool of a classfile.
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface PoolEntry extends WritableElement<PoolEntry>
        permits AnnotationConstantValueEntry, DynamicConstantPoolEntry,
                LoadableConstantEntry, MemberRefEntry, ModuleEntry, NameAndTypeEntry,
                PackageEntry {

    /**
     * {@return the constant pool this entry is from}
     */
    ConstantPool constantPool();

    /**
     * {@return the constant pool tag that describes the type of this entry}
     */
    byte tag();

    /**
     * {@return the index within the constant pool corresponding to this entry}
     */
    int index();

    /**
     * {@return the number of constant pool slots this entry consumes}
     */
    int width();
}
