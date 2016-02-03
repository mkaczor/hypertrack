package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.Map.Entry;

public class JsonGenerator {

	public String getJsonForProcesses(JsonProcess... jsonProcesses) {
		return "{\"processes\": [ " + asList(jsonProcesses).stream().map(this::getProcessJson).collect(joining(",")) + "]}";
	}
	
	private String getProcessJson(JsonProcess jsonProcess) {
		return "{\"name\": \"" + jsonProcess.getProcessName() +"\","
						+ "\"type\": \"" + jsonProcess.getProcessType() + "\","
	        			+ "\"ins\": [ \"" + jsonProcess.getInputSignals().stream().collect(joining(",")) + "\" ],"
	        			+ "\"outs\": [ \""+ jsonProcess.getOutputSignals().stream().collect(joining(",")) +"\" ],"
	        			+ jsonProcess.getProperties().entrySet().stream().map(this::propertyJsonString).collect(joining(","))
	        			+ "}";
	}
	
	private String propertyJsonString(Entry<String, String> property) {
		return "\"" + property.getKey() + "\":\"" + property.getValue() + "\"";
	}
	
	public String getJsonForSignals(JsonSignal... signals) {
		String signalsJsonString = asList(signals).stream()
				.map(this::getJsonString)
				.collect(joining(","));
		return "{\"signals\": [" + signalsJsonString + "]}";

	}
	
	private String getJsonString(JsonSignal jsonSignal) {
		String result = "{\"name\":\"" + jsonSignal.getSignalName() + "\"";
		if (jsonSignal.getControlType() != null) {
			result += ",\"control\":\"" + jsonSignal.getControlType() + "\"";
		}
		result += "}";
		return result;
	}
}
