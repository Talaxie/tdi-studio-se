package org.talend.designer.core.ui.action;

import java.util.List;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.talend.core.model.components.ComponentCategory;
import org.talend.designer.core.i18n.Messages;
import org.talend.designer.core.ui.editor.cmd.DisableParallelizationCommand;
import org.talend.designer.core.ui.editor.nodecontainer.NodeContainer;
import org.talend.designer.core.ui.editor.nodecontainer.NodeContainerPart;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.nodes.NodePart;
import org.talend.designer.core.ui.editor.subjobcontainer.SubjobContainer;
import org.talend.designer.core.ui.editor.subjobcontainer.SubjobContainerPart;
import org.talend.designer.core.utils.ParallelExecutionUtils;

public class DisableParallelizationAction extends SelectionAction {

    public static final String ID = "org.talend.designer.core.ui.editor.action.DisableParallelizationAction"; //$NON-NLS-1$

    IWorkbenchPart part;

    public DisableParallelizationAction(IWorkbenchPart part) {
        super(part);
        this.part = part;
        setId(ID);
        setText(Messages.getString("PropertiesContextAction.disableParallelization"));
    }

    @Override
    protected boolean calculateEnabled() {
        List parts = getSelectedObjects();
        if (parts.isEmpty()) {
            return false;
        }
        if (parts.size() == 1) {
            Object o = parts.get(0);
            if (o instanceof SubjobContainerPart) {
                SubjobContainerPart part = (SubjobContainerPart) o;
                SubjobContainer subjob = (SubjobContainer) part.getModel();
                Node subStartNode = subjob.getSubjobStartNode();
                if (subjob.getProcess().getComponentsType().equals(ComponentCategory.CATEGORY_4_DI.getName())) {
                    if (subjob.isDisplayed() && ParallelExecutionUtils.isExistPartitioningCon(subStartNode)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (o instanceof NodePart) {
                NodePart part = (NodePart) o;
                Node node = (Node) part.getModel();
                if (node.getProcess().getComponentsType().equals(ComponentCategory.CATEGORY_4_DI.getName())) {
                    if (node.isStart() && ParallelExecutionUtils.isExistPartitioningCon(node)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void run() {
        List editparts = getSelectedObjects();
        if (editparts.size() == 1) {
            Object o = editparts.get(0);
            if (o instanceof NodePart) {
                NodePart part = (NodePart) o;
                Node node = (Node) part.getModel();
                getCommandStack().execute(new DisableParallelizationCommand(node));
            } else if (o instanceof SubjobContainerPart) {
                boolean hasStartNode = false;
                List<? extends GraphicalEditPart> childNodes = ((SubjobContainerPart) o).getChildren();
                for (GraphicalEditPart childNode : childNodes) {
                    NodeContainerPart part = (NodeContainerPart) childNode;
                    NodeContainer node = (NodeContainer) part.getModel();
                    if (node.getNode().isStart()) {
                        hasStartNode = true;
                        getCommandStack().execute(new DisableParallelizationCommand(node.getNode()));
                    }
                }
                if (!hasStartNode) {
                    for (GraphicalEditPart childNode : childNodes) {
                        NodeContainerPart part = (NodeContainerPart) childNode;
                        NodeContainer node = (NodeContainer) part.getModel();
                        if (node.getNode().isSubProcessStart()) {
                            getCommandStack().execute(new DisableParallelizationCommand(node.getNode()));
                        }
                    }
                }
            }
        }
    }
}
