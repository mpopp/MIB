package mib.microservices.util;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonEncoder<T> implements Encoder<T> {
	private ObjectMapper mapper;

	public JsonEncoder(VerifiableProperties verifiableProperties) {
		this.mapper = new ObjectMapper();
	}
	
	@Override
	public byte[] toBytes(T value) {
		try {
			return this.mapper.writeValueAsBytes(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new byte[0];
	}
}
