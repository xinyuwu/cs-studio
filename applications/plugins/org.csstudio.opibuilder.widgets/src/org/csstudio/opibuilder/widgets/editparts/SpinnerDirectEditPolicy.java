package org.csstudio.opibuilder.widgets.editparts;

import org.csstudio.opibuilder.widgets.model.LabelModel;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

/**
 * The Editpolicy to handle direct text edit in spinner.
 * @author Xihui Chen
 *
 */
public class SpinnerDirectEditPolicy 
	extends DirectEditPolicy {

	/**
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest edit) {
		String labelText = (String)edit.getCellEditor().getValue();
		SpinnerEditpart spinner = (SpinnerEditpart)getHost();
		SpinnerEditCommand command = new SpinnerEditCommand((LabelModel)spinner.getModel(),labelText);
		return command;
	}
	
	/**
	 * @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
		//String value = (String)request.getCellEditor().getValue();
		//((LabelFigure)getHostFigure()).setText(value);
		//hack to prevent async layout from placing the cell editor twice.
		//getHostFigure().getUpdateManager().performUpdate();
		
	
	}


class SpinnerEditCommand extends Command	{
	
	private String newText, oldText;
	private LabelModel label;
	
	public SpinnerEditCommand(LabelModel l, String s) {
	label = l;
	if (s != null)
		newText = s;
	else
		newText = "";  //$NON-NLS-1$
	}
	
	public void execute() {
		oldText = label.getText();
		label.setText(newText);
	}
	
	public void undo() {
		label.setText(oldText);
	}

}


}
