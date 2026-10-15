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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import java.lang.classfile.constantpool.ModuleEntry;
import java.lang.classfile.constantpool.PackageEntry;
import java.lang.constant.ModuleDesc;
import java.lang.constant.PackageDesc;
import java.lang.reflect.AccessFlag;

import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;
import jdk.internal.javac.PreviewFeature;

/**
 * Models a single "opens" declaration in the {@link ModuleAttribute}.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ModuleOpenInfo
        permits UnboundAttribute.UnboundModuleOpenInfo {

    /**
     * {@return the package being opened}
     */
    PackageEntry openedPackage();

    /**
     * {@return the flags associated with this open declaration, as a bit mask}
     * Valid flags include {@link java.lang.classfile.ClassFile#ACC_SYNTHETIC} and
     * {@link java.lang.classfile.ClassFile#ACC_MANDATED}
     */
    int opensFlagsMask();

    /**
     * {@return the access flags}
     */
    default Set<AccessFlag> opensFlags() {
        return AccessFlag.maskToAccessFlags(opensFlagsMask(), AccessFlag.Location.MODULE_OPENS);
    }

    /**
     * {@return whether the specified access flag is set}
     * @param flag the access flag
     */
    default boolean has(AccessFlag flag) {
        return Util.has(AccessFlag.Location.MODULE_OPENS, opensFlagsMask(), flag);
    }

    /**
     * The list of modules to which this package is opened, if it is a
     * qualified open.
     *
     * @return the modules to which this package is opened
     */
    List<ModuleEntry> opensTo();

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageEntry opens, int opensFlags,
                             List<ModuleEntry> opensTo) {
        return new UnboundAttribute.UnboundModuleOpenInfo(opens, opensFlags, opensTo);
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageEntry opens, Collection<AccessFlag> opensFlags,
                             List<ModuleEntry> opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageEntry opens,
                             int opensFlags,
                             ModuleEntry... opensTo) {
        return of(opens, opensFlags, List.of(opensTo));
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageEntry opens,
                             Collection<AccessFlag> opensFlags,
                             ModuleEntry... opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageDesc opens, int opensFlags,
                             List<ModuleDesc> opensTo) {
        return of(TemporaryConstantPool.INSTANCE.packageEntry(TemporaryConstantPool.INSTANCE.utf8Entry(opens.internalName())),
                opensFlags,
                Util.moduleEntryList(opensTo));
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageDesc opens, Collection<AccessFlag> opensFlags,
                             List<ModuleDesc> opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageDesc opens,
                             int opensFlags,
                             ModuleDesc... opensTo) {
        return of(opens, opensFlags, List.of(opensTo));
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, if it is a qualified open
     */
    static ModuleOpenInfo of(PackageDesc opens,
                             Collection<AccessFlag> opensFlags,
                             ModuleDesc... opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }
}
