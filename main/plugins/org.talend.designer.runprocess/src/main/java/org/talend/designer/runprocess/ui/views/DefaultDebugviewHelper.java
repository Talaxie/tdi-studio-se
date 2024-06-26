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
package org.talend.designer.runprocess.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.components.ComponentCategory;
import org.talend.designer.runprocess.ui.DebugProcessTosComposite;
import org.talend.designer.runprocess.ui.TraceDebugProcessComposite;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class DefaultDebugviewHelper implements IDebugViewHelper {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.designer.runprocess.ui.views.IDebugViewHelper#getDebugComposite(org.eclipse.swt.widgets.Composite)
     */
    public TraceDebugProcessComposite getDebugComposite(Composite container) {
        // TODO Auto-generated method stub
        return new DebugProcessTosComposite(container, SWT.NONE);
    }

    @Override
    public ComponentCategory getDebugType() {
        return ComponentCategory.CATEGORY_4_DI;
    }

}
