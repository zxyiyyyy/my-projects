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

import java.lang.classfile.TypeKind;
import jdk.internal.classfile.impl.AbstractPoolEntry;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code CONSTANT_Float_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.4 The CONSTANT_Integer_info and CONSTANT_Float_info Structures
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface FloatEntry
        extends AnnotationConstantValueEntry, ConstantValueEntry
        permits AbstractPoolEntry.FloatEntryImpl {

    /**
     * {@return the float value}
     */

    float floatValue();

    /**
     * {@return the type of the constant}
     */
    @Override
    default TypeKind typeKind() {
        return TypeKind.FloatType;
    }
}
