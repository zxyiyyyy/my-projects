/*
 * Copyright (c) 1998, 2023, Oracle and/or its affiliates. All rights reserved.
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

package java.security.interfaces;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/**
 * The interface to an RSA private key.
 *
 * @author Jan Luehe
 * @since 1.2
 *
 *
 * @see RSAPrivateCrtKey
 */

public interface RSAPrivateKey extends java.security.PrivateKey, RSAKey
{

    /**
     * The type fingerprint that is set to indicate
     * serialization compatibility with a previous
     * version of the type.
     *
     * @deprecated A {@code serialVersionUID} field in an interface is
     * ineffectual. Do not use; no replacement.
     */
    @Deprecated
    @SuppressWarnings("serial")
    @java.io.Serial
    long serialVersionUID = 5187144804936595022L;

    /**
     * Returns the private exponent.
     *
     * @return the private exponent
     */
    BigInteger getPrivateExponent();

    /**
     * {@inheritDoc java.security.AsymmetricKey}
     *
     * @implSpec
     * The default implementation returns {@code null}.
     *
     * @return {@inheritDoc java.security.AsymmetricKey}
     * @since 22
     */
    @Override
    default AlgorithmParameterSpec getParams() {
        return null;
    }
}
