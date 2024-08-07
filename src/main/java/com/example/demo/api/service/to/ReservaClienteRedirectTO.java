package com.example.demo.api.service.to;

import com.example.demo.api.repository.modelo.Reservacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservaClienteRedirectTO {

    Reservacion reservacion;

    String redirect;

	public Reservacion getReserva() {
		return reservacion;
	}

	public void setReserva(Reservacion reservacion) {
		this.reservacion = reservacion;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}




}
