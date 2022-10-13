package com.geopagos.toumament.model.jwt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JwtRequest {

	@NotNull
	@NotBlank
    private String username;
	
	@NotNull
	@NotBlank
    private String password;

}
