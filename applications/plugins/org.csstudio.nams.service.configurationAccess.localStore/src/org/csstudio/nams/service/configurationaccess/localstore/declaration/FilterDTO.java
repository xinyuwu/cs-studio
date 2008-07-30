package org.csstudio.nams.service.configurationaccess.localstore.declaration;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csstudio.nams.service.configurationaccess.localstore.Mapper;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.FilterAction2FilterDTO;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.FilterConditionDTO;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.filterConditionSpecifics.FilterConditionsToFilterDTO;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.filterConditionSpecifics.HasManuallyJoinedElements;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.filterConditionSpecifics.JunctorConditionForFilterTreeDTO;
import org.csstudio.nams.service.configurationaccess.localstore.internalDTOs.filterConditionSpecifics.NegationConditionForFilterTreeDTO;

/**
 * Dieses Daten-Transfer-Objekt stellt hält die Konfiguration eines Filters dar
 * 
 * Das Create-Statement für die Datenbank hat folgendes Aussehen:
 * 
 * <pre>
 * Create table AMS_Filter
 * 
 * iFilterID		INT,
 * iGroupRef		INT default -1 NOT NULL,
 * cName			VARCHAR(128),
 * cDefaultMessage	VARCHAR(1024),
 * PRIMARY KEY (iFilterID)
 * ;
 * </pre>
 */
