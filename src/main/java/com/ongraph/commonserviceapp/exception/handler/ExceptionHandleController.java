package com.ongraph.commonserviceapp.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ongraph.commonserviceapp.exception.CustomException;
import com.ongraph.commonserviceapp.model.DataResponse;
import com.ongraph.commonserviceapp.model.ErrorCodes;
import com.ongraph.commonserviceapp.model.ErrorDetails;
import com.ongraph.commonserviceapp.util.LoggerHelper;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandleController {
	
	@ExceptionHandler({CustomException.class})
	@ResponseStatus(HttpStatus.ALREADY_REPORTED)
	public ResponseEntity<DataResponse> handleCustomException(CustomException customException){
		log.error("handleCustomException()->\nexception:{}",LoggerHelper.printStackTrace(customException,5));
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(
				new DataResponse(
						new ErrorDetails(customException.getErrorCode().name(), customException.getMessage()))
				);
				
	}
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.ALREADY_REPORTED)
	public ResponseEntity<DataResponse> handleGlobalException(Exception exception){
		log.error("handleGlobalException()->\nexception:{}",LoggerHelper.printStackTrace(exception,20));
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(
				new DataResponse(
						new ErrorDetails(ErrorCodes.E_ISE500.name(), exception.getMessage()))
				);
				
	}
}
