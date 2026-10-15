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
 * Models a {@code CONSTANT_String_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.3 The CONSTANT_String_info Structure
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface StringEntry
        extends ConstantValueEntry
        permits AbstractPoolEntry.StringEntryImpl {
    /**
     * {@return the UTF constant pool entry describing the string contents}
     */
    Utf8Entry utf8();

    /**
     * {@return the string value for this entry}
     */
    String stringValue();
}
