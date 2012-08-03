/*******************************************************************************
 * Copyright (c) 2011 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelFactory;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.ui.property.AbstractListComposite;
import org.eclipse.bpmn2.modeler.ui.property.ExtensionValueListComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.DefinitionsPropertyComposite;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.SchemaImportDialog;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmDefinitionsPropertyComposite extends DefinitionsPropertyComposite {

	/**
	 * @param section
	 */
	public JbpmDefinitionsPropertyComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmDefinitionsPropertyComposite(Composite parent, int style) {
		super(parent, style);
	}


	@Override
	public void createBindings(EObject be) {
		super.createBindings(be);

		if (be instanceof Definitions) {
			Definitions definitions = (Definitions)be;
			for (RootElement re : definitions.getRootElements()) {
				if (re instanceof Process) {
					Process process = (Process)re;
					ExtensionValueListComposite importsTable = new ExtensionValueListComposite(
							this,  AbstractListComposite.READ_ONLY_STYLE)
					{
						@Override
						protected EObject addListItem(EObject object, EStructuralFeature feature) {
							String name = JbpmModelUtil.showImportDialog(object);
							return JbpmModelUtil.addImport(name, object);
						}

					};
					importsTable.bindList(process, ModelPackage.eINSTANCE.getDocumentRoot_ImportType());
					importsTable.setTitle("Imports");
				}
			}
		}
	}
}
