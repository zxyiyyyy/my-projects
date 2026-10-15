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
package java.lang.classfile.constantpool;

import jdk.internal.classfile.impl.AbstractPoolEntry;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;
import java.lang.constant.ClassDesc;

/**
 * Models a {@code CONSTANT_Fieldref_info} constant in the constant pool of a
 * classfile.
 * @jvms 4.4.2 The CONSTANT_Fieldref_info, CONSTANT_Methodref_info, and CONSTANT_InterfaceMethodref_info Structures
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface FieldRefEntry extends MemberRefEntry
        permits AbstractPoolEntry.FieldRefEntryImpl {

    /**
     * {@return a symbolic descriptor for the field's type}
     */
    default ClassDesc typeSymbol() {
        return Util.fieldTypeSymbol(nameAndType());
    }
}
