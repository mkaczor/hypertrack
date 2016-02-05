package pl.edu.agh.hypertrack.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class HypertrackEntityKey {
	
	private String workflowName;
	private String entityName;
	
	public HypertrackEntityKey(String workflowName, String entityName) {
		this.workflowName = workflowName;
		this.entityName = entityName;
	}
	
	public String getWorkflowName() {
		return workflowName;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
}
