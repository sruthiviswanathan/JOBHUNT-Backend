package com.zilker.onlinejobsearch.utils;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.ErrorResponse;
import com.zilker.onlinejobsearch.beans.ResponseAsArray;
import com.zilker.onlinejobsearch.beans.ResponseAsObject;
import com.zilker.onlinejobsearch.beans.ResponseMessage;
import com.zilker.onlinejobsearch.customException.ApplicationException;

@Service
public class ResponseGeneratorUtil {

	// Generates the successResponse and returns the ResponseEntity object
		public <T> ResponseEntity<?> successResponse(T body) {
			ResponseAsObject<T> response = new ResponseAsObject<>();
			response.setIsSuccess(true);
			response.setResponseBody(body);
			return ResponseEntity.ok().body(response);
		}

		// Generates the successResponse and returns the ResponseAsArray
		public <T> ResponseEntity<?> successResponse(List<?> body) {
			ResponseAsArray response = new ResponseAsArray();
			response.setIsSuccess(true);
			response.setResponseBody(body);
			return ResponseEntity.ok().body(response);
		}

		// Generates the errorResponse and returns the ResponseEntity object
		public ResponseEntity<?> errorResponse(String errorCode, String errorDescription) {
			ResponseAsObject<ErrorResponse> response = new ResponseAsObject<>();
			ErrorResponse errorBody = new ErrorResponse(errorCode, errorDescription);
			response.setIsSuccess(false);
			response.setResponseBody(errorBody);
			return ResponseEntity.ok().body(response);
		}
		
		public ResponseEntity<?> errorResponse(ApplicationException ex) {
			ResponseAsObject<ErrorResponse> response = new ResponseAsObject<>();
			ErrorResponse errorBody = new ErrorResponse(ex.getErrorCode(),ex.getErrorMessage(),ex.getErrorData());
			response.setIsSuccess(false);
			response.setResponseBody(errorBody);
			return ResponseEntity.ok().body(response);
		}

		public ResponseEntity<?> generateMessage(String message) {
			ResponseAsObject<ResponseMessage> response = new ResponseAsObject<>();
			ResponseMessage responseMessage = new ResponseMessage(message);
			response.setIsSuccess(true);
			response.setResponseBody(responseMessage);
			return ResponseEntity.ok().body(response);
		}

	
}
