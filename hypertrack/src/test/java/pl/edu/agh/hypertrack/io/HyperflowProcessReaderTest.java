package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static pl.edu.agh.hypertrack.model.HyperflowProcessAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.hypertrack.model.HyperflowInputSignal;
import pl.edu.agh.hypertrack.model.HyperflowOutputSignal;
import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HyperflowProcessType;
import pl.edu.agh.hypertrack.model.HypertrackEntityUniqueKey;

@RunWith(MockitoJUnitRunner.class)
public class HyperflowProcessReaderTest {

	private final static String WORKFLOW_NAME = "workflowName";
	private final static String PROCESS_NAME = "process";
	private final static String PROCESS_OUTPUT_SIGNAL_NAME = "in";
	private final static String PROCESS_INPUT_SIGNAL_NAME = "out";
	
	@Mock
	private HyperflowSignalReader signalReader;
	
	@Mock
	private JsonProcessSignalsValidator signlasValidator;
	
	@InjectMocks
	private HyperflowProcessReader processReader;

	@Test
	public void shouldRethrowExceptionThrownBySignalsValidatorWhenProcessSignalsValidationFails() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess();
		HypertrackJsonReadException validationException = new HypertrackJsonReadException("Validation error");
		willThrow(validationException).given(signlasValidator).validate(jsonProcess);
		
		//when
		Throwable thrown = catchThrowable(() -> processReader.read(WORKFLOW_NAME, jsonProcess));
		
		//then
		assertThat(thrown).isSameAs(validationException);
	}
	
	@Test
	public void shouldReadedProcessHasCorrectKey() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess();
		
		//when
		HyperflowProcess hyperflowProcess = processReader.read(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess).hasKey(new HypertrackEntityUniqueKey(WORKFLOW_NAME, PROCESS_NAME));
	}
	
	@Test
	public void shouldReadedProcessHasSameTypeAsJsonProcess() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess();
		
		//when
		HyperflowProcess hyperflowProcess = processReader.read(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess).hasProcessType(HyperflowProcessType.FOREACH);
	}
	
	@Test
	public void shouldReadedProcessHasSamePropertiesAsJsonProcess() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess();
		jsonProcess.setProperty("funct", "funcVal");
		
		//when
		HyperflowProcess hyperflowProcess = processReader.read(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess.getProperties()).containsAllEntriesOf(jsonProcess.getProperties());
	}
	
	@Test
	public void shouldReadedProcessHaveProcessLikeInJsonWorkflow() {
		
		// given
		HyperflowInputSignal input = new HyperflowInputSignal();
		HyperflowOutputSignal output = new HyperflowOutputSignal();
		given(signalReader.readInputSignal(any(), eq(PROCESS_INPUT_SIGNAL_NAME))).willReturn(input);
		given(signalReader.readOutputSignal(any(), eq(PROCESS_OUTPUT_SIGNAL_NAME))).willReturn(output);
		
		// when
		HyperflowProcess hyperflowProcess = processReader.read(WORKFLOW_NAME, aJsonProcess());
		
		//then
		assertThat(hyperflowProcess).hasOnlyInputSignals(input);
		assertThat(hyperflowProcess).hasOnlyOutputSignals(output);
	}
	
	private JsonProcess aJsonProcess() {
		return new JsonProcess(PROCESS_NAME, HyperflowProcessType.FOREACH, asList(PROCESS_INPUT_SIGNAL_NAME), asList(PROCESS_OUTPUT_SIGNAL_NAME));
	}
}
