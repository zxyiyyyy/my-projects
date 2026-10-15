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

package jdk.internal.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySegment.Scope;
import java.util.Objects;

public final class ArenaImpl implements Arena {

    private final MemorySessionImpl session;
    private final boolean shouldReserveMemory;
    ArenaImpl(MemorySessionImpl session) {
        this.session = session;
        shouldReserveMemory = session instanceof ImplicitSession;
    }

    @Override
    public Scope scope() {
        return session;
    }

    @Override
    public void close() {
        session.close();
    }

    public MemorySegment allocateNoInit(long byteSize, long byteAlignment) {
        Utils.checkAllocationSizeAndAlign(byteSize, byteAlignment);
        return SegmentFactories.allocateSegment(byteSize, byteAlignment, session, shouldReserveMemory);
    }

    @Override
    public MemorySegment allocate(long byteSize, long byteAlignment) {
        MemorySegment segment = allocateNoInit(byteSize, byteAlignment);
        return segment.fill((byte)0);
    }
}
