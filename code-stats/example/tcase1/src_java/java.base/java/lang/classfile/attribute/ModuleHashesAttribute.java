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

package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.ClassElement;

import java.util.List;

import java.lang.classfile.constantpool.Utf8Entry;
import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code ModuleHashes} attribute, which can
 * appear on classes that represent module descriptors.  This is a JDK-specific
 * attribute, which captures the hashes of a set of co-delivered modules.
 * Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing the elements of a {@link java.lang.classfile.ClassModel}.
 *
 *  <p>The specification of the {@code ModuleHashes} attribute is:
 * <pre> {@code
 *
 * ModuleHashes_attribute {
 *   // index to CONSTANT_utf8_info structure in constant pool representing
 *   // the string "ModuleHashes"
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 *
 *   // index to CONSTANT_utf8_info structure with algorithm name
 *   u2 algorithm_index;
 *
 *   // the number of entries in the hashes table
 *   u2 hashes_count;
 *   {   u2 module_name_index (index to CONSTANT_Module_info structure)
 *       u2 hash_length;
 *       u1 hash[hash_length];
 *   } hashes[hashes_count];
 *
 * }
 * } </pre>
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ModuleHashesAttribute
        extends Attribute<ModuleHashesAttribute>, ClassElement
        permits BoundAttribute.BoundModuleHashesAttribute, UnboundAttribute.UnboundModuleHashesAttribute {

    /**
     * {@return the algorithm name used to compute the hash}
     */
    Utf8Entry algorithm();

    /**
     * {@return the hash information about related modules}
     */
    List<ModuleHashInfo> hashes();

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(String algorithm,
                                    List<ModuleHashInfo> hashes) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(algorithm), hashes);
    }

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(String algorithm,
                                    ModuleHashInfo... hashes) {
        return of(algorithm, List.of(hashes));
    }

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(Utf8Entry algorithm,
                                    List<ModuleHashInfo> hashes) {
        return new UnboundAttribute.UnboundModuleHashesAttribute(algorithm, hashes);
    }

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(Utf8Entry algorithm,
                                    ModuleHashInfo... hashes) {
        return of(algorithm, List.of(hashes));
    }
}
