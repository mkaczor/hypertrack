package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static pl.edu.agh.hypertrack.model.HyperflowProcessAssert.assertThat;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.hypertrack.model.HyperflowInputSignal;
import pl.edu.agh.hypertrack.model.HyperflowOutputSignal;
import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HypertrackEntityUniqueKey;

@RunWith(MockitoJUnitRunner.class)
public class HyperflowProcessReaderTest {

	private final static String JSON_STRING = "json";
	private final static String WORKFLOW_NAME = "workflowName";
	private final static String PROCESS_NAME = "process";
	private final static String PROCESS_OUTPUT_SIGNAL_NAME = "in";
	private final static String PROCESS_INPUT_SIGNAL_NAME = "out";
	
	@Mock
	private HyperflowSignalReader signalReader;
	
	@InjectMocks
	private HyperflowProcessReader processReader;
	
	private JsonProcess process = new JsonProcess(PROCESS_NAME, asList(PROCESS_INPUT_SIGNAL_NAME), asList(PROCESS_OUTPUT_SIGNAL_NAME));
	private JsonSignal inputSignal = new JsonSignal(PROCESS_INPUT_SIGNAL_NAME);
	private JsonSignal outputSignal = new JsonSignal(PROCESS_OUTPUT_SIGNAL_NAME);
	
	
	@Test
	public void shouldReadedWorkflowHaveProcessLikeInJsonWorkflow() {
		
		// given
		JsonWorkflow jsonWorkflow = aJsonWorkflow().build();
		HyperflowInputSignal input = new HyperflowInputSignal();
		HyperflowOutputSignal output = new HyperflowOutputSignal();
		given(signalReader.readInputSignal(any(), eq(inputSignal))).willReturn(input);
		given(signalReader.readOutputSignal(any(), eq(outputSignal))).willReturn(output);
		
		// when
		Set<HyperflowProcess> workflowProcesses = processReader.read(jsonWorkflow);
		
		//then
		assertThat(workflowProcesses).hasSize(1);
		HyperflowProcess hyperflowProcess = workflowProcesses.iterator().next();
		
		assertThat(hyperflowProcess).hasKey(new HypertrackEntityUniqueKey(WORKFLOW_NAME, PROCESS_NAME));
		assertThat(hyperflowProcess).hasOnlyInputSignals(input);
		assertThat(hyperflowProcess).hasOnlyOutputSignals(output);
	}
	
	@Test
	public void shouldThrowExceptionWhenTwoProcessesWithTheSameNameDefined() {
		
		// given
		JsonWorkflow jsonWorkflow = aJsonWorkflow().withProcesses(process, processWithSameNameAs(process)).build();

		// when
		Throwable thrown = catchThrowable(() -> processReader.read(jsonWorkflow));

		// then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	//TODO: perhaps different place
	private JsonWorkflowBuilder aJsonWorkflow() {
		return aJsonWorkflow().withName(WORKFLOW_NAME)
			.withSignals(inputSignal, outputSignal)
			.withProcesses(process);
	}
	
	private JsonProcess processWithSameNameAs(JsonProcess processToDuplicate) {
		
		return new JsonProcess(processToDuplicate.getProcessName(), emptyList(), emptyList());
	}
}
