package org.csstudio.nams.service.configurationaccess.localstore.declaration;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csstudio.nams.service.configurationaccess.localstore.declaration.filterActions.FilterActionType;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.filterConditionSpecifics.HasManuallyJoinedElements;

@Entity
@Table(name = "AMS_FILTERACTION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IFILTERACTIONTYPEREF", discriminatorType = DiscriminatorType.INTEGER)
public abstract class FilterActionDTO implements NewAMSConfigurationElementDTO,
		HasManuallyJoinedElements {

	/**
	 * Entweder TOPIC, Alarmbearbeiter, AlarmbearbeiterGruppe
	 */
	@Transient
	protected NewAMSConfigurationElementDTO receiver;

	@Transient
	protected FilterActionType filterActionType;

	@Id
	@Column(name = "IFILTERACTIONID", nullable = false)
	private int iFilterActionID;

	@Column(name = "IRECEIVERREF", nullable = false)
	private int iReceiverRef;

	@Column(name = "CMESSAGE", length = 1024)
	private String message;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FilterActionDTO)) {
			return false;
		}
		final FilterActionDTO other = (FilterActionDTO) obj;
		if (this.iFilterActionID != other.iFilterActionID) {
			return false;
		}
		if (this.iReceiverRef != other.iReceiverRef) {
			return false;
		}
		if (this.message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!this.message.equals(other.message)) {
			return false;
		}
		return true;
	}

	@Deprecated
	public FilterActionType getFilterActionType() {
		if (this.filterActionType == null) {
			throw new RuntimeException("filterActionType in " + this
					+ " not set");
		}
		return this.filterActionType;
	}

	public int getIFilterActionID() {
		return this.iFilterActionID;
	}

	public String getMessage() {
		return this.message;
	}

	public String getUniqueHumanReadableName() {
		return this.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.iFilterActionID;
		result = prime * result + this.iReceiverRef;
		result = prime * result
				+ ((this.message == null) ? 0 : this.message.hashCode());
		return result;
	}

	public boolean isInCategory(final int categoryDBId) {
		return false;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ ": Nachricht an Topic mit id: " + this.receiver;
	}

	protected int getIReceiverRef() {
		return this.iReceiverRef;
	}

	protected void setIReceiverRef(final int receiverRef) {
		this.iReceiverRef = receiverRef;
	}
}
