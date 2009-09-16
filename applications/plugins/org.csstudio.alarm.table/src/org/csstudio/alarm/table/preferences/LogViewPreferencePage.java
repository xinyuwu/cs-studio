/* 
 * Copyright (c) 2008 Stiftung Deutsches Elektronen-Synchrotron, 
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS. 
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND 
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE 
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR 
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. 
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, 
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION, 
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS 
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY 
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */
package org.csstudio.alarm.table.preferences;

import org.csstudio.alarm.table.JmsLogsPlugin;
import org.csstudio.alarm.table.internal.localization.Messages;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference page for log view. 1. Table for column names and width 2. Number
 * of maximum rows of log table 3. Table for sets of monitored topics
 * 
 */
public class LogViewPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public LogViewPreferencePage() {
		super(GRID);
		setPreferenceStore(JmsLogsPlugin.getDefault().getPreferenceStore());
		setDescription(Messages.columnNamesMessageKeys);
	}

	public void createFieldEditors() {

		addField(new ListEditor(
				LogViewPreferenceConstants.P_STRING,
				LogViewPreferenceConstants.P_STRING + ": ", getFieldEditorParent()) { //$NON-NLS-1$

			public String[] parseString(String stringList) {
				return stringList.split(";"); //$NON-NLS-1$
			}

			public String getNewInputObject() {
				InputDialog inputDialog = new InputDialog(
						getFieldEditorParent().getShell(),
						Messages.newColumnName, Messages.column, "", null); //$NON-NLS-1$
				if (inputDialog.open() == Window.OK) {
					return inputDialog.getValue();
				}
				return null;
			}

			public String createList(String[] items) {
				String temp = ""; //$NON-NLS-1$
				for (int i = 0; i < items.length; i++) {
					temp = temp + items[i] + ";"; //$NON-NLS-1$
				}
				return temp;
			}
		});
		addField(new FontFieldEditor(AlarmViewPreferenceConstants.LOG_TABLE_FONT, "Table Font", "Major", getFieldEditorParent()));
		Group g1 = new Group(getFieldEditorParent(), SWT.NONE);
		g1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
		
		addField(new StringFieldEditor(LogViewPreferenceConstants.MAX,
				LogViewPreferenceConstants.MAX + ": ", g1)); //$NON-NLS-1$
		addField(new PreferenceTableEditor(
				LogViewPreferenceConstants.TOPIC_SET, "&Topic Sets: ",
				getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
}