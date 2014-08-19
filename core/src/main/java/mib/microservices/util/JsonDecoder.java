package mib.microservices.util;

import kafka.serializer.Decoder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDecoder<T> implements Decoder<T> {
	private Class<T> clazz;
	private ObjectMapper mapper;

	public JsonDecoder(Class<T> clazz) {
		this.clazz = clazz;
		
		this.mapper = new ObjectMapper();
		this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Override
	public T fromBytes(byte[] bytes) {
		try {
			return this.mapper.readValue(bytes, this.clazz);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}