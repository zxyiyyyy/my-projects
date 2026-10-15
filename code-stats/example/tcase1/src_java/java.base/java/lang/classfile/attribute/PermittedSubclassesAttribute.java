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
package java.lang.classfile.attribute;

import java.lang.constant.ClassDesc;
import java.util.Arrays;
import java.util.List;

import java.lang.classfile.Attribute;
import java.lang.classfile.ClassElement;
import java.lang.classfile.constantpool.ClassEntry;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code PermittedSubclasses} attribute {@jvms 4.7.31}, which can
 * appear on classes to indicate which classes may extend this class.
 * Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing the elements of a {@link java.lang.classfile.ClassModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 17.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface PermittedSubclassesAttribute
        extends Attribute<PermittedSubclassesAttribute>, ClassElement
        permits BoundAttribute.BoundPermittedSubclassesAttribute, UnboundAttribute.UnboundPermittedSubclassesAttribute {

    /**
     * {@return the list of permitted subclasses}
     */
    List<ClassEntry> permittedSubclasses();

    /**
     * {@return a {@code PermittedSubclasses} attribute}
     * @param permittedSubclasses the permitted subclasses
     */
    static PermittedSubclassesAttribute of(List<ClassEntry> permittedSubclasses) {
        return new UnboundAttribute.UnboundPermittedSubclassesAttribute(permittedSubclasses);
    }

    /**
     * {@return a {@code PermittedSubclasses} attribute}
     * @param permittedSubclasses the permitted subclasses
     */
    static PermittedSubclassesAttribute of(ClassEntry... permittedSubclasses) {
        return of(List.of(permittedSubclasses));
    }

    /**
     * {@return a {@code PermittedSubclasses} attribute}
     * @param permittedSubclasses the permitted subclasses
     */
    static PermittedSubclassesAttribute ofSymbols(List<ClassDesc> permittedSubclasses) {
        return of(Util.entryList(permittedSubclasses));
    }

    /**
     * {@return a {@code PermittedSubclasses} attribute}
     * @param permittedSubclasses the permitted subclasses
     */
    static PermittedSubclassesAttribute ofSymbols(ClassDesc... permittedSubclasses) {
        // List view, since ref to nestMembers is temporary
        return ofSymbols(Arrays.asList(permittedSubclasses));
    }
}
