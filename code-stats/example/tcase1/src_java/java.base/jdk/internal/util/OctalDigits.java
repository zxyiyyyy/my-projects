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

package jdk.internal.util;

import java.lang.invoke.MethodHandle;

import jdk.internal.vm.annotation.Stable;

/**
 * Digits class for octal digits.
 *
 * @since 21
 */
public final class OctalDigits implements Digits {
    @Stable
    private static final short[] DIGITS;

    /**
     * Singleton instance of OctalDigits.
     */
    public static final Digits INSTANCE = new OctalDigits();

    static {
        short[] digits = new short[8 * 8];

        for (int i = 0; i < 8; i++) {
            short lo = (short) (i + '0');

            for (int j = 0; j < 8; j++) {
                short hi = (short) ((j + '0') << 8);
                digits[(i << 3) + j] = (short) (hi | lo);
            }
        }

        DIGITS = digits;
    }

    /**
     * Constructor.
     */
    private OctalDigits() {
    }

    @Override
    public int digits(long value, byte[] buffer, int index,
                      MethodHandle putCharMH) throws Throwable {
        while ((value & ~0x3F) != 0) {
            int digits = DIGITS[(int) (value & 0x3F)];
            value >>>= 6;
            putCharMH.invokeExact(buffer, --index, digits >> 8);
            putCharMH.invokeExact(buffer, --index, digits & 0xFF);
        }

        int digits = DIGITS[(int) (value & 0x3F)];
        putCharMH.invokeExact(buffer, --index, digits >> 8);

        if (7 < value) {
            putCharMH.invokeExact(buffer, --index, digits & 0xFF);
        }

        return index;
    }

    @Override
    public int size(long value) {
        return (66 - Long.numberOfLeadingZeros(value)) / 3;
    }
}
