package org.csstudio.trends.databrowser.sampleview;

//import java.text.Format;

import org.csstudio.platform.data.ISeverity;
import org.csstudio.trends.databrowser.model.ModelSample;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/** The JFace label provider for ModelSample data.
 *  @author Kay Kasemir
 *  @author Albert Kagarmanov
 */
public class SampleTableLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider
{
    /** Get text for all but the columns.
     *  @param index: 0, 1, 2 for Time, Value, Info
     */
	public String getColumnText(final Object obj, final int index)
	{
        final TableItem item = (TableItem) obj;
        final ModelSample sample = item.getSample();
        switch (index)
        {
        case 0:
            return sample.getSample().getTime().toString();
        case 1:
            return sample.getSample().format();
        default:
            String info = sample.getInfo();
            return info == null ? "" : info; //$NON-NLS-1$
        }
	}

    /** Get column image */
	public Image getColumnImage(Object obj, int index)
	{
        return null; // no column images
	}

    /** @see org.eclipse.jface.viewers.ITableColorProvider */
    public Color getBackground(final Object obj, final int index)
    {
        final TableItem item = (TableItem) obj;
        final ModelSample sample = item.getSample();
        ISeverity severity = sample.getSample().getSeverity();
        if (severity.isOK())
            return null; // no special color
        // Make entry stand out,
        // using system color that we don't have to dispose
        if (severity.isMinor())
            return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
        if (severity.isMajor())
            return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
        return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
    }

    /** @see org.eclipse.jface.viewers.ITableColorProvider */
    public Color getForeground(Object obj, int index)
    {
        return null;
    }
}
