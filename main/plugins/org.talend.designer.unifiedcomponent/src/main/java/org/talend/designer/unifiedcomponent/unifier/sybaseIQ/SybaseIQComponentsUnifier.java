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
package org.talend.designer.unifiedcomponent.unifier.sybaseIQ;

import org.talend.core.database.EDatabaseTypeName;
import org.talend.designer.unifiedcomponent.unifier.AbstractComponentsUnifier;

/**
 * created by wchen on Dec 1, 2017 Detailled comment
 *
 */
public class SybaseIQComponentsUnifier extends AbstractComponentsUnifier {

    /*
     * (non-Javadoc)
     *
     * @see org.talend.designer.unifiedcomponent.unifier.IComponentsUnifier#getDatabase()
     */
    @Override
    public String getDisplayName() {
        return EDatabaseTypeName.SYBASEIQ.getDisplayName();
    }
}
