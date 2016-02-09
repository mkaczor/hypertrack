package pl.edu.agh.hypertrack.io;

import static pl.edu.agh.hypertrack.io.JsonProcessInputSignalAssert.assertThat;

import org.junit.Test;

public class JsonProcessInputSignalTest {

	@Test
	public void shouldActivationIndicatorBe1WhenNotSpecified() {
		
		//given
		String inputSignalJson = "name";
		
		//when
		JsonProcessInputSignal inputSignal = new JsonProcessInputSignal(inputSignalJson);
		
		//then
		assertThat(inputSignal).hasActivationIndicator("1");
		assertThat(inputSignal).hasName("name");
	}
	
	@Test
	public void shouldActivationIndicatorBeEqualToOneSpecifiedAfterColon() {
		
		//given
		String inputSignalJson = "name:signalToCount";
		
		//when
		JsonProcessInputSignal inputSignal = new JsonProcessInputSignal(inputSignalJson);
		
		//then
		assertThat(inputSignal).hasActivationIndicator("signalToCount");
		assertThat(inputSignal).hasName("name");
	}
}
