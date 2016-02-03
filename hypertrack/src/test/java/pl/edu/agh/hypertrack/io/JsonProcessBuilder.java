package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pl.edu.agh.hypertrack.model.HyperflowProcessType;

public class JsonProcessBuilder {

	private String processName;
	private HyperflowProcessType processType = HyperflowProcessType.DATAFLOW;
	private Collection<String> inputSignals;
	private Collection<String> outputSignals;
	private Map<String, String> properties = new HashMap<>();
	
	private JsonProcessBuilder() {}
	
	public static JsonProcessBuilder aJsonProcess() {
		return new JsonProcessBuilder();
	}
	
	public JsonProcess build() {
		JsonProcess jsonProcess = new JsonProcess(processName, processType, inputSignals, outputSignals);
		//TODO: change initialization of properties
		for (Entry<String, String> property: properties.entrySet()) {
			jsonProcess.setProperty(property.getKey(), property.getValue());
		}
		return jsonProcess;
	}
	
	public JsonProcessBuilder withProcessName(String processName) {
		this.processName = processName;
		return this;
	}
	
	public JsonProcessBuilder withProcessType(HyperflowProcessType processType) {
		this.processType = processType;
		return this;
	}
	
	public JsonProcessBuilder withInputSignals(String ...inputSignals) {
		this.inputSignals = asList(inputSignals);
		return this;
	}
	
	public JsonProcessBuilder withOutputSignals(String outputSignals) {
		this.outputSignals = asList(outputSignals);
		return this;
	}
	
	public JsonProcessBuilder withProperty(String name, String value) {
		this.properties.put(name, value);
		return this;
	}
}
