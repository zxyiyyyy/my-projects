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
import jdk.internal.classfile.impl.BoundAttribute;

import java.util.Arrays;
import java.util.List;

import java.lang.classfile.constantpool.PackageEntry;
import java.lang.constant.PackageDesc;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code ModulePackages} attribute {@jvms 4.7.26}, which can
 * appear on classes that represent module descriptors.
 * Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing the elements of a {@link java.lang.classfile.ClassModel}.
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 * <p>
 * The attribute was introduced in the Java SE Platform version 9.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ModulePackagesAttribute
        extends Attribute<ModulePackagesAttribute>, ClassElement
        permits BoundAttribute.BoundModulePackagesAttribute,
                UnboundAttribute.UnboundModulePackagesAttribute {

    /**
     * {@return the packages that are opened or exported by this module}
     */
    List<PackageEntry> packages();

    /**
     * {@return a {@code ModulePackages} attribute}
     * @param packages the packages
     */
    static ModulePackagesAttribute of(List<PackageEntry> packages) {
        return new UnboundAttribute.UnboundModulePackagesAttribute(packages);
    }

    /**
     * {@return a {@code ModulePackages} attribute}
     * @param packages the packages
     */
    static ModulePackagesAttribute of(PackageEntry... packages) {
        return of(List.of(packages));
    }

    /**
     * {@return a {@code ModulePackages} attribute}
     * @param packages the packages
     */
    static ModulePackagesAttribute ofNames(List<PackageDesc> packages) {
        var p = new PackageEntry[packages.size()];
        for (int i = 0; i < packages.size(); i++) {
            p[i] = TemporaryConstantPool.INSTANCE.packageEntry(TemporaryConstantPool.INSTANCE.utf8Entry(packages.get(i).internalName()));
        }
        return of(p);
    }

    /**
     * {@return a {@code ModulePackages} attribute}
     * @param packages the packages
     */
    static ModulePackagesAttribute ofNames(PackageDesc... packages) {
        // List view, since ref to packages is temporary
        return ofNames(Arrays.asList(packages));
    }
}
