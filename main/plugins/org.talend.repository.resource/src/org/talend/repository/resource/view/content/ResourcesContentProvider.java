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
package org.talend.repository.resource.view.content;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProjectRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.viewer.content.ProjectRepoDirectChildrenNodeContentProvider;

/**
 *
 * DOC jding class global comment. Detailled comment
 */
public class ResourcesContentProvider extends ProjectRepoDirectChildrenNodeContentProvider {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.repository.viewer.content.ProjectRepoChildrenNodeContentProvider#getTopLevelNodeFromProjectRepositoryNode
     * (org.talend.repository.model.ProjectRepositoryNode)
     */
    @Override
    protected RepositoryNode getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode projectNode) {
		return projectNode
                .getRootRepositoryNode(ERepositoryObjectType.RESOURCES);
    }
}
