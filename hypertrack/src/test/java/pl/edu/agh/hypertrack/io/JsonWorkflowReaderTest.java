package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JsonWorkflowReaderTest {

	private static final String INPUT_SIGNAL = "input";
	private static final String OUTPUT_SIGNAL = "output";
	private static final String COUNT_CONTROL = "count";
	private static final String PROCESS_NAME = "proc";
	private static final String TYPE_PROPERTY = "type";
	private static final String TYPE = "dataflow";
	private static final String FUNC_PROPERTY = "function";
	private static final String FUNC = "func";
	
	private JsonWorkflowReader workflowReader = new JsonWorkflowReader();
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromEmptySting()
	{
		//given
		String json = "";
		
		//when
		Throwable thrown = catchThrowable(() -> workflowReader.readWorkflow(json));
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromNonJsonString()
	{
		//given
		String json = "{\"id\": \"file\", \"valeu\" : \"FILE\"}";
		
		// when
		Throwable thrown = catchThrowable(() -> workflowReader.readWorkflow(json));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldReadEmptyWorkflowWhenNonJsonStringPassedToReader()
	{
		//given
		String nonJson = "no a JSON string";
		
		// when
		Throwable thrown = catchThrowable(() -> workflowReader.readWorkflow(nonJson));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldReadedWorkflowHaveInputAndOutputSignals()
	{
		//given
		String json = "{" + getInputSignalJson() + "," + getOutputSignalJson() + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.readWorkflow(json);
		
		//then
		assertThat(readedWorkflow.getInputSignals()).containsOnly(INPUT_SIGNAL);
		assertThat(readedWorkflow.getOutputSignals()).containsOnly(OUTPUT_SIGNAL);
	}
	
	private String getInputSignalJson()
	{
		return "\"ins\": [ \"" + INPUT_SIGNAL + "\" ]";
	}
	
	private String getOutputSignalJson()
	{
		return "\"outs\": [ \"" + OUTPUT_SIGNAL + "\" ]";
	}
	
	@Test
	public void shouldReadedWorkflowHaveSignals()
	{
		//given
		String json = "{" + getSignalsJson() + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.readWorkflow(json);
		
		//then
		assertThat(readedWorkflow.getSignals()).extracting(JsonSignal::getSignalName).containsOnly(INPUT_SIGNAL, OUTPUT_SIGNAL);
		assertThat(readedWorkflow.getSignals()).extracting(JsonSignal::getControlType).filteredOn(s -> s != null).containsOnly(COUNT_CONTROL);
	}
	
	private String getSignalsJson()
	{
		return "\"signals\": [ {"
				+ "\"name\":\"" + INPUT_SIGNAL + "\"},"
				+ "{\"name\":\"" + OUTPUT_SIGNAL + "\", \"control\":\"" + COUNT_CONTROL + "\" } ]";
	}
	
	@Test
	public void shouldReadedWorkflowHaveProcesses()
	{
		//given
		String json = "{" + getProcessesJson() + "}";
		Map<String, Object> processProperties = new HashMap<>();
		processProperties.put(TYPE_PROPERTY, TYPE);
		processProperties.put(FUNC_PROPERTY, FUNC);
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.readWorkflow(json);
		
		//then
		assertThat(readedWorkflow.getProcesses()).extracting(JsonProcess::getProcessName).containsOnly(PROCESS_NAME);
		assertThat(readedWorkflow.getProcesses()).flatExtracting(JsonProcess::getInputSignals).containsOnly(INPUT_SIGNAL);
		assertThat(readedWorkflow.getProcesses()).flatExtracting(JsonProcess::getOutputSignals).containsOnly(OUTPUT_SIGNAL);
		assertThat(readedWorkflow.getProcesses()).flatExtracting(process -> process.getProperties().entrySet()).containsExactlyElementsOf(processProperties.entrySet());
	}
	
	private String getProcessesJson()
	{
		return "\"processes\": [ { " 
	        + "\"name\": \"" + PROCESS_NAME +"\","
		    + "\"" + TYPE_PROPERTY + "\": \"" + TYPE + "\","
	        + "\"" + FUNC_PROPERTY + "\": \"" + FUNC + "\","
	        + "\"ins\": [ \"" + INPUT_SIGNAL+ "\" ],"
	        + "\"outs\": [ \""+ OUTPUT_SIGNAL +"\" ]}]";
	}
}
