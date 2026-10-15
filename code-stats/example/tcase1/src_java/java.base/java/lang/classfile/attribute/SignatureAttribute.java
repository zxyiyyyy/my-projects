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
import java.lang.classfile.ClassSignature;
import java.lang.classfile.FieldElement;
import java.lang.classfile.MethodElement;
import java.lang.classfile.constantpool.Utf8Entry;
import jdk.internal.classfile.impl.BoundAttribute;
import java.lang.classfile.MethodSignature;
import java.lang.classfile.Signature;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code Signature} attribute {@jvms 4.7.9}, which
 * can appear on classes, methods, or fields. Delivered as a
 * {@link java.lang.classfile.ClassElement}, {@link java.lang.classfile.FieldElement}, or
 * {@link java.lang.classfile.MethodElement} when traversing
 * the corresponding model type.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 5.0.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface SignatureAttribute
        extends Attribute<SignatureAttribute>,
                ClassElement, MethodElement, FieldElement
        permits BoundAttribute.BoundSignatureAttribute, UnboundAttribute.UnboundSignatureAttribute {

    /**
     * {@return the signature for the class, method, or field}
     */
    Utf8Entry signature();

    /**
     * Parse the signature as a class signature.
     * @return the class signature
     */
    default ClassSignature asClassSignature() {
        return ClassSignature.parseFrom(signature().stringValue());
    }

    /**
     * Parse the signature as a method signature.
     * @return the method signature
     */
    default MethodSignature asMethodSignature() {
        return MethodSignature.parseFrom(signature().stringValue());
    }

    /**
     * Parse the signature as a type signature.
     * @return the type signature
     */
    default Signature asTypeSignature() {
        return Signature.parseFrom(signature().stringValue());
    }

    /**
     * {@return a {@code Signature} attribute for a class}
     * @param classSignature the signature
     */
    static SignatureAttribute of(ClassSignature classSignature) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(classSignature.signatureString()));
    }

    /**
     * {@return a {@code Signature} attribute for a method}
     * @param methodSignature the signature
     */
    static SignatureAttribute of(MethodSignature methodSignature) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(methodSignature.signatureString()));
    }

    /**
     * {@return a {@code Signature} attribute}
     * @param signature the signature
     */
    static SignatureAttribute of(Signature signature) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(signature.signatureString()));
    }

    /**
     * {@return a {@code Signature} attribute}
     * @param signature the signature
     */
    static SignatureAttribute of(Utf8Entry signature) {
        return new UnboundAttribute.UnboundSignatureAttribute(signature);
    }
}
