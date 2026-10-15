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

import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a single line number in the {@link LineNumberTableAttribute}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface LineNumberInfo
        permits UnboundAttribute.UnboundLineNumberInfo {

    /**
     * {@return the index into the code array at which the code for this line
     * begins}
     */
    int startPc();

    /**
     * {@return the line number within the original source file}
     */
    int lineNumber();

    /**
     * {@return a line number description}
     * @param startPc the starting index of the code array for this line
     * @param lineNumber the line number within the original source file
     */
    public static LineNumberInfo of(int startPc, int lineNumber) {
        return new UnboundAttribute.UnboundLineNumberInfo(startPc, lineNumber);
    }
}
