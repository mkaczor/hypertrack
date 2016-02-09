package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CountedSignalActivationIndicatorValidatorTest {

	private static final String INPUT_SIGNAL = "input";
	private static final String SIGNAL_TO_COUNT = "signalToCount";
	
	@Mock
	private Map<String, JsonSignal> jsonSignals;
	
	@InjectMocks
	private CountedSignalActivationIndicatorValidator validator;
	
	@Test
	public void shouldThrowExceptionWhenActivationIndicatorPointsToNonExistingSignal() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL + ":" + SIGNAL_TO_COUNT);
		given(jsonSignals.containsKey(INPUT_SIGNAL)).willReturn(true);
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateActivationIndicator(input));
						
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessage(
				"No signal named " + SIGNAL_TO_COUNT + " defined, but used as count by signal " + INPUT_SIGNAL);
	}
	
	@Test
	public void shouldThrowExceptionWhenActivationIndicatorPointsToNonCountSignal() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL + ":" + SIGNAL_TO_COUNT);
		given(jsonSignals.containsKey(INPUT_SIGNAL)).willReturn(true);
		given(jsonSignals.containsKey(SIGNAL_TO_COUNT)).willReturn(true);
		JsonSignal signalToCount = new JsonSignal(SIGNAL_TO_COUNT);
		given(jsonSignals.get(SIGNAL_TO_COUNT)).willReturn(signalToCount);
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateActivationIndicator(input));
						
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessage(
				"Signal named " + SIGNAL_TO_COUNT + " is not count signal, but used as one by signal " + INPUT_SIGNAL);
	}
	
	@Test
	public void shouldNotThrowExceptionWhenActivationIndicatorPointsToExistingCountSignal() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL + ":" + SIGNAL_TO_COUNT);
		given(jsonSignals.containsKey(INPUT_SIGNAL)).willReturn(true);
		given(jsonSignals.containsKey(SIGNAL_TO_COUNT)).willReturn(true);
		JsonSignal signalToCount = new JsonSignal(SIGNAL_TO_COUNT, "count");
		given(jsonSignals.get(SIGNAL_TO_COUNT)).willReturn(signalToCount);
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateActivationIndicator(input));
						
		//then
		assertThat(thrown).isNull();
	}
}
