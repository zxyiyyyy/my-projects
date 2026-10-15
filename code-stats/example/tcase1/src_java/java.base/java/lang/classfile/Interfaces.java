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

import java.lang.constant.ClassDesc;
import java.util.Arrays;
import java.util.List;

import java.lang.classfile.constantpool.ClassEntry;
import jdk.internal.classfile.impl.InterfacesImpl;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the interfaces of a class.  Delivered as a {@link
 * java.lang.classfile.ClassElement} when traversing a {@link ClassModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface Interfaces
        extends ClassElement
        permits InterfacesImpl {

    /** {@return the interfaces of this class} */
    List<ClassEntry> interfaces();

    /**
     * {@return an {@linkplain Interfaces} element}
     * @param interfaces the interfaces
     */
    static Interfaces of(List<ClassEntry> interfaces) {
        return new InterfacesImpl(interfaces);
    }

    /**
     * {@return an {@linkplain Interfaces} element}
     * @param interfaces the interfaces
     */
    static Interfaces of(ClassEntry... interfaces) {
        return of(List.of(interfaces));
    }

    /**
     * {@return an {@linkplain Interfaces} element}
     * @param interfaces the interfaces
     */
    static Interfaces ofSymbols(List<ClassDesc> interfaces) {
        return of(Util.entryList(interfaces));
    }

    /**
     * {@return an {@linkplain Interfaces} element}
     * @param interfaces the interfaces
     */
    static Interfaces ofSymbols(ClassDesc... interfaces) {
        return ofSymbols(Arrays.asList(interfaces));
    }
}
