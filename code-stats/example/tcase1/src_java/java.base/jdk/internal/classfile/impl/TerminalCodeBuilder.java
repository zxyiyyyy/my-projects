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

public sealed interface TerminalCodeBuilder extends CodeBuilder
        permits DirectCodeBuilder, BufferedCodeBuilder, TransformingCodeBuilder {

}
