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
package org.talend.repository.model.migration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.talend.core.model.components.ModifyComponentsAction;
import org.talend.core.model.components.conversions.IComponentConversion;
import org.talend.core.model.components.filters.NameComponentFilter;
import org.talend.core.model.migration.AbstractJobMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.designer.core.model.utils.emf.talendfile.ColumnType;
import org.talend.designer.core.model.utils.emf.talendfile.MetadataType;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;
import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;

/**
 *
 * @author kcoepeau
 *
 * Migrate tImpalaOutput schema date pattern to use "yyyy-MM-dd"
 *
 */
public class AddSchemaDatePatternUseForImpalaTask extends AbstractJobMigrationTask {

    @Override
    public List<ERepositoryObjectType> getTypes() {
        List<ERepositoryObjectType> toReturn = new ArrayList<ERepositoryObjectType>();
        toReturn.add(ERepositoryObjectType.PROCESS);
        return toReturn;
    }

    @Override
    public ExecutionResult execute(Item item) {
        ProcessType processType = getProcessType(item);
        if(processType == null){
            return ExecutionResult.NOTHING_TO_DO;
        }

        String componentName = "tImpalaOutput";

        try {
            boolean modified = ModifyComponentsAction.searchAndModify(item, processType, new NameComponentFilter(componentName),
                    Arrays
                    .<IComponentConversion> asList(new IComponentConversion() {

                        public void transform(NodeType node) {

                            for(Object om : node.getMetadata()){
                                MetadataType metadata = (MetadataType) om;
                                for(Object oc : metadata.getColumn()){
                                    ColumnType column = (ColumnType) oc;
                                    if(column.getType().equals("id_Date") && column.getSourceType().equals("TIMESTAMP")){
                                        column.setPattern("\"yyyy-MM-dd\"");
                                    }
                                }
                            }
                        }

                    }));
            if (modified) {
                return ExecutionResult.SUCCESS_NO_ALERT;
            } else {
                return ExecutionResult.NOTHING_TO_DO;
            }
        } catch (Exception e) {
            return ExecutionResult.FAILURE;
        }
    }

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2018, 1, 18, 18, 0, 0);
        return gc.getTime();
    }

}
