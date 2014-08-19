package mib.microservices.testservice;

import java.util.List;
import java.util.Map;

public class B {
	public B() {
	}
	
	public B(String s, List<Integer> is, Map<String, A> map) {
		this.s = s;
		this.is = is;
		this.map = map;
	}

	public String s;
	
	public List<Integer> is;
	
	public Map<String, A> map;
}
