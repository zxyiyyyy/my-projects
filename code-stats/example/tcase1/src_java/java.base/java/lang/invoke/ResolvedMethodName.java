/*
 * Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

final class ResolvedMethodName {
    //@Injected JVM_Method* vmtarget;
    private Class<?> vmholder;

    Class<?> declaringClass() {
        return vmholder;
    }
}
