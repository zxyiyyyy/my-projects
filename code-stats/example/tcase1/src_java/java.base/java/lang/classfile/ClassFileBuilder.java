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

import java.lang.constant.ClassDesc;
import java.util.function.Consumer;

import java.lang.classfile.constantpool.ConstantPool;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import jdk.internal.javac.PreviewFeature;

/**
 * A builder for a classfile or portion of a classfile.  Builders are rarely
 * created directly; they are passed to handlers by methods such as
 * {@link ClassFile#build(ClassDesc, Consumer)} or to transforms.
 * Elements of the newly built entity can be specified
 * abstractly (by passing a {@link ClassFileElement} to {@link #with(ClassFileElement)}
 * or concretely by calling the various {@code withXxx} methods.
 *
 * @param <E> the element type
 * @param <B> the builder type
 * @see ClassFileTransform
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ClassFileBuilder<E extends ClassFileElement, B extends ClassFileBuilder<E, B>>
        extends Consumer<E> permits ClassBuilder, FieldBuilder, MethodBuilder, CodeBuilder {

    /**
     * Integrate the {@link ClassFileElement} into the entity being built.
     * @param e the element
     */
    @Override
    default void accept(E e) {
        with(e);
    }

    /**
     * Integrate the {@link ClassFileElement} into the entity being built.
     * @param e the element
     * @return this builder
     */
    B with(E e);

    /**
     * {@return the constant pool builder associated with this builder}
     */
    ConstantPoolBuilder constantPool();

    /**
     * {@return whether the provided constant pool is compatible with this builder}
     * @param source the constant pool to test compatibility with
     */
    default boolean canWriteDirect(ConstantPool source) {
        return constantPool().canWriteDirect(source);
    }

    /**
     * Apply a transform to a model, directing results to this builder.
     * @param model the model to transform
     * @param transform the transform to apply
     */
    default void transform(CompoundElement<E> model, ClassFileTransform<?, E, B> transform) {
        @SuppressWarnings("unchecked")
        B builder = (B) this;
        var resolved = transform.resolve(builder);
        resolved.startHandler().run();
        model.forEachElement(resolved.consumer());
        resolved.endHandler().run();
    }
}
