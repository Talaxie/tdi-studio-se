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
package org.talend.designer.core.ui.dialog;

import org.apache.commons.collections.BidiMap;
import org.eclipse.swt.widgets.Shell;
import org.talend.designer.core.ui.editor.nodes.Node;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public interface IBrmsExtension {

    BrmsDialog createBrmsDialog(Shell parent);

    BrmsDialog createBrmsDialogForRepository(Shell parent);

    public void initialize(Node node, String propertyName, BidiMap hashCurControls);
}
