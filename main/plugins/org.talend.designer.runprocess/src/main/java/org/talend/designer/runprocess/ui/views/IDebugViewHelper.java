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

import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.components.ComponentCategory;
import org.talend.designer.runprocess.ui.TraceDebugProcessComposite;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public interface IDebugViewHelper {

    public TraceDebugProcessComposite getDebugComposite(final Composite container);

    public ComponentCategory getDebugType();

}
