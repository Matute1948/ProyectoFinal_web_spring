package com.example.demo.api.service;

import java.util.List;

import com.example.demo.api.repository.modelo.Cliente;
import com.example.demo.api.service.to.ClienteTO;

public interface IClienteService {

	public void guardar(Cliente cliente);

	public void actualizar(Cliente cliente);

	public void borrar(Cliente cliente);

	public ClienteTO seleccionar(Integer id);
	
	public Cliente seleccionarC(Integer id);

	// ESTUDIANTE TO ************

	public List<ClienteTO> buscarCLientesApellidoTO(String apellido);
	
	public Cliente convertirTO(ClienteTO cleinetTO) ;

	//****************************************
	public void borrarPorCedula(String cedula);

	public Cliente buscarPorCedula(String cedula);
}