@Entity
@Table(name = "AMS_Filter")
public class FilterDTO implements NewAMSConfigurationElementDTO,
		HasManuallyJoinedElements {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "iFilterID")
	private int iFilterID; // INT,

	@Column(name = "iGroupRef", nullable = false)
	private int iGroupRef = -1; // INT default -1 NOT NULL,

	@Column(name = "cName", length = 128)
	private String name; // VARCHAR(128),

	@Column(name = "cDefaultMessage", length = 1024)
	private String defaultMessage; // VARCHAR(1024),

	@Transient
	private List<FilterConditionDTO> filterConditons = new LinkedList<FilterConditionDTO>();

	@Transient
	private final List<FilterActionDTO> filterActions = new LinkedList<FilterActionDTO>();

	public void deleteJoinLinkData(final Mapper mapper) throws Throwable {
		final List<FilterConditionsToFilterDTO> joins = mapper.loadAll(
				FilterConditionsToFilterDTO.class, true);

		for (final FilterConditionsToFilterDTO fctf : joins) {
			if (fctf.getIFilterRef() == this.iFilterID) {
				mapper.delete(fctf);
			}
		}

		for (final FilterConditionDTO condition : this.getFilterConditions()) {
			if ((condition instanceof JunctorConditionForFilterTreeDTO)
					|| (condition instanceof NegationConditionForFilterTreeDTO)) {
				final FilterConditionDTO foundFC = mapper.findForId(
						FilterConditionDTO.class, condition
								.getIFilterConditionID(), true);
				((HasManuallyJoinedElements) foundFC)
						.deleteJoinLinkData(mapper);

				mapper.delete(foundFC);
			}
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FilterDTO)) {
			return false;
		}
		final FilterDTO other = (FilterDTO) obj;
		if (this.defaultMessage == null) {
			if (other.defaultMessage != null) {
				return false;
			}
		} else if (!this.defaultMessage.equals(other.defaultMessage)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.iFilterID != other.iFilterID) {
			return false;
		}
		if (this.iGroupRef != other.iGroupRef) {
			return false;
		}
		return true;
	}

	public String getDefaultMessage() {
		return this.defaultMessage;
	}

	public List<FilterActionDTO> getFilterActions() {
		return this.filterActions;
	}

	public List<FilterConditionDTO> getFilterConditions() {
		return this.filterConditons;
	}

	public int getIFilterID() {
		return this.iFilterID;
	}

	/**
	 * Kategorie
	 */
	public int getIGroupRef() {
		return this.iGroupRef;
	}

	public String getName() {
		return this.name;
	}

	public String getUniqueHumanReadableName() {
		return this.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.name == null) ? 0 : this.name.hashCode());
		result = prime
				* result
				+ ((this.defaultMessage == null) ? 0 : this.defaultMessage
						.hashCode());
		result = prime * result + this.iFilterID;
		result = prime * result + this.iGroupRef;
		return result;
	}

	public boolean isInCategory(final int categoryDBId) {
		return false;
	}

	public void loadJoinData(final Mapper mapper) throws Throwable {
		final List<FilterConditionsToFilterDTO> joins = mapper.loadAll(
				FilterConditionsToFilterDTO.class, true);

		final List<FilterActionDTO> alleFilterActions = mapper.loadAll(
				FilterActionDTO.class, true);
		final List<FilterAction2FilterDTO> actionJoins = mapper.loadAll(
				FilterAction2FilterDTO.class, true);
		Collections.sort(actionJoins, new Comparator<FilterAction2FilterDTO>() {
			public int compare(final FilterAction2FilterDTO o1,
					final FilterAction2FilterDTO o2) {
				return o2.getIPos() - o1.getIPos();
			}
		});

		final Map<Integer, FilterActionDTO> filterActionMap = new HashMap<Integer, FilterActionDTO>();
		for (final FilterActionDTO filterAction : alleFilterActions) {
			filterActionMap
					.put(filterAction.getIFilterActionID(), filterAction);
		}

		this.filterConditons.clear();

		for (final FilterConditionsToFilterDTO join : joins) {
			if (join.getIFilterRef() == this.getIFilterID()) {
				final FilterConditionDTO gefunden = mapper.findForId(
						FilterConditionDTO.class,
						join.getIFilterConditionRef(), true);
				assert gefunden != null : "Es existiert eine FC mit der ID "
						+ join.getIFilterConditionRef();

				this.filterConditons.add(gefunden);
			}
		}

		this.filterActions.clear();

		for (final FilterAction2FilterDTO actionJoin : actionJoins) {
			if (actionJoin.getId().getIFilterRef() == this.getIFilterID()) {
				final FilterActionDTO foundAction = filterActionMap
						.get(actionJoin.getId().getIFilterActionRef());
				assert foundAction != null : "Es existiert eine Action mit der ID "
						+ actionJoin.getId().getIFilterActionRef();

				this.filterActions.add(foundAction);
			}
		}
	}

	public void setDefaultMessage(final String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public void setFilterConditions(
			final List<FilterConditionDTO> filterConditonDTOs) {
		this.filterConditons = filterConditonDTOs;
	}

	@SuppressWarnings("unused")
	public void setIGroupRef(final int groupRef) {
		this.iGroupRef = groupRef;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void storeJoinLinkData(final Mapper mapper) throws Throwable {
		final List<FilterConditionsToFilterDTO> joins = mapper.loadAll(
				FilterConditionsToFilterDTO.class, true);

		final List<FilterConditionDTO> ehemalsReferenziert = new LinkedList<FilterConditionDTO>();

		for (final FilterConditionsToFilterDTO join : joins) {
			if (join.getIFilterRef() == this.getIFilterID()) {
				final FilterConditionDTO found = mapper.findForId(
						FilterConditionDTO.class,
						join.getIFilterConditionRef(), true);
				ehemalsReferenziert.add(found);
			}
		}

		final List<FilterConditionDTO> operands = this.getFilterConditions();

		for (final FilterConditionDTO operand : operands) {
			final FilterConditionDTO fc = mapper.findForId(
					FilterConditionDTO.class, operand.getIFilterConditionID(),
					true);

			if (fc != null) {
				if (!ehemalsReferenziert.remove(fc)) {
					final FilterConditionsToFilterDTO newJoin = new FilterConditionsToFilterDTO(
							this.getIFilterID(), fc.getIFilterConditionID());
					mapper.save(newJoin);
				}
				if ((operand instanceof JunctorConditionForFilterTreeDTO)
						|| (operand instanceof NegationConditionForFilterTreeDTO)) {
					((HasManuallyJoinedElements) operand)
							.storeJoinLinkData(mapper);
				}
			} else {
				mapper.save(operand);
				final FilterConditionsToFilterDTO newJoin = new FilterConditionsToFilterDTO(
						this.getIFilterID(), operand.getIFilterConditionID());
				mapper.save(newJoin);
			}
		}

		for (final FilterConditionDTO toRemove : ehemalsReferenziert) {
			final FilterConditionsToFilterDTO found = this.findForId(toRemove
					.getIFilterConditionID(), joins);
			mapper.delete(found);
			if (toRemove instanceof JunctorConditionForFilterTreeDTO) {
				mapper.delete(toRemove);
			}
			if (toRemove instanceof NegationConditionForFilterTreeDTO) {
				mapper.delete(toRemove);
			}
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass()
				.getSimpleName());
		builder.append(": ");
		builder.append("iFilterID: ");
		builder.append(this.iFilterID);
		builder.append(", iGroupRef: ");
		builder.append(this.iGroupRef);
		builder.append(", cName: ");
		builder.append(this.name);
		return builder.toString();
	}

	private FilterConditionsToFilterDTO findForId(final int id,
			final Collection<FilterConditionsToFilterDTO> fcs) {
		for (final FilterConditionsToFilterDTO t : fcs) {
			if (t.getIFilterConditionRef() == id) {
				return t;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void setIFilterID(final int filterID) {
		this.iFilterID = filterID;
	}

}
