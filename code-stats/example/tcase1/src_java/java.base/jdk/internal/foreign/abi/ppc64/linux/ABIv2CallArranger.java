/*
 * Copyright (c) 2021, 2023, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 SAP SE. All rights reserved.
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
package jdk.internal.foreign.abi.ppc64.linux;

import jdk.internal.foreign.abi.ppc64.CallArranger;

/**
 * PPC64 CallArranger specialized for ABI v2.
 */
public class ABIv2CallArranger extends CallArranger {

    @Override
    protected boolean useABIv2() {
        return true;
    }

    @Override
    protected boolean isAIX() {
        return false;
    }
}
