// ============================================================================
//
// Copyright (C) 2006-2021 Talaxie Inc. - www.deilink.fr
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talaxie SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.hashfile.common;

public enum MATCHING_MODE {
    KEEP_ALL,
    KEEP_FIRST,
    KEEP_LAST;

    public static MATCHING_MODE parse(String matchingMode) {
        MATCHING_MODE[] multipleMatchingModes = values();
        for (MATCHING_MODE multipleMatchingMode : multipleMatchingModes) {
            if (multipleMatchingMode.toString().equals(matchingMode)) {
                return multipleMatchingMode;
            }
        }
        return null;
    }
}
