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
import jdk.internal.classfile.impl.Util;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;
import java.lang.constant.DynamicConstantDesc;

import jdk.internal.classfile.impl.AbstractPoolEntry;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a {@code CONSTANT_Dynamic_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.10 The CONSTANT_Dynamic_info and CONSTANT_InvokeDynamic_info Structures
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ConstantDynamicEntry
        extends DynamicConstantPoolEntry, LoadableConstantEntry
        permits AbstractPoolEntry.ConstantDynamicEntryImpl {

    /**
     * {@return a symbolic descriptor for the dynamic constant's type}
     */
    default ClassDesc typeSymbol() {
        return Util.fieldTypeSymbol(nameAndType());
    }

    @Override
    default ConstantDesc constantValue() {
        return asSymbol();
    }

    /**
     * {@return the symbolic descriptor for the {@code invokedynamic} constant}
     */
    default DynamicConstantDesc<?> asSymbol() {
        return DynamicConstantDesc.ofNamed(bootstrap().bootstrapMethod().asSymbol(),
                                           name().stringValue(),
                                           typeSymbol(),
                                           bootstrap().arguments().stream()
                                                      .map(LoadableConstantEntry::constantValue)
                                                      .toArray(ConstantDesc[]::new));
    }

    /**
     * {@return the type of the constant}
     */
    @Override
    default TypeKind typeKind() {
        return TypeKind.fromDescriptor(type().stringValue());
    }
}
