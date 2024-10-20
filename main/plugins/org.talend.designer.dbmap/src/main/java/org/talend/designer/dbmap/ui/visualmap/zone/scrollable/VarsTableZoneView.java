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
package org.talend.designer.dbmap.ui.visualmap.zone.scrollable;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.talend.designer.dbmap.managers.MapperManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 *
 * $Id: VarsTableZoneView.java 968 2006-12-12 10:59:26Z amaumont $
 *
 */
public class VarsTableZoneView extends TablesZoneView {

    public VarsTableZoneView(Composite parent, int style, MapperManager mapperManager) {
        super(parent, style, mapperManager);
    }

    @Override
    public Layout initLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.marginLeft = 0;
        formLayout.marginRight = 0;
        formLayout.marginTop = 7;
        formLayout.marginBottom = 10;
        formLayout.marginWidth = 0;
        formLayout.marginHeight = 0;
        formLayout.spacing = 10;
        setLayout(formLayout);
        return formLayout;
    }

}
