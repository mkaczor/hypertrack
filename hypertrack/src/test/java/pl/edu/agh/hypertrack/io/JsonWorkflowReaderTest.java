package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.edu.agh.hypertrack.io.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

public class JsonWorkflowReaderTest {

	private static final String WORKFLOW_NAME = "workflowName";
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
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromJsonWithWrongStructure()
	{
		//given
		String json = "{\"id\": \"file\", \"valeu\" : \"FILE\"}";
		
		// when
		Throwable thrown = catchThrowable(() -> workflowReader.readWorkflow(json));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingFromNonJsonString()
	{
		//given
		String nonJson = "no a JSON string";
		
		// when
		Throwable thrown = catchThrowable(() -> workflowReader.readWorkflow(nonJson));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error reading");
	}
	
	@Test
	public void shouldReadedWorkflowHaveName()
	{
		//given
		String json = "{ \"name\":\"" + WORKFLOW_NAME + "\"}";
		
		//when
		JsonWorkflow readWorkflow = workflowReader.readWorkflow(json);
		
		//then
		assertThat(readWorkflow).hasWorkflowName(WORKFLOW_NAME);
	}
	
	@Test
	public void shouldReadedWorkflowHaveInputAndOutputSignals()
	{
		//given
		String json = "{" + getInputSignalJson() + "," + getOutputSignalJson() + "}";
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.readWorkflow(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyInputSignals(INPUT_SIGNAL);
		assertThat(readedWorkflow).hasOnlyOutputSignals(OUTPUT_SIGNAL);
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
		assertThat(readedWorkflow).hasOnlySignals(expectedSignals());
	}
	
	private String getSignalsJson()
	{
		return "\"signals\": [ {"
				+ "\"name\":\"" + INPUT_SIGNAL + "\"},"
				+ "{\"name\":\"" + OUTPUT_SIGNAL + "\", \"control\":\"" + COUNT_CONTROL + "\" } ]";
	}
	
	private JsonSignal[] expectedSignals()
	{
		JsonSignal expectedSignals[] = new JsonSignal[] {new JsonSignal(INPUT_SIGNAL), new JsonSignal(OUTPUT_SIGNAL, COUNT_CONTROL)};
		return expectedSignals;
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
		assertThat(readedWorkflow).hasOnlyProcesses(expectedJsonProcess());
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
	
	private JsonProcess expectedJsonProcess()
	{
		Map<String, Object> processProperties = new HashMap<>();
		processProperties.put(TYPE_PROPERTY, TYPE);
		processProperties.put(FUNC_PROPERTY, FUNC);
		
		JsonProcess expectedProcess = new JsonProcess(PROCESS_NAME, new HashSet<>(asList(INPUT_SIGNAL)), new HashSet<>(asList(OUTPUT_SIGNAL)));
		expectedProcess.setProperty(TYPE_PROPERTY, TYPE);
		expectedProcess.setProperty(FUNC_PROPERTY, FUNC);
		return expectedProcess;
	}
}
