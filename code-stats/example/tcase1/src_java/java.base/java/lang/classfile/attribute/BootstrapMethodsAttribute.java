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

import java.util.List;

import java.lang.classfile.Attribute;
import java.lang.classfile.BootstrapMethodEntry;
import java.lang.classfile.constantpool.ConstantPool;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code BootstrapMethods} attribute {@jvms 4.7.23}, which serves as
 * an extension to the constant pool of a classfile.  Elements of the bootstrap
 * method table are accessed through {@link ConstantPool}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 7.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface BootstrapMethodsAttribute
        extends Attribute<BootstrapMethodsAttribute>
        permits BoundAttribute.BoundBootstrapMethodsAttribute,
                UnboundAttribute.EmptyBootstrapAttribute {

    /**
     * {@return the elements of the bootstrap method table}
     */
    List<BootstrapMethodEntry> bootstrapMethods();

    /**
     * {@return the size of the bootstrap methods table}.  Calling this method
     * does not necessarily inflate the entire table.
     */
    int bootstrapMethodsSize();

    // No factories; BMA is generated as part of constant pool
}
