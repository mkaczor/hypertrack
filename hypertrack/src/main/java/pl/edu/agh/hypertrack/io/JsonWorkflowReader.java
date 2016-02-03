package pl.edu.agh.hypertrack.io;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

final class JsonWorkflowReader {

	private ObjectMapper mapper = new ObjectMapper();

	public JsonWorkflow read(String json)
	{
		try {
			return mapper.readValue(json, JsonWorkflow.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error reading " + json, e);
		}
	}
}
