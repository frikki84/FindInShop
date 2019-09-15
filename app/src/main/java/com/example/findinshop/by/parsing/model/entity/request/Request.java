package com.example.findinshop.by.parsing.model.entity.request;

public class Request {
	public static final String DEFAULT_REQUEST = " ";
	
	
	private String request;
	
	public Request() {
		request = DEFAULT_REQUEST;
	}
	
	public Request(String request) {
		this.request = request;
	}
	
	public Request(Request request) {
		this.request = request.request;
	}
	
	public String getRequest() {
		return request;
	}
	
	public void setRequest(String request) {
		this.request = request;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [request=" + request + "]";
	}
	
	
	
	
	

}
