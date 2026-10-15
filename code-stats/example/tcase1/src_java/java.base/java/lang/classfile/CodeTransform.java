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

import java.util.function.Consumer;
import java.util.function.Supplier;

import jdk.internal.classfile.impl.TransformImpl;
import jdk.internal.javac.PreviewFeature;

/**
 * A transformation on streams of {@link CodeElement}.
 *
 * @see ClassFileTransform
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
@FunctionalInterface
public non-sealed interface CodeTransform
        extends ClassFileTransform<CodeTransform, CodeElement, CodeBuilder> {

    /**
     * A code transform that sends all elements to the builder.
     */
    CodeTransform ACCEPT_ALL = new CodeTransform() {
        @Override
        public void accept(CodeBuilder builder, CodeElement element) {
            builder.with(element);
        }
    };

    /**
     * Create a stateful code transform from a {@link Supplier}.  The supplier
     * will be invoked for each transformation.
     *
     * @param supplier a {@link Supplier} that produces a fresh transform object
     *                 for each traversal
     * @return the stateful code transform
     */
    static CodeTransform ofStateful(Supplier<CodeTransform> supplier) {
        return new TransformImpl.SupplierCodeTransform(supplier);
    }

    /**
     * Create a code transform that passes each element through to the builder,
     * and calls the specified function when transformation is complete.
     *
     * @param finisher the function to call when transformation is complete
     * @return the code transform
     */
    static CodeTransform endHandler(Consumer<CodeBuilder> finisher) {
        return new CodeTransform() {
            @Override
            public void accept(CodeBuilder builder, CodeElement element) {
                builder.with(element);
            }

            @Override
            public void atEnd(CodeBuilder builder) {
                finisher.accept(builder);
            }
        };
    }

    /**
     * @implSpec
     * The default implementation returns this code transform chained with another
     * code transform from the argument. Chaining of two transforms requires to
     * involve a chained builder serving as a target builder for this transform
     * and also as a source of elements for the downstream transform.
     */
    @Override
    default CodeTransform andThen(CodeTransform t) {
        return new TransformImpl.ChainedCodeTransform(this, t);
    }

    /**
     * @implSpec The default implementation returns a resolved transform bound
     *           to the given code builder.
     */
    @Override
    default ResolvedTransform<CodeElement> resolve(CodeBuilder builder) {
        return new TransformImpl.ResolvedTransformImpl<>(e -> accept(builder, e),
                                                         () -> atEnd(builder),
                                                         () -> atStart(builder));
    }
}
