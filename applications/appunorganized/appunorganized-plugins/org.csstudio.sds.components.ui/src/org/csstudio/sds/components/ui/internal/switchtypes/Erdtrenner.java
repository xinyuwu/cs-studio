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
 package org.csstudio.sds.components.ui.internal.switchtypes;

import org.csstudio.sds.components.common.CosySwitch;
import org.csstudio.sds.components.ui.internal.utils.Trigonometry;
import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.SWT;

/**
 * The Erdtrenner switch type.
 *
 * @author jbercic
 *
 */
public final class Erdtrenner extends CosySwitch {

    /**
     * The default constructor.
     */
    public Erdtrenner() {
    }

    /**
     * {@inheritDoc}
     */
    protected void paintBase(final Graphics gfx, final int width, final int height) {
        int wireHeight=(int)Math.round((double)height*0.82);
        gfx.drawLine(width/2,0,width/2,wireHeight/3);
        gfx.drawLine(width/2,2*wireHeight/3,width/2,wireHeight);
        //base three lines
        gfx.drawLine(width/4,wireHeight,3*width/4,wireHeight);
        gfx.drawLine(width/4+width/16,wireHeight+(height-wireHeight)/2,
                3*width/4-width/16,wireHeight+(height-wireHeight)/2);
        gfx.drawLine(width/4+width/8,height,
                3*width/4-width/8,height);
    }

    /**
     * {@inheritDoc}
     */
    protected void paintOpenState(final Graphics gfx, final int width, final int height) {
        int wireHeight=(int)Math.round((double)height*0.82);
        gfx.drawLine(width/2,2*wireHeight/3,
                width/2+(int)(wireHeight/3*Trigonometry.cos(120.0)),
                2*wireHeight/3-(int)(wireHeight/3*Trigonometry.sin(120.0)));
    }

    /**
     * {@inheritDoc}
     */
    protected void paintDashedOpenState(final Graphics gfx, final int width, final int height) {
        int wireHeight=(int)Math.round((double)height*0.82);
        gfx.setLineStyle(SWT.LINE_DOT);
        gfx.drawLine(width/2,2*wireHeight/3,
                width/2+(int)(wireHeight/3*Trigonometry.cos(120.0)),
                2*wireHeight/3-(int)(wireHeight/3*Trigonometry.sin(120.0)));
        gfx.setLineStyle(SWT.LINE_SOLID);
    }

    /**
     * {@inheritDoc}
     */
    protected void paintClosedState(final Graphics gfx, final int width, final int height) {
        int wireHeight=(int)Math.round((double)height*0.82);
        gfx.drawLine(width/2,2*wireHeight/3,width/2,wireHeight/3);
        gfx.drawLine(width/4+width/8,wireHeight/3,
                3*width/4-width/8,wireHeight/3);
        gfx.fillOval(width/2-this.getLineWidth()*2,2*wireHeight/3-this.getLineWidth()*2,
                this.getLineWidth()*4,this.getLineWidth()*4);
    }

}
