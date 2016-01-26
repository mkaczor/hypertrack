package pl.edu.agh.hypertrack.io;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonSignal {
	
	@JsonProperty("name")
	private String signalName;
	
	@JsonProperty("control")
	private String controlType;
	
	public String getSignalName() {
		return signalName;
	}
	
	public String getControlType() {
		return controlType;
	}
	
}
