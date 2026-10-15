/*
 * Copyright (c) 2012, 2023, Oracle and/or its affiliates. All rights reserved.
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
package jdk.internal.event;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Helper class for exception events.
 */
public final class ThrowableTracer {

    private static final AtomicLong numThrowables = new AtomicLong();

    public static void enable() throws NoSuchFieldException, IllegalAccessException {
        Field field = Throwable.class.getDeclaredField("jfrTracing");
        field.setAccessible(true);
        field.setBoolean(null, true);
    }

    public static void traceError(Class<?> clazz, String message) {
        if (OutOfMemoryError.class.isAssignableFrom(clazz)) {
            return;
        }

        if (ErrorThrownEvent.enabled()) {
            long timestamp = ErrorThrownEvent.timestamp();
            ErrorThrownEvent.commit(timestamp, message, clazz);
        }
        if (ExceptionThrownEvent.enabled()) {
            long timestamp = ExceptionThrownEvent.timestamp();
            ExceptionThrownEvent.commit(timestamp, message, clazz);
        }
        numThrowables.incrementAndGet();
    }

    public static void traceThrowable(Class<?> clazz, String message) {
        if (ExceptionThrownEvent.enabled()) {
            long timestamp = ExceptionThrownEvent.timestamp();
            ExceptionThrownEvent.commit(timestamp, message, clazz);
        }
        numThrowables.incrementAndGet();
    }

    public static void emitStatistics() {
        long timestamp = ExceptionStatisticsEvent.timestamp();
        ExceptionStatisticsEvent.commit(timestamp, numThrowables.get());
    }
}
