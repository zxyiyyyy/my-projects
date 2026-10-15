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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.classfile.BootstrapMethodEntry;
import java.lang.classfile.ClassReader;
import jdk.internal.javac.PreviewFeature;

/**
 * Provides read access to the constant pool and bootstrap method table of a
 * classfile.
 * @jvms 4.4 The Constant Pool
 *
 * @sealedGraph
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ConstantPool extends Iterable<PoolEntry>
        permits ClassReader, ConstantPoolBuilder {

    /**
     * {@return the entry at the specified index}
     *
     * @param index the index within the pool of the desired entry
     * @throws ConstantPoolException if the index is out of range of the
     *         constant pool, or is considered unusable
     */
    PoolEntry entryByIndex(int index);

    /**
     * {@return the size of the constant pool}
     */
    int size();

    /**
     * {@return an iterator over pool entries}
     */
    @Override
    default Iterator<PoolEntry> iterator() {
        return new Iterator<>() {
            int index = 1;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public PoolEntry next() {
                if (!hasNext()) throw new NoSuchElementException();
                var e = entryByIndex(index);
                index += e.width();
                return e;
            }
        };
    }


    /**
     * {@return the {@link BootstrapMethodEntry} at the specified index within
     * the bootstrap method table}
     *
     * @param index the index within the bootstrap method table of the desired
     *              entry
     * @throws ConstantPoolException if the index is out of range of the
     *         bootstrap methods
     */
    BootstrapMethodEntry bootstrapMethodEntry(int index);

    /**
     * {@return the number of entries in the bootstrap method table}
     */
    int bootstrapMethodCount();
}
