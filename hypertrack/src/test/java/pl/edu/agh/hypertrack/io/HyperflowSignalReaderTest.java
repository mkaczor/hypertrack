package pl.edu.agh.hypertrack.io;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert.assertThat;
import static pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert.assertThat;

import java.util.Map;

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
public class HyperflowSignalReaderTest {

	private static final String WORKFLOW_NAME = "workflowName";
	private static final String SIGNAL_NAME = "signalName";
	private static final String PROCESS_NAME = "processName";
	
	@Mock
	private Map<String, JsonSignal> jsonSignals;
	
	@InjectMocks
	private HyperflowSignalReader signalReader = new HyperflowSignalReader(WORKFLOW_NAME);
	
	private HypertrackEntityKey signalKey = new HypertrackEntityKey(WORKFLOW_NAME, SIGNAL_NAME);
	
	private HyperflowProcess process = new HyperflowProcess(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_NAME),
			HyperflowProcessType.DATAFLOW, emptyMap());	
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenReadingOutputSignalWithNameNotSpecifiedInJson() {
		
		//given
		given(jsonSignals.containsKey(SIGNAL_NAME)).willReturn(false);
	
		//when
		Throwable thrown = catchThrowable(() -> signalReader.readOutputSignal(process, SIGNAL_NAME));
		
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class)
			.hasMessageContaining("No signal named " + SIGNAL_NAME + " defined, but used as output of process " + PROCESS_NAME);
	}
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenReadingInputSignalWithNameNotSpecifiedInJson() {
		
		//given
		given(jsonSignals.containsKey(SIGNAL_NAME)).willReturn(false);
	
		//when
		Throwable thrown = catchThrowable(() -> signalReader.readInputSignal(process, SIGNAL_NAME));
		
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class)
			.hasMessageContaining("No signal named " + SIGNAL_NAME + " defined, but used as input to process " + PROCESS_NAME);
	}

	@Test
	public void shouldReadInputSignalWhenSignalNameAndSourceProcessSpecified() {
		
		//given
		given(jsonSignals.containsKey(SIGNAL_NAME)).willReturn(true);
		
		//when
		HyperflowInputSignal inputSignal = signalReader.readInputSignal(process, SIGNAL_NAME);
		
		//then
		assertThat(inputSignal).hasKey(signalKey);
		assertThat(inputSignal).hasTarget(process);
		assertThat(inputSignal).hasSource(null); //TODO: hasNoSource
	}
	
	@Test
	public void shouldReadOutputSignalWhenSignalNameAndSourceProcessSpecified() {
		
		//given
		given(jsonSignals.containsKey(SIGNAL_NAME)).willReturn(true);
		
		//when
		HyperflowOutputSignal outputSignal = signalReader.readOutputSignal(process, SIGNAL_NAME);
		
		//then
		assertThat(outputSignal).hasKey(signalKey);
		assertThat(outputSignal).hasSource(process);
		assertThat(outputSignal).hasNoTarget();
	}
}