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
 * Models a {@code CONSTANT_UTF8_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.7 The CONSTANT_Utf8_info Structure
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface Utf8Entry
        extends CharSequence, AnnotationConstantValueEntry
        permits AbstractPoolEntry.Utf8EntryImpl {

    /**
     * {@return the string value for this entry}
     */
    String stringValue();

    /**
     * {@return whether this entry describes the same string as the provided string}
     *
     * @param s the string to compare to
     */
    boolean equalsString(String s);
}
