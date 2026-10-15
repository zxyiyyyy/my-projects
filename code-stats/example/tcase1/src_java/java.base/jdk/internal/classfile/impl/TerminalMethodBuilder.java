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

import java.lang.classfile.CodeModel;
import java.lang.classfile.MethodBuilder;

public sealed interface TerminalMethodBuilder
        extends MethodBuilder
        permits BufferedMethodBuilder, DirectMethodBuilder {
    BufferedCodeBuilder bufferedCodeBuilder(CodeModel original);
}
