/*
 * Copyright (c) 2022, 2023, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 SAP SE. All rights reserved.
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
package jdk.internal.foreign.abi.ppc64.aix;

import jdk.internal.foreign.abi.AbstractLinker;
import jdk.internal.foreign.abi.LinkerOptions;
import jdk.internal.foreign.abi.SharedUtils;
import jdk.internal.foreign.abi.ppc64.CallArranger;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.nio.ByteOrder;
import java.util.Map;

public final class AixPPC64Linker extends AbstractLinker {

    static final Map<String, MemoryLayout> CANONICAL_LAYOUTS =
            SharedUtils.canonicalLayouts(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT);

    public static AixPPC64Linker getInstance() {
        final class Holder {
            private static final AixPPC64Linker INSTANCE = new AixPPC64Linker();
        }

        return Holder.INSTANCE;
    }

    private AixPPC64Linker() {
        // Ensure there is only one instance
    }

    @Override
    protected void checkStructMember(MemoryLayout member, long offset) {
        // special case double members that are not the first member
        // see: https://www.ibm.com/docs/en/xl-c-and-cpp-aix/16.1?topic=data-using-alignment-modes
        // Note: It is possible to enforce 8-byte alignment by #pragma align (natural)
        // Therefore, we use normal checks if we are already 8-byte aligned.
        if ((offset % 8 != 0) && (member instanceof ValueLayout vl && vl.carrier() == double.class)) {
            if (vl.byteAlignment() != 4) {
                throw new IllegalArgumentException("double struct member " + vl + " at offset " + offset + " should be 4-byte aligned");
            }
            if (vl.order() != linkerByteOrder()) {
                throw new IllegalArgumentException("double struct member " + vl + " at offset " + offset + " has an unexpected byte order");
            }
        } else {
            super.checkStructMember(member, offset);
        }
    }

    @Override
    protected MethodHandle arrangeDowncall(MethodType inferredMethodType, FunctionDescriptor function, LinkerOptions options) {
        return CallArranger.AIX.arrangeDowncall(inferredMethodType, function, options);
    }

    @Override
    protected UpcallStubFactory arrangeUpcall(MethodType targetType, FunctionDescriptor function, LinkerOptions options) {
        return CallArranger.AIX.arrangeUpcall(targetType, function, options);
    }

    @Override
    protected ByteOrder linkerByteOrder() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public Map<String, MemoryLayout> canonicalLayouts() {
        return CANONICAL_LAYOUTS;
    }
}
