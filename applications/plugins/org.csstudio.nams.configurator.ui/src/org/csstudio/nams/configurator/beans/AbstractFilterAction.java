package org.csstudio.nams.configurator.beans;

import org.csstudio.nams.service.configurationaccess.localstore.declaration.filterActions.FilterActionType;

public abstract class AbstractFilterAction<Type extends FilterActionType>
		implements FilterAction {

	protected String message = "";
	protected Type type;
	protected IReceiverBean receiver;
	protected final Class<Type> clazz;

	protected AbstractFilterAction(final Class<Type> clazz) {
		this.clazz = clazz;
	}

	@Override
	public abstract Object clone() throws CloneNotSupportedException;

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final AbstractFilterAction<Type> other = (AbstractFilterAction<Type>) obj;
		if (this.clazz == null) {
			if (other.clazz != null) {
				return false;
			}
		} else if (!this.clazz.equals(other.clazz)) {
			return false;
		}
		if (this.message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!this.message.equals(other.message)) {
			return false;
		}
		if (this.receiver == null) {
			if (other.receiver != null) {
				return false;
			}
		} else if (!this.receiver.equals(other.receiver)) {
			return false;
		}
		if (this.type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!this.type.equals(other.type)) {
			return false;
		}
		return true;
	}

	public String getEmpfaengerName() {
		return this.receiver.getDisplayName();
	}

	public FilterActionType getFilterActionType() {
		return this.type;
	}

	public String getMessage() {
		return this.message;
	}

	public IReceiverBean getReceiver() {
		return this.receiver;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.clazz == null) ? 0 : this.clazz.hashCode());
		result = prime * result
				+ ((this.message == null) ? 0 : this.message.hashCode());
		result = prime * result
				+ ((this.receiver == null) ? 0 : this.receiver.hashCode());
		result = prime * result
				+ ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setReceiver(final IReceiverBean receiver) {
		this.receiver = receiver;
	}

	@SuppressWarnings("unchecked")
	public void setType(final FilterActionType type) {
		if (this.clazz.isInstance(type)) {
			this.type = (Type) type;
		} else {
			throw new IllegalArgumentException(
					"FilterAction does not support FilterActionType "
							+ type.getClass().getSimpleName());
		}
	}
}
