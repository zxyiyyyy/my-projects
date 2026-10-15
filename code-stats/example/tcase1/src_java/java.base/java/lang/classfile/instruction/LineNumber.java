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

import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.PseudoInstruction;
import java.lang.classfile.attribute.LineNumberTableAttribute;
import jdk.internal.classfile.impl.LineNumberImpl;
import jdk.internal.javac.PreviewFeature;

/**
 * A pseudo-instruction which models a single entry in the
 * {@link LineNumberTableAttribute}.  Delivered as a {@link CodeElement}
 * during traversal of the elements of a {@link CodeModel}, according to
 * the setting of the {@link ClassFile.LineNumbersOption} option.
 *
 * @see PseudoInstruction
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LineNumber extends PseudoInstruction
        permits LineNumberImpl {

    /**
     * {@return the line number}
     */
    int line();

    /**
     * {@return a line number pseudo-instruction}
     *
     * @param line the line number
     */
    static LineNumber of(int line) {
        return LineNumberImpl.of(line);
    }
}
