package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.willThrow;
import static pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicatorAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicator;

@RunWith(MockitoJUnitRunner.class)
public class HyperflowInputActivatioIndicatorFactoryTest {

	private static final String INPUT_SIGNAL = "input";
	
	@Mock
	private CountedSignalActivationIndicatorValidator validator;
	
	@InjectMocks
	private HyperflowInputActivatioIndicatorFactory factory;
	
	@Test
	public void shouldCreateFixedNumberActivationIndicatorWithDefaultValueWhenNoIndicatorSpecified() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL);
		
		//when
		HyperflowInputActivationIndicator activationIndicator = factory.createActivationIndicator(input);
		
		//then
		assertThat(activationIndicator).hasInstancesToActivate(1);
		assertThat(activationIndicator).hasSignalToCountName(null);
		assertThat(activationIndicator).hasSignalToCount(null);
	}
	
	@Test
	public void shouldCreateFixedNumberActivationIndicatorWhenIndicatorIsNumeric() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL + ":3");
		
		//when
		HyperflowInputActivationIndicator activationIndicator = factory.createActivationIndicator(input);
		
		//then
		assertThat(activationIndicator).hasInstancesToActivate(3);
		assertThat(activationIndicator).hasSignalToCountName(null);
		assertThat(activationIndicator).hasSignalToCount(null);
	}
	
	@Test
	public void shouldCreateCountedActivationIndicatorWhenIndicatorIsString() {
		
		//given
		String signalToCount = "signalToCount";
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL + ":" + signalToCount);
		
		//when
		HyperflowInputActivationIndicator activationIndicator = factory.createActivationIndicator(input);
		
		//then
		assertThat(activationIndicator).hasInstancesToActivate(null);
		assertThat(activationIndicator).hasSignalToCountName(signalToCount);
		assertThat(activationIndicator).hasSignalToCount(null);
	}
	
	@Test
	public void shouldRethrowExceptionThrownByValidatorWhenIndicatorIsStringAndValidationFails() {
		
		//given
		String signalToCount = "signalToCount";
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL + ":" + signalToCount);
		HypertrackJsonReadException validationException = new HypertrackJsonReadException("Validation error");
		willThrow(validationException).given(validator).validateActivationIndicator(input);
				
		//when
		Throwable thrown = catchThrowable(() -> factory.createActivationIndicator(input));
				
		//then
		assertThat(thrown).isSameAs(validationException);
	}
}
