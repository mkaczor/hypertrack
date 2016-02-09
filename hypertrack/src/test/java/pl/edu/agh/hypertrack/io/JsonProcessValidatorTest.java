package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;

public class JsonProcessValidatorTest {

	private static final String PROCESS_NAME = "name";
	private static final String INPUT_SIGNAL = "in";
	private static final String OUTPUT_SIGNAL = "out";
	
	private JsonProcessValidator validator = new JsonProcessValidator();
	
	private JsonProcessBuilder aJsonProcess = JsonProcessBuilder.aJsonProcess().withProcessName(PROCESS_NAME)
			.withInputSignals(INPUT_SIGNAL)
			.withOutputSignals(OUTPUT_SIGNAL);
	
	@Test
	public void shouldThrowExceptionWhenValidatingProcessWithDuplicatedInputSignals() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.withInputSignals(INPUT_SIGNAL, INPUT_SIGNAL).build();
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));
				
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("duplicated input signals defined");
	}
	
	@Test
	public void shouldThrowExceptionWhenValidatingProcessWithTwoInputSignalsWithSameNameButDifferentActivationIndicator() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.withInputSignals(INPUT_SIGNAL, INPUT_SIGNAL + ":2").build();
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));
				
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("duplicated input signals defined");
	}
	
	@Test
	public void shouldThrowExceptionWhenValidatingProcessWithNoInputSignals() {

		// given
		JsonProcess jsonProcess = aJsonProcess.withInputSignals().build();

		// when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));

		// then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("no input signals");
	}
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenValidatingProcessWithDuplicatedOutputSignals() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess.withOutputSignals(OUTPUT_SIGNAL, OUTPUT_SIGNAL).build();
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));
				
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("duplicated output signals defined");
	}
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenValidatingProcessWithNoOutputSignals() {

		// given
		JsonProcess jsonProcess = aJsonProcess.withOutputSignals().build();

		// when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));

		// then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("no output signals");
	}
	
	@Test
	public void shouldNotThrowExceptionWhenValidatingProcessWithCorrectSignalsDefinition() {
		
		// given
		JsonProcess jsonProcess = aJsonProcess.build();

		// when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));

		// then
		assertThat(thrown).isNull();
	}
}
