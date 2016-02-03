package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.edu.agh.hypertrack.io.Assertions.assertThat;

import java.util.Map.Entry;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;

import pl.edu.agh.hypertrack.model.HyperflowProcessType;

public class JsonWorkflowReaderTest {

	/*
	 * TODO:
	 * - tworzenie JSONA - jakas klasa
	 * - builder do procesow/sygnalow
	 */
	
	private static final String WORKFLOW_NAME = "workflowName";
	private static final String INPUT_SIGNAL = "input";
	private static final String OUTPUT_SIGNAL = "output";
	private static final String COUNT_CONTROL = "count";
	private static final String PROCESS_NAME = "proc";
	private static final String FUNC_PROPERTY = "function";
	private static final String TYPE_PROPERTY = "type";
	private static final String FOREACH_TYPE = "FOREACH";
	private static final String FUNC = "func";
	
	private JsonWorkflowReader workflowReader = new JsonWorkflowReader();
	
	private JsonProcessBuilder aJsonProcess = JsonProcessBuilder.aJsonProcess()
			.withProcessName(PROCESS_NAME)
			.withInputSignals(INPUT_SIGNAL)
			.withOutputSignals(OUTPUT_SIGNAL)
			.withProperty(FUNC_PROPERTY, FUNC);
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromEmptySting() {
		
		//given
		String json = "";
		
		//when
		Throwable thrown = catchThrowable(() -> workflowReader.read(json));
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromJsonWithWrongStructure() {
		
		//given
		String json = "{\"id\": \"file\", \"valeu\" : \"FILE\"}";
		
		// when
		Throwable thrown = catchThrowable(() -> workflowReader.read(json));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromNonJsonString() {
		
		//given
		String nonJson = "no a JSON string";
		
		// when
		Throwable thrown = catchThrowable(() -> workflowReader.read(nonJson));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldReadedWorkflowHaveName() {
		
		//given
		String json = "{ \"name\":\"" + WORKFLOW_NAME + "\"}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasWorkflowName(WORKFLOW_NAME);
	}
	
	@Test
	public void shouldReadedWorkflowHaveInputAndOutputSignals() {
		
		//given
		String json = "{" + getInputSignalJsonFor(INPUT_SIGNAL) + "," + getOutputSignalJsonFor(OUTPUT_SIGNAL) + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyInputSignals(INPUT_SIGNAL);
		assertThat(readedWorkflow).hasOnlyOutputSignals(OUTPUT_SIGNAL);
	}
	
	private String getInputSignalJsonFor(String inputSignalName) {
		return "\"ins\": [ \"" + inputSignalName + "\" ]";
	}
	
	private String getOutputSignalJsonFor(String outputSignalName) {
		return "\"outs\": [ \"" + outputSignalName + "\" ]";
	}
	
	@Test
	public void shouldReadedWorkflowHaveSignals() {
		
		//given
		JsonSignal[] expectedSignals = expectedSignals();
		String json = "{" + getSignalsJson(expectedSignals) + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlySignals(expectedSignals);
	}
	
	private JsonSignal[] expectedSignals() {
		JsonSignal expectedSignals[] = new JsonSignal[] {new JsonSignal(INPUT_SIGNAL), new JsonSignal(OUTPUT_SIGNAL, COUNT_CONTROL)};
		return expectedSignals;
	}

	private String getSignalsJson(JsonSignal[] signals) {
		String signalsJsonString = asList(signals).stream()
				.map(this::getJsonString)
				.collect(joining(","));
		return "\"signals\": [" + signalsJsonString + "]";

	}
	
	private String getJsonString(JsonSignal jsonSignal) {
		String result = "{\"name\":\"" + jsonSignal.getSignalName() + "\"";
		if (jsonSignal.getControlType() != null) {
			result += ",\"control\":\"" + jsonSignal.getControlType() + "\"";
		}
		result += "}";
		return result;
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenSignalDoesNotHaveUniqueName() {
	
		//given
		JsonSignal[] signals = new JsonSignal[] {new JsonSignal(INPUT_SIGNAL), new JsonSignal(INPUT_SIGNAL, COUNT_CONTROL)};
		String json = "{" + getSignalsJson(signals) + "}";
		
		//when
		Throwable thrown = catchThrowable(() -> workflowReader.read(json));
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasCauseInstanceOf(JsonMappingException.class);
		assertThat(thrown.getCause()).hasMessageContaining("Already had POJO for id");
	}
	
	@Test
	public void shouldReadedWorkflowHaveProcessesWithDefaultTypeWhenTypeNotSpecified() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.build();
		String json = "{" + getProcessesJson(jsonProcess) + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyProcesses(jsonProcess);
	}
	
	@Test
	public void shouldReadedWorkflowHaveProcessesWithSpecifiedType() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.withProcessType(HyperflowProcessType.FOREACH).build();
		String json = "{" + getProcessesJson(jsonProcess) + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyProcesses(jsonProcess);
	}

	@Test
	public void shouldThrowIllegalArgumentExceptionWhenProcessDoesNotHaveUniqueName() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.build();
		String json = "{" + getProcessesJson(jsonProcess, processNamedSameAs(jsonProcess)) + "}";
		
		//when
		Throwable thrown = catchThrowable(() -> workflowReader.read(json));
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasCauseInstanceOf(JsonMappingException.class);
		assertThat(thrown.getCause()).hasMessageContaining("Already had POJO for id");
	}
	
	private JsonProcess processNamedSameAs(JsonProcess process) {
		return aJsonProcess.withProcessName(process.getProcessName()).build();
	}

	private String getProcessesJson(JsonProcess... jsonProcesses) {
		return "\"processes\": [ " + asList(jsonProcesses).stream().map(this::getProcessJson).collect(joining(",")) + "]";
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
}
