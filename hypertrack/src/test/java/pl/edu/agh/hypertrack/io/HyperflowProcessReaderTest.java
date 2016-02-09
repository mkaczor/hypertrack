package pl.edu.agh.hypertrack.io;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static pl.edu.agh.hypertrack.io.JsonProcessBuilder.aJsonProcess;
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
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

@RunWith(MockitoJUnitRunner.class)
public class HyperflowProcessReaderTest {

	private final static String WORKFLOW_NAME = "workflowName";
	private final static String PROCESS_NAME = "process";
	private final static String PROCESS_OUTPUT_SIGNAL_NAME = "in";
	private final static String PROCESS_INPUT_SIGNAL_NAME = "out";
	
	@Mock
	private HyperflowSignalReader signalReader;
	
	@Mock
	private JsonProcessValidator signlasValidator;
	
	@Mock
	private HyperflowProcessFactory processFactory;
	
	@InjectMocks
	private HyperflowProcessReader processReader;
			
	@Test
	public void shouldRethrowExceptionThrownBySignalsValidatorWhenProcessSignalsValidationFails() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess().build();
		HypertrackJsonReadException validationException = new HypertrackJsonReadException("Validation error");
		willThrow(validationException).given(signlasValidator).validate(jsonProcess);
		
		//when
		Throwable thrown = catchThrowable(() -> processReader.read(WORKFLOW_NAME, jsonProcess));
		
		//then
		assertThat(thrown).isSameAs(validationException);
	}
	
	@Test
	public void shouldReturnProcessCreatedByProcessFactory() {
		
		//given
		HyperflowProcess hyperflowProcess = mock(HyperflowProcess.class);
		JsonProcess jsonProcess = aJsonProcess().build();
		given(processFactory.createNewEmptyProcess(WORKFLOW_NAME, jsonProcess)).willReturn(hyperflowProcess);
		
		//when
		HyperflowProcess readedProcess = processReader.read(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(readedProcess).isSameAs(hyperflowProcess);
	}
	
	@Test
	public void shouldReadedProcessHaveSignalsLikeInJsonWorkflow() {
		
		// given
		JsonProcess jsonProcess = aJsonProcess()
				.withInputSignals(PROCESS_INPUT_SIGNAL_NAME)
				.withOutputSignals(PROCESS_OUTPUT_SIGNAL_NAME)
				.build();

		HyperflowInputSignal input = mock(HyperflowInputSignal.class);
		HyperflowOutputSignal output = mock(HyperflowOutputSignal.class);
		HyperflowProcess emptyHyperflowProcess = emptyHyperflowProcess();
		given(processFactory.createNewEmptyProcess(WORKFLOW_NAME, jsonProcess)).willReturn(emptyHyperflowProcess);
		given(signalReader.readInputSignal(emptyHyperflowProcess, new JsonProcessInputSignal(PROCESS_INPUT_SIGNAL_NAME))).willReturn(input);
		given(signalReader.readOutputSignal(emptyHyperflowProcess, PROCESS_OUTPUT_SIGNAL_NAME)).willReturn(output);
		
		// when
		HyperflowProcess hyperflowProcess = processReader.read(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess).hasOnlyInputSignals(input);
		assertThat(hyperflowProcess).hasOnlyOutputSignals(output);
	}
	
	private HyperflowProcess emptyHyperflowProcess() {
		return new HyperflowProcess(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_NAME),
				HyperflowProcessType.DATAFLOW, emptyMap());
	}
}
