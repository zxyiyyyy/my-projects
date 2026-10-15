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
import java.util.List;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributedElement;
import java.lang.classfile.constantpool.Utf8Entry;
import jdk.internal.classfile.impl.BoundRecordComponentInfo;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a single record component in the {@link java.lang.classfile.attribute.RecordAttribute}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface RecordComponentInfo
        extends AttributedElement
        permits BoundRecordComponentInfo, UnboundAttribute.UnboundRecordComponentInfo {
    /**
     * {@return the name of this component}
     */
    Utf8Entry name();

    /**
     * {@return the field descriptor of this component}
     */
    Utf8Entry descriptor();

    /**
     * {@return the field descriptor of this component, as a {@linkplain ClassDesc}}
     */
    default ClassDesc descriptorSymbol() {
        return ClassDesc.ofDescriptor(descriptor().stringValue());
    }

    /**
     * {@return a record component description}
     * @param name the component name
     * @param descriptor the component field descriptor
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(Utf8Entry name,
                                  Utf8Entry descriptor,
                                  List<Attribute<?>> attributes) {
        return new UnboundAttribute.UnboundRecordComponentInfo(name, descriptor, attributes);
    }

    /**
     * {@return a record component description}
     * @param name the component name
     * @param descriptor the component field descriptor
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(Utf8Entry name,
                                  Utf8Entry descriptor,
                                  Attribute<?>... attributes) {
        return of(name, descriptor, List.of(attributes));
    }

    /**
     * {@return a record component description}
     * @param name the component name
     * @param descriptor the component field descriptor
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(String name,
                                  ClassDesc descriptor,
                                  List<Attribute<?>> attributes) {
        return new UnboundAttribute.UnboundRecordComponentInfo(TemporaryConstantPool.INSTANCE.utf8Entry(name),
                                                               TemporaryConstantPool.INSTANCE.utf8Entry(descriptor.descriptorString()),
                                                               attributes);
    }

    /**
     * {@return a record component description}
     * @param name the component name
     * @param descriptor the component field descriptor
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(String name,
                                  ClassDesc descriptor,
                                  Attribute<?>... attributes) {
        return of(name, descriptor, List.of(attributes));
    }
}
