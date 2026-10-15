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
package jdk.internal.classfile.impl;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.Label;
import java.lang.classfile.TypeKind;
import java.lang.classfile.instruction.LabelTarget;

import java.util.Objects;
import java.lang.classfile.Instruction;

public final class BlockCodeBuilderImpl
        extends NonterminalCodeBuilder
        implements CodeBuilder.BlockCodeBuilder {
    private final Label startLabel, endLabel, breakLabel;
    private boolean reachable = true;
    private boolean hasInstructions = false;
    private int topLocal;
    private int terminalMaxLocals;

    public BlockCodeBuilderImpl(CodeBuilder parent, Label breakLabel) {
        super(parent);
        this.startLabel = parent.newLabel();
        this.endLabel = parent.newLabel();
        this.breakLabel = Objects.requireNonNull(breakLabel);
    }

    public void start() {
        topLocal = topLocal(parent);
        terminalMaxLocals = topLocal(terminal);
        terminal.with((LabelTarget) startLabel);
    }

    public void end() {
        terminal.with((LabelTarget) endLabel);
        if (terminalMaxLocals != topLocal(terminal)) {
            throw new IllegalStateException("Interference in local variable slot management");
        }
    }

    public boolean reachable() {
        return reachable;
    }

    public boolean isEmpty() {
        return !hasInstructions;
    }

    private int topLocal(CodeBuilder parent) {
        return switch (parent) {
            case BlockCodeBuilderImpl b -> b.topLocal;
            case ChainedCodeBuilder b -> topLocal(b.terminal);
            case DirectCodeBuilder b -> b.curTopLocal();
            case BufferedCodeBuilder b -> b.curTopLocal();
            case TransformingCodeBuilder b -> topLocal(b.delegate);
        };
    }

    @Override
    public CodeBuilder with(CodeElement element) {
        parent.with(element);

        hasInstructions |= element instanceof Instruction;

        if (reachable) {
            if (element instanceof Instruction i && i.opcode().isUnconditionalBranch())
                reachable = false;
        }
        else if (element instanceof LabelTarget) {
            reachable = true;
        }
        return this;
    }

    @Override
    public Label startLabel() {
        return startLabel;
    }

    @Override
    public Label endLabel() {
        return endLabel;
    }

    @Override
    public int allocateLocal(TypeKind typeKind) {
        int retVal = topLocal;
        topLocal += typeKind.slotSize();
        return retVal;
    }

    @Override
    public Label breakLabel() {
        return breakLabel;
    }
}
