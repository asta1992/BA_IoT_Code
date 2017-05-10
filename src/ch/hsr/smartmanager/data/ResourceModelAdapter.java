package ch.hsr.smartmanager.data;

import org.eclipse.leshan.core.model.ResourceModel;

public class ResourceModelAdapter {

	private ResourceModel resourceModel;
	
	public ResourceModelAdapter(ResourceModel resourceModel) {
		this.resourceModel = resourceModel;
	}

	public int getId() {
		return resourceModel.id;
	}

	public String getName() {
		return resourceModel.name;
	}

	public String getOperations() {
		return resourceModel.operations.name();
	}

	public boolean isMultiple() {
		return resourceModel.multiple;
	}

	public boolean isMandatory() {
		return resourceModel.mandatory;
	}

	public String getType() {
		return resourceModel.type.name();
	}

	public String getRangeEnumeration() {
		return resourceModel.rangeEnumeration;
	}

	public String getUnits() {
		return resourceModel.units;
	}

	public String getDescription() {
		return resourceModel.description;
	}
	

	
	
	

}
