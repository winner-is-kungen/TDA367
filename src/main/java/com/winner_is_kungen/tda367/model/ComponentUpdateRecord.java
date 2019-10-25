package com.winner_is_kungen.tda367.model;

public class ComponentUpdateRecord {
	private final String componentID;
	private final int channel;

	ComponentUpdateRecord(String componentID, int channel) {
		this.componentID = componentID;
		this.channel = channel;
	}

	public String getComponentID() {
		return componentID;
	}

	public int getChannel() {
		return channel;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		else if (other instanceof ComponentUpdateRecord) {
			ComponentUpdateRecord otherRecord = (ComponentUpdateRecord)other;
			return getComponentID().equals(otherRecord.getComponentID()) && getChannel() == otherRecord.getChannel();
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getComponentID().hashCode() + getChannel();
	}
}