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

import jdk.internal.javac.PreviewFeature;

/**
 * Immutable model for a portion of (or the entirety of) a classfile.  Elements
 * that model parts of the classfile that have attributes will implement {@link
 * AttributedElement}; elements that model complex parts of the classfile that
 * themselves contain their own child elements will implement {@link
 * CompoundElement}.  Elements specific to various locations in the classfile
 * will implement {@link ClassElement}, {@link MethodElement}, etc.
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ClassFileElement
        permits AttributedElement, CompoundElement, WritableElement,
                ClassElement, CodeElement, FieldElement, MethodElement {
}
