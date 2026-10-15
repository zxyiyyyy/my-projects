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
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.MethodElement;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code Exceptions} attribute {@jvms 4.7.5}, which can appear on
 * methods, and records the exceptions declared to be thrown by this method.
 * Delivered as a {@link MethodElement} when traversing the elements of a
 * {@link java.lang.classfile.MethodModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ExceptionsAttribute
        extends Attribute<ExceptionsAttribute>, MethodElement
        permits BoundAttribute.BoundExceptionsAttribute,
                UnboundAttribute.UnboundExceptionsAttribute {

    /**
     * {@return the exceptions declared to be thrown by this method}
     */
    List<ClassEntry> exceptions();

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the checked exceptions that may be thrown from this method
     */
    static ExceptionsAttribute of(List<ClassEntry> exceptions) {
        return new UnboundAttribute.UnboundExceptionsAttribute(exceptions);
    }

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the checked exceptions that may be thrown from this method
     */
    static ExceptionsAttribute of(ClassEntry... exceptions) {
        return of(List.of(exceptions));
    }

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the checked exceptions that may be thrown from this method
     */
    static ExceptionsAttribute ofSymbols(List<ClassDesc> exceptions) {
        return of(Util.entryList(exceptions));
    }

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the checked exceptions that may be thrown from this method
     */
    static ExceptionsAttribute ofSymbols(ClassDesc... exceptions) {
        return ofSymbols(Arrays.asList(exceptions));
    }
}
