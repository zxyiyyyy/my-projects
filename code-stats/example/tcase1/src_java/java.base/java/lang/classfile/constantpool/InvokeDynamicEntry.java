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

import java.lang.constant.ConstantDesc;
import java.lang.constant.DynamicCallSiteDesc;
import java.lang.constant.MethodTypeDesc;

import jdk.internal.classfile.impl.AbstractPoolEntry;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a constant pool entry for a dynamic call site.
 * @jvms 4.4.10 The CONSTANT_Dynamic_info and CONSTANT_InvokeDynamic_info Structures
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface InvokeDynamicEntry
        extends DynamicConstantPoolEntry
        permits AbstractPoolEntry.InvokeDynamicEntryImpl {

    /**
     * {@return a symbolic descriptor for the call site's invocation type}
     */
    default MethodTypeDesc typeSymbol() {
        return Util.methodTypeSymbol(nameAndType());
    }

    /**
     * {@return a symbolic descriptor for the dynamic call site}
     */
    default DynamicCallSiteDesc asSymbol() {
        return DynamicCallSiteDesc.of(bootstrap().bootstrapMethod().asSymbol(),
                                      name().stringValue(),
                                      typeSymbol(),
                                      bootstrap().arguments().stream()
                                                 .map(LoadableConstantEntry::constantValue)
                                                 .toArray(ConstantDesc[]::new));
    }
}
