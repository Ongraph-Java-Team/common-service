package com.ongraph.commonserviceapp.security.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ongraph.commonserviceapp.model.DataResponse;
import com.ongraph.commonserviceapp.model.ErrorCodes;
import com.ongraph.commonserviceapp.model.ErrorDetails;
import com.ongraph.commonserviceapp.util.LoggerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthEntryPoint implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		log.error("Unauthorized error: {}", LoggerHelper.printStackTrace(authException));

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		DataResponse dataResponse = new DataResponse(
				new ErrorDetails(ErrorCodes.E_AUTH401.name(), authException.getMessage()));
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), dataResponse);

	}
}
