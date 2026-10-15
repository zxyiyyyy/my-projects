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
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassModel;
import java.lang.classfile.constantpool.Utf8Entry;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code SourceFile} attribute {@jvms 4.7.10}, which
 * can appear on classes. Delivered as a {@link java.lang.classfile.ClassElement}
 * when traversing a {@link ClassModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface SourceFileAttribute
        extends Attribute<SourceFileAttribute>, ClassElement
        permits BoundAttribute.BoundSourceFileAttribute, UnboundAttribute.UnboundSourceFileAttribute {

    /**
     * {@return the name of the source file from which this class was compiled}
     */
    Utf8Entry sourceFile();

    /**
     * {@return a source file attribute}
     * @param sourceFile the source file name
     */
    static SourceFileAttribute of(String sourceFile) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(sourceFile));
    }

    /**
     * {@return a source file attribute}
     * @param sourceFile the source file name
     */
    static SourceFileAttribute of(Utf8Entry sourceFile) {
        return new UnboundAttribute.UnboundSourceFileAttribute(sourceFile);
    }
}
