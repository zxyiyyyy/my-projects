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
import java.util.Optional;
import java.util.function.Consumer;

import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.constantpool.ConstantPool;
import jdk.internal.classfile.impl.ClassImpl;
import jdk.internal.classfile.impl.verifier.VerifierImpl;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a classfile.  The contents of the classfile can be traversed via
 * a streaming view (e.g., {@link #elements()}), or via random access (e.g.,
 * {@link #flags()}), or by freely mixing the two.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ClassModel
        extends CompoundElement<ClassElement>, AttributedElement
        permits ClassImpl {

    /**
     * {@return the constant pool for this class}
     */
    ConstantPool constantPool();

    /** {@return the access flags} */
    AccessFlags flags();

    /** {@return the constant pool entry describing the name of this class} */
    ClassEntry thisClass();

    /** {@return the major classfile version} */
    int majorVersion();

    /** {@return the minor classfile version} */
    int minorVersion();

    /** {@return the fields of this class} */
    List<FieldModel> fields();

    /** {@return the methods of this class} */
    List<MethodModel> methods();

    /** {@return the superclass of this class, if there is one} */
    Optional<ClassEntry> superclass();

    /** {@return the interfaces implemented by this class} */
    List<ClassEntry> interfaces();

    /** {@return whether this class is a module descriptor} */
    boolean isModuleInfo();
}
