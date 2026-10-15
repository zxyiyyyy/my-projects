/*
 * Copyright (c) 2019, 2023, Oracle and/or its affiliates. All rights reserved.
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

import jdk.internal.foreign.Utils;

/**
 * Base class for memory segment var handle view implementations.
 */
abstract sealed class VarHandleSegmentViewBase extends VarHandle permits
        VarHandleSegmentAsBytes,
        VarHandleSegmentAsChars,
        VarHandleSegmentAsDoubles,
        VarHandleSegmentAsFloats,
        VarHandleSegmentAsInts,
        VarHandleSegmentAsLongs,
        VarHandleSegmentAsShorts {

    /** endianness **/
    final boolean be;

    /** access size (in bytes, computed from var handle carrier type) **/
    final long length;

    /** alignment constraint (in bytes, expressed as a bit mask) **/
    final long alignmentMask;

    VarHandleSegmentViewBase(VarForm form, boolean be, long length, long alignmentMask, boolean exact) {
        super(form, exact);
        this.be = be;
        this.length = length;
        this.alignmentMask = alignmentMask;
    }

    static IllegalArgumentException newIllegalArgumentExceptionForMisalignedAccess(long address) {
        return new IllegalArgumentException("Misaligned access at address: " + Utils.toHexString(address));
    }

    static UnsupportedOperationException newUnsupportedAccessModeForAlignment(long alignment) {
        return new UnsupportedOperationException("Unsupported access mode for alignment: " + alignment);
    }
}
