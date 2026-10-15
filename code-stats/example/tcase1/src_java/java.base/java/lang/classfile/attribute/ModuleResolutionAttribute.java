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
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.javac.PreviewFeature;

/**
 * Models the {@code ModuleResolution} attribute, which can
 * appear on classes that represent module descriptors.  This is a JDK-specific
 *  * attribute, which captures resolution metadata for modules.
 * Delivered as a {@link java.lang.classfile.ClassElement} when
 * traversing the elements of a {@link java.lang.classfile.ClassModel}.
 *
 *  <p>The specification of the {@code ModuleResolution} attribute is:
 * <pre> {@code
 *  ModuleResolution_attribute {
 *    u2 attribute_name_index;    // "ModuleResolution"
 *    u4 attribute_length;        // 2
 *    u2 resolution_flags;
 *
 *  The value of the resolution_flags item is a mask of flags used to denote
 *  properties of module resolution. The flags are as follows:
 *
 *   // Optional
 *   0x0001 (DO_NOT_RESOLVE_BY_DEFAULT)
 *
 *   // At most one of:
 *   0x0002 (WARN_DEPRECATED)
 *   0x0004 (WARN_DEPRECATED_FOR_REMOVAL)
 *   0x0008 (WARN_INCUBATING)
 *  }
 * } </pre>
 * <p>
 * The attribute does not permit multiple instances in a given location.
 * Subsequent occurrence of the attribute takes precedence during the attributed
 * element build or transformation.
 *
 * @since 22
 */
@PreviewFeature(feature = PreviewFeature.Feature.CLASSFILE_API)
public sealed interface ModuleResolutionAttribute
        extends Attribute<ModuleResolutionAttribute>, ClassElement
        permits BoundAttribute.BoundModuleResolutionAttribute, UnboundAttribute.UnboundModuleResolutionAttribute {

    /**
     *  The value of the resolution_flags item is a mask of flags used to denote
     *  properties of module resolution. The flags are as follows:
     * <pre> {@code
     *   // Optional
     *   0x0001 (DO_NOT_RESOLVE_BY_DEFAULT)
     *
     *   // At most one of:
     *   0x0002 (WARN_DEPRECATED)
     *   0x0004 (WARN_DEPRECATED_FOR_REMOVAL)
     *   0x0008 (WARN_INCUBATING)
     *  } </pre>
     * @return the module resolution flags
     */
    int resolutionFlags();

    /**
     * {@return a {@code ModuleResolution} attribute}
     * @param resolutionFlags the resolution flags
     */
    static ModuleResolutionAttribute of(int resolutionFlags) {
        return new UnboundAttribute.UnboundModuleResolutionAttribute(resolutionFlags);
    }
}
