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

import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.instruction.CharacterRange;
import java.lang.classfile.instruction.ExceptionCatch;
import java.lang.classfile.instruction.LabelTarget;
import java.lang.classfile.instruction.LineNumber;
import java.lang.classfile.instruction.LocalVariable;
import java.lang.classfile.instruction.LocalVariableType;
import jdk.internal.classfile.impl.AbstractPseudoInstruction;
import jdk.internal.javac.PreviewFeature;

/**
 * Models metadata about a {@link CodeAttribute}, such as entries in the
 * exception table, line number table, local variable table, or the mapping
 * between instructions and labels.  Pseudo-instructions are delivered as part
 * of the element stream of a {@link CodeModel}.  Delivery of some
 * pseudo-instructions can be disabled by modifying the value of classfile
 * options (e.g., {@link ClassFile.DebugElementsOption}).
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface PseudoInstruction
        extends CodeElement
        permits CharacterRange, ExceptionCatch, LabelTarget, LineNumber, LocalVariable, LocalVariableType, AbstractPseudoInstruction {
}
