package mib.microservice.dto;

//Simple DTO. No annotations needed for JSON (de)serializing. This is done with Genson on field base. 
//So not even setters would be needed for that process.
public class SampleDomainObject {

	private int field;

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}
}
