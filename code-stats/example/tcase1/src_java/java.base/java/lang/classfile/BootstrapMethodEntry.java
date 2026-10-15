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

import java.util.List;

import java.lang.classfile.constantpool.ConstantPool;
import java.lang.classfile.constantpool.LoadableConstantEntry;
import java.lang.classfile.constantpool.MethodHandleEntry;
import jdk.internal.classfile.impl.BootstrapMethodEntryImpl;
import jdk.internal.javac.PreviewFeature;

/**
 * Models an entry in the bootstrap method table.  The bootstrap method table
 * is stored in the {@code BootstrapMethods} attribute, but is modeled by
 * the {@link ConstantPool}, since the bootstrap method table is logically
 * part of the constant pool.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface BootstrapMethodEntry
        extends WritableElement<BootstrapMethodEntry>
        permits BootstrapMethodEntryImpl {

    /**
     * {@return the constant pool associated with this entry}
     */
    ConstantPool constantPool();

    /**
     * {@return the index into the bootstrap method table corresponding to this entry}
     */
    int bsmIndex();

    /**
     * {@return the bootstrap method}
     */
    MethodHandleEntry bootstrapMethod();

    /**
     * {@return the bootstrap arguments}
     */
    List<LoadableConstantEntry> arguments();
}
