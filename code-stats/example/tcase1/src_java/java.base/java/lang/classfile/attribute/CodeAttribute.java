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

import java.lang.classfile.Attribute;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Label;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code Code} attribute {@jvms 4.7.3}, appears on non-native,
 * non-abstract methods and contains the bytecode of the method body.  Delivered
 * as a {@link java.lang.classfile.MethodElement} when traversing the elements of a
 * {@link java.lang.classfile.MethodModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface CodeAttribute extends Attribute<CodeAttribute>, CodeModel
        permits BoundAttribute.BoundCodeAttribute {

    /**
     * {@return The length of the code array in bytes}
     */
    int codeLength();

    /**
     * {@return the bytes (bytecode) of the code array}
     */
    byte[] codeArray();

    /**
     * {@return the position of the {@code Label} in the {@code codeArray}
     * or -1 if the {@code Label} does not point to the {@code codeArray}}
     * @param label a marker for a position within this {@code CodeAttribute}
     */
    int labelToBci(Label label);
}
