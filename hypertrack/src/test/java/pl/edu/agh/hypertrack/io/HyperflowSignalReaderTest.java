package pl.edu.agh.hypertrack.io;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert.assertThat;
import static pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicator;
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
	private JsonSignalValidator validator;
	
	@Mock
	private HyperflowInputActivatioIndicatorFactory activationIndicatorFactory;
	
	@InjectMocks
	private HyperflowSignalReader signalReader = new HyperflowSignalReader(WORKFLOW_NAME);
	
	private HypertrackEntityKey signalKey = new HypertrackEntityKey(WORKFLOW_NAME, SIGNAL_NAME);
	
	private HyperflowProcess process = new HyperflowProcess(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_NAME),
			HyperflowProcessType.DATAFLOW, emptyMap());	
	
	@Test
	public void shouldRethrowExceptionThrownBySignalValidatorWhenInputSignalValidationFails() {
		
		//given
		HypertrackJsonReadException validationException = new HypertrackJsonReadException("Validation error");
		JsonProcessInputSignal inputSignal = new JsonProcessInputSignal(SIGNAL_NAME);
		willThrow(validationException).given(validator).validateInputSignal(inputSignal, process);
		
		//when
		Throwable thrown = catchThrowable(() -> signalReader.readInputSignal(process, inputSignal));
		
		//then
		assertThat(thrown).isSameAs(validationException);
	}
	
	@Test
	public void shouldReadInputSignalWhenSignalNameAndSourceProcessSpecified() {
		
		//when
		HyperflowInputSignal inputSignal = signalReader.readInputSignal(process, new JsonProcessInputSignal(SIGNAL_NAME));
		
		//then
		assertThat(inputSignal).hasKey(signalKey);
		assertThat(inputSignal).hasTarget(process);
		assertThat(inputSignal).hasSource(null); //TODO: hasNoSource
	}
	
	@Test
	public void shouldReadedInputSignalHaveActivationIndicatorReturnedByFactory() {
		
		//given
		JsonProcessInputSignal inputSignal = new JsonProcessInputSignal(SIGNAL_NAME);
		HyperflowInputActivationIndicator activationIndicator = mock(HyperflowInputActivationIndicator.class);
		given(activationIndicatorFactory.createActivationIndicator(inputSignal)).willReturn(activationIndicator);
		
		//when
		HyperflowInputSignal hyperflowInputSignal = signalReader.readInputSignal(process, inputSignal);
		
		//then
		assertThat(hyperflowInputSignal).hasActivationIndicator(activationIndicator);
	}
	
	@Test
	public void shouldRethrowExceptionThrownBySignalValidatorWhenOutputSignalValidationFails() {
		
		//given
		HypertrackJsonReadException validationException = new HypertrackJsonReadException("Validation error");
		willThrow(validationException).given(validator).validateOutputSignal(SIGNAL_NAME, process);
		
		//when
		Throwable thrown = catchThrowable(() -> signalReader.readOutputSignal(process, SIGNAL_NAME));
		
		//then
		assertThat(thrown).isSameAs(validationException);
	}
	
	@Test
	public void shouldReadOutputSignalWhenSignalNameAndSourceProcessSpecified() {
		
		//when
		HyperflowOutputSignal outputSignal = signalReader.readOutputSignal(process, SIGNAL_NAME);
		
		//then
		assertThat(outputSignal).hasKey(signalKey);
		assertThat(outputSignal).hasSource(process);
		assertThat(outputSignal).hasNoTarget();
	}
}