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

package java.security;

import java.security.spec.AlgorithmParameterSpec;

/**
 * An asymmetric key, which can be either a public key or a private key.
 * This interface contains methods that are common to either a public key or
 * a private key.
 *
 * @since 22
 */
public interface AsymmetricKey extends Key {
    /**
     * Returns the parameters associated with this key.
     * The parameters are optional and may be either
     * explicitly specified or implicitly created during
     * key pair generation.
     *
     * @implSpec
     * The default implementation returns {@code null}.
     *
     * @return the associated parameters, may be {@code null}
     */
    default AlgorithmParameterSpec getParams() {
        return null;
    }
}
