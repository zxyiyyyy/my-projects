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
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code CONSTANT_NameAndType_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.6 The CONSTANT_NameAndType_info Structure
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface NameAndTypeEntry extends PoolEntry
        permits AbstractPoolEntry.NameAndTypeEntryImpl {

    /**
     * {@return the field or method name}
     */
    Utf8Entry name();

    /**
     * {@return the field or method descriptor}
     */
    Utf8Entry type();
}
