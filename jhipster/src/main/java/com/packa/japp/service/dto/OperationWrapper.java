package com.packa.japp.service.dto;

public class OperationWrapper<T> {

	private T operationResult;
	private OperationStatusEnum status;
	private String message;

	public T getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(T operationResult) {
		this.operationResult = operationResult;
	}

	public OperationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OperationStatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
