/*
 *  Copyright (c) 2020, 2023, Oracle and/or its affiliates. All rights reserved.
 *  ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
 *
 */

package jdk.internal.foreign;

import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;
import java.util.Optional;

import jdk.internal.misc.Unsafe;
import jdk.internal.vm.annotation.ForceInline;

/**
 * Implementation for native memory segments. A native memory segment is essentially a wrapper around
 * a native long address.
 */
sealed class NativeMemorySegmentImpl extends AbstractMemorySegmentImpl permits MappedMemorySegmentImpl {

    final long min;

    @ForceInline
    NativeMemorySegmentImpl(long min, long length, boolean readOnly, MemorySessionImpl scope) {
        super(length, readOnly, scope);
        this.min = (Unsafe.getUnsafe().addressSize() == 4)
                // On 32-bit systems, normalize the upper unused 32-bits to zero
                ? min & 0x0000_0000_FFFF_FFFFL
                // On 64-bit systems, all the bits are used
                : min;
    }

    @Override
    public long address() {
        return min;
    }

    @Override
    public Optional<Object> heapBase() {
        return Optional.empty();
    }

    @ForceInline
    @Override
    NativeMemorySegmentImpl dup(long offset, long size, boolean readOnly, MemorySessionImpl scope) {
        return new NativeMemorySegmentImpl(min + offset, size, readOnly, scope);
    }

    @Override
    ByteBuffer makeByteBuffer() {
        return NIO_ACCESS.newDirectByteBuffer(min, (int) this.length, null, this);
    }

    @Override
    public boolean isNative() {
        return true;
    }

    @Override
    public long unsafeGetOffset() {
        return min;
    }

    @Override
    public Object unsafeGetBase() {
        return null;
    }

    @Override
    public long maxAlignMask() {
        return 0;
    }
}
