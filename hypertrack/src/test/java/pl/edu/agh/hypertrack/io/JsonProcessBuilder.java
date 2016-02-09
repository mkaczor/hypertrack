package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pl.edu.agh.hypertrack.model.HyperflowProcessType;

public class JsonProcessBuilder {

	private String processName = "";
	private HyperflowProcessType processType = HyperflowProcessType.DATAFLOW;
	private Collection<JsonProcessInputSignal> inputSignals = Collections.emptyList();
	private Collection<String> outputSignals = emptyList();
	private Map<String, String> properties = new HashMap<>();
	
	private JsonProcessBuilder() {}
	
	public static JsonProcessBuilder aJsonProcess() {
		return new JsonProcessBuilder();
	}
	
	public JsonProcess build() {
		JsonProcess jsonProcess = new JsonProcess(processName, processType, inputSignals, outputSignals);
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
		this.inputSignals = asList(inputSignals).stream().map(n -> new JsonProcessInputSignal(n)).collect(toList());
		return this;
	}
	
	public JsonProcessBuilder withOutputSignals(String ...outputSignals) {
		this.outputSignals = asList(outputSignals);
		return this;
	}
	
	public JsonProcessBuilder withProperty(String name, String value) {
		this.properties.put(name, value);
		return this;
	}
}
