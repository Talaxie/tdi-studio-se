/**
 * Copyright (C) 2006-2021 Talaxie Inc. - www.deilink.fr
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.talend.sdk.component.studio.ui.composite.generator;

import org.talend.designer.core.ui.editor.properties.controllers.AbstractElementPropertySectionController;
import org.talend.sdk.component.studio.ui.composite.controller.TaCoKitGuessSchemaController;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class TaCoKitGuessSchemaGenerator extends AbstractTaCoKitGenerator {

    @Override
    public AbstractElementPropertySectionController generate() {
        return new TaCoKitGuessSchemaController(getDynamicProperty());
    }

}
