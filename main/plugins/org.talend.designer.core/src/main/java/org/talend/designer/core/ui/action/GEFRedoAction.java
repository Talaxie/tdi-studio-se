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
package org.talend.designer.core.ui.action;

import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.talend.designer.core.ui.editor.connections.ConnLabelEditPart;
import org.talend.designer.core.ui.editor.nodes.NodeLabelEditPart;
import org.talend.designer.core.ui.editor.notes.NoteEditPart;

/**
 * DOC Talaxie class global comment. Detailled comment
 */
public class GEFRedoAction extends SelectionAction {

    /**
     * DOC Talaxie GEFRedoAction constructor comment.
     *
     * @param part
     */
    public GEFRedoAction(IWorkbenchPart part) {
        super(part);
        setId(ActionFactory.REDO.getId());
        setText("Redo"); //$NON-NLS-1$
        ISharedImages sharedImages = part.getSite().getWorkbenchWindow().getWorkbench().getSharedImages();
        setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
        setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_REDO_DISABLED));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
     */
    @Override
    protected boolean calculateEnabled() {
        List objects = getSelectedObjects();
        if (!objects.isEmpty()) {
            for (Object o : objects) {
                if (o instanceof ConnLabelEditPart) {
                    return false;
                }
                if (o instanceof NoteEditPart) {
                    return false;
                }
                if (o instanceof NodeLabelEditPart) {
                    return false;
                }
            }
        }
        return getCommandStack().canRedo();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        getCommandStack().redo();
    }

}
