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

import jdk.internal.classfile.impl.AbstractPoolEntry;
import java.lang.constant.ModuleDesc;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code CONSTANT_Module_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.11 The CONSTANT_Module_info Structure
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ModuleEntry extends PoolEntry
        permits AbstractPoolEntry.ModuleEntryImpl {
    /**
     * {@return the name of the module}
     */
    Utf8Entry name();

    /**
     * {@return a symbolic descriptor for the module}
     */
    ModuleDesc asSymbol();
}
