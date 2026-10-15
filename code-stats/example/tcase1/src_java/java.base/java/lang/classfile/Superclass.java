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

import java.lang.classfile.constantpool.ClassEntry;
import jdk.internal.classfile.impl.SuperclassImpl;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the superclass of a class.  Delivered as a {@link
 * java.lang.classfile.ClassElement} when traversing a {@link ClassModel}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface Superclass
        extends ClassElement
        permits SuperclassImpl {

    /** {@return the superclass} */
    ClassEntry superclassEntry();

    /**
     * {@return a {@linkplain Superclass} element}
     * @param superclassEntry the superclass
     */
    static Superclass of(ClassEntry superclassEntry) {
        return new SuperclassImpl(superclassEntry);
    }
}
