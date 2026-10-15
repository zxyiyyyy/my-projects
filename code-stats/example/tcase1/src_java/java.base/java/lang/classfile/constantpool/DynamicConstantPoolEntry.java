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

import java.lang.classfile.BootstrapMethodEntry;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a dynamic constant pool entry, which is either {@link ConstantDynamicEntry}
 * or {@link InvokeDynamicEntry}.
 * @jvms 4.4.10 The CONSTANT_Dynamic_info and CONSTANT_InvokeDynamic_info Structures
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface DynamicConstantPoolEntry extends PoolEntry
        permits ConstantDynamicEntry, InvokeDynamicEntry {

    /**
     * {@return the entry in the bootstrap method table for this constant}
     */
    BootstrapMethodEntry bootstrap();

    /**
     * {@return index of the entry in the bootstrap method table for this constant}
     */
    int bootstrapMethodIndex();

    /**
     * {@return the invocation name and type}
     */
    NameAndTypeEntry nameAndType();

    /**
     * {@return the invocation name}
     */
    default Utf8Entry name() {
        return nameAndType().name();
    }

    /**
     * {@return the invocation type}
     */
    default Utf8Entry type() {
        return nameAndType().type();
    }
}
