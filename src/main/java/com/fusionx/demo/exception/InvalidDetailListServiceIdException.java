package com.fusionx.demo.exception;

import com.fusionx.demo.enums.ActionType;
import com.fusionx.demo.enums.ServiceEntity;

public class InvalidDetailListServiceIdException extends RuntimeException {

	private static final long serialVersionUID = 3171872881842320594L;

	private final ServiceEntity serviceEntity;
	private final ActionType actionType;
	private final Integer index;
	public InvalidDetailListServiceIdException(String exception, ServiceEntity serviceEntity, ActionType actionType, Integer index) {
		super(exception);
		this.serviceEntity=serviceEntity;
		this.actionType=actionType;
		this.index=index;
	}
	public ServiceEntity getServiceEntity() {
		return serviceEntity;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public Integer getIndex() {
		return index;
	}
}
