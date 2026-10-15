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

package jdk.internal.foreign;

import jdk.internal.access.JavaNioAccess;
import jdk.internal.access.SharedSecrets;
import jdk.internal.vm.annotation.ForceInline;
import sun.nio.ch.DirectBuffer;

import java.nio.Buffer;
import java.util.Objects;

/**
 * The global, non-closeable, shared session. Similar to a shared session, but its {@link #close()} method throws unconditionally.
 * Adding new resources to the global session, does nothing: as the session can never become not-alive, there is nothing to track.
 * Acquiring and or releasing a memory session similarly does nothing.
 */
non-sealed class GlobalSession extends MemorySessionImpl {

    public GlobalSession() {
        super(null, null);
    }

    @Override
    @ForceInline
    public void release0() {
        // do nothing
    }

    @Override
    public boolean isCloseable() {
        return false;
    }

    @Override
    @ForceInline
    public void acquire0() {
        // do nothing
    }

    @Override
    void addInternal(ResourceList.ResourceCleanup resource) {
        // do nothing
    }

    @Override
    public void justClose() {
        throw nonCloseable();
    }

    /**
     * This is a global session that wraps a heap object. Possible objects are: Java arrays, buffers and
     * class loaders. Objects of two heap sessions are compared by identity. That is, if the wrapped object is the same,
     * then the resulting heap sessions are also considered equals. We do not compare the objects using
     * {@link Object#equals(Object)}, as that would be problematic when comparing buffers, whose equality and
     * hash codes are content-dependent.
     */
    static class HeapSession extends GlobalSession {

        final Object ref;

        public HeapSession(Object ref) {
            super();
            this.ref = Objects.requireNonNull(ref);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof HeapSession session &&
                    ref == session.ref;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(ref);
        }
    }
}
