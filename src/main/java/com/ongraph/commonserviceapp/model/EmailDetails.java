package com.ongraph.commonserviceapp.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmailDetails {
	private String recipient;
	private Map<String,Object> variables;
}
