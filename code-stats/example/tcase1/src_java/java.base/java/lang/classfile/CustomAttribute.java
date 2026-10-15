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
 * Models a non-standard attribute of a classfile.  Clients should extend
 * this class to provide an implementation class for non-standard attributes,
 * and provide an {@link AttributeMapper} to mediate between the classfile
 * format and the {@linkplain CustomAttribute} representation.
 * @param <T> the custom attribute type
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public abstract non-sealed class CustomAttribute<T extends CustomAttribute<T>>
        implements Attribute<T>, CodeElement, ClassElement, MethodElement, FieldElement {

    private final AttributeMapper<T> mapper;

    /**
     * Construct a {@linkplain CustomAttribute}.
     * @param mapper the attribute mapper
     */
    protected CustomAttribute(AttributeMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public final AttributeMapper<T> attributeMapper() {
        return mapper;
    }

    @Override
    public final String attributeName() {
        return mapper.name();
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void writeTo(BufWriter buf) {
        mapper.writeAttribute(buf, (T) this);
    }

    @Override
    public String toString() {
        return String.format("CustomAttribute[name=%s]", mapper.name());
    }
}
