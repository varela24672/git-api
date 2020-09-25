package com.tpa.git.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "admin", "push", "pull" })
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Permissions {

	@JsonProperty("admin")
	public Boolean admin;
	@JsonProperty("push")
	public Boolean push;
	@JsonProperty("pull")
	public Boolean pull;

}