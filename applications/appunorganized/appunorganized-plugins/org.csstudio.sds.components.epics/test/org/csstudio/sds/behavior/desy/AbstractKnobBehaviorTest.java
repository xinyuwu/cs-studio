/*
 * Copyright (c) 2011 Stiftung Deutsches Elektronen-Synchrotron,
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
package org.csstudio.sds.behavior.desy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.csstudio.sds.components.model.KnobModel;
import org.csstudio.sds.eventhandling.AbstractBehavior;
import org.csstudio.sds.eventhandling.AbstractBehaviorTest;
import org.csstudio.sds.model.AbstractWidgetModel;

/**
 * @author hrickens
 * @since 26.10.2011
 */
public abstract class AbstractKnobBehaviorTest<B extends AbstractBehavior<KnobModel>> extends AbstractBehaviorTest<KnobModel, B> {


    @Override
    protected KnobModel createModelMock() {
        final KnobModel mock = mock(KnobModel.class);
        when(mock.getColor(KnobModel.PROP_KNOB_COLOR)).thenReturn("UserDefinedColor");
        return mock;
    }


    @Override
    protected void verifyDoInitialize() {
        getInOrder().verify(getModelMock()).setPropertyValue(AbstractWidgetModel.PROP_COLOR_BACKGROUND,"${Initial}");
//        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${Initial}");
    }

    @Override
    protected void verifyConnectionStateConnectedWithData() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"UserDefinedColor");
    }

    @Override
    protected void verifyValueChangeConnectedWithData() {
    }

    @Override
    protected void verifyConnectionStateConnectedWithoutData() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${Invalid}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verifyValueChangeConnectedWithoutData() {
    }

    @Override
    protected void verifyConnectionStateConnecting() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${Initial}");
    }

    @Override
    protected void verifyConnectionStateFailed() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${VerbAbbr}");
    }

    @Override
    protected void verifyConnectionStateConnectionLost() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${VerbAbbr}");
    }

    @Override
    protected void verifyConnectionStateDestroyed() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${VerbAbbr}");
    }

    @Override
    protected void verifyConnectionStateDisconnecting() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${VerbAbbr}");
    }

    @Override
    protected void verifyConnectionStateDisconnected() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${VerbAbbr}");
    }

    @Override
    protected void verifyConnectionStateInitial() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${Initial}");
    }

    @Override
    protected void verifyConnectionStateOperational() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"UserDefinedColor");
    }

    @Override
    protected void verifyValueChangeOperational() {
    }

    @Override
    protected void verifyConnectionStateReady() {
        getInOrder().verify(getModelMock()).setPropertyValue(KnobModel.PROP_KNOB_COLOR,"${Initial}");
    }

}
