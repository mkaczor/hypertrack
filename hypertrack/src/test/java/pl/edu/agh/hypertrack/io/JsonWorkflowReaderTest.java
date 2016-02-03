package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.edu.agh.hypertrack.io.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;

import pl.edu.agh.hypertrack.model.HyperflowProcessType;

public class JsonWorkflowReaderTest {

	private static final String WORKFLOW_NAME = "workflowName";
	private static final String INPUT_SIGNAL = "input";
	private static final String OUTPUT_SIGNAL = "output";
	private static final String COUNT_CONTROL = "count";
	private static final String PROCESS_NAME = "proc";
	private static final String FUNC_PROPERTY = "function";
	private static final String FUNC = "func";
	
	private JsonWorkflowReader workflowReader = new JsonWorkflowReader();
	
	private JsonGenerator jsonGenerator = new JsonGenerator();
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
		String json = jsonGenerator.getJsonForWorkflowName(WORKFLOW_NAME);
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasWorkflowName(WORKFLOW_NAME);
	}
	
	@Test
	public void shouldReadedWorkflowHaveInputAndOutputSignals() {
		
		//given
		String json = jsonGenerator.getJsonForInputAndOutputSignal(INPUT_SIGNAL, OUTPUT_SIGNAL);
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyInputSignals(INPUT_SIGNAL);
		assertThat(readedWorkflow).hasOnlyOutputSignals(OUTPUT_SIGNAL);
	}
	
	@Test
	public void shouldReadedWorkflowHaveSignals() {
		
		//given
		JsonSignal[] expectedSignals = expectedSignals();
		String json = jsonGenerator.getJsonForSignals(expectedSignals);
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlySignals(expectedSignals);
	}
	
	private JsonSignal[] expectedSignals() {
		JsonSignal expectedSignals[] = new JsonSignal[] {new JsonSignal(INPUT_SIGNAL), new JsonSignal(OUTPUT_SIGNAL, COUNT_CONTROL)};
		return expectedSignals;
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenSignalDoesNotHaveUniqueName() {
	
		//given
		JsonSignal[] signals = new JsonSignal[] {new JsonSignal(INPUT_SIGNAL), new JsonSignal(INPUT_SIGNAL, COUNT_CONTROL)};
		String json = jsonGenerator.getJsonForSignals(signals);
		
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
		String json = jsonGenerator.getJsonForProcesses(jsonProcess);
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyProcesses(jsonProcess);
	}
	
	@Test
	public void shouldReadedWorkflowHaveProcessesWithSpecifiedType() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.withProcessType(HyperflowProcessType.FOREACH).build();
		String json = jsonGenerator.getJsonForProcesses(jsonProcess);
		
		//when
		JsonWorkflow readedWorkflow = workflowReader.read(json);
		
		//then
		assertThat(readedWorkflow).hasOnlyProcesses(jsonProcess);
	}

	@Test
	public void shouldThrowIllegalArgumentExceptionWhenProcessDoesNotHaveUniqueName() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.build();
		String json = jsonGenerator.getJsonForProcesses(jsonProcess, processNamedSameAs(jsonProcess));
		
		//when
		Throwable thrown = catchThrowable(() -> workflowReader.read(json));
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasCauseInstanceOf(JsonMappingException.class);
		assertThat(thrown.getCause()).hasMessageContaining("Already had POJO for id");
	}
	
	private JsonProcess processNamedSameAs(JsonProcess process) {
		return aJsonProcess.withProcessName(process.getProcessName()).build();
	}
}
