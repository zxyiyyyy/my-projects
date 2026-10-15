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
package java.lang.classfile.instruction;

import java.lang.classfile.BufWriter;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Label;
import java.lang.classfile.PseudoInstruction;
import java.lang.classfile.Signature;
import java.lang.classfile.attribute.LocalVariableTypeTableAttribute;
import java.lang.classfile.constantpool.Utf8Entry;
import jdk.internal.classfile.impl.AbstractPseudoInstruction;
import jdk.internal.classfile.impl.BoundLocalVariableType;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.javac.PreviewFeature;

/**
 * A pseudo-instruction which models a single entry in the {@link
 * LocalVariableTypeTableAttribute}.  Delivered as a {@link CodeElement} during
 * traversal of the elements of a {@link CodeModel}, according to the setting of
 * the {@link ClassFile.DebugElementsOption} option.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LocalVariableType extends PseudoInstruction
        permits AbstractPseudoInstruction.UnboundLocalVariableType, BoundLocalVariableType {
    /**
     * {@return the local variable slot}
     */
    int slot();

    /**
     * {@return the local variable name}
     */
    Utf8Entry name();

    /**
     * {@return the local variable signature}
     */
    Utf8Entry signature();

    /**
     * {@return the local variable signature}
     */
    default Signature signatureSymbol() {
        return Signature.parseFrom(signature().stringValue());
    }

    /**
     * {@return the start range of the local variable scope}
     */
    Label startScope();

    /**
     * {@return the end range of the local variable scope}
     */
    Label endScope();

    /**
     * Writes the local variable to the specified writer
     *
     * @param buf the writer
     * @return true if the variable has been written
     */
    boolean writeTo(BufWriter buf);

    /**
     * {@return a local variable type pseudo-instruction}
     *
     * @param slot the local variable slot
     * @param nameEntry the local variable name
     * @param signatureEntry the local variable signature
     * @param startScope the start range of the local variable scope
     * @param endScope the end range of the local variable scope
     */
    static LocalVariableType of(int slot, Utf8Entry nameEntry, Utf8Entry signatureEntry, Label startScope, Label endScope) {
        return new AbstractPseudoInstruction.UnboundLocalVariableType(slot, nameEntry, signatureEntry,
                                                                      startScope, endScope);
    }

    /**
     * {@return a local variable type pseudo-instruction}
     *
     * @param slot the local variable slot
     * @param name the local variable name
     * @param signature the local variable signature
     * @param startScope the start range of the local variable scope
     * @param endScope the end range of the local variable scope
     */
    static LocalVariableType of(int slot, String name, Signature signature, Label startScope, Label endScope) {
        return of(slot,
                  TemporaryConstantPool.INSTANCE.utf8Entry(name),
                  TemporaryConstantPool.INSTANCE.utf8Entry(signature.signatureString()),
                  startScope, endScope);
    }
}
