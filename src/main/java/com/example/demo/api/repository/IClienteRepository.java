package com.example.demo.api.repository;

import java.util.List;

import com.example.demo.api.repository.modelo.Cliente;

public interface IClienteRepository {
	
	public void insertar(Cliente cliente);
	
	public void actualizar(Cliente cliente);
	
	public void eliminar(Cliente cliente);
	
	public Cliente seleccionar(Integer id);
	
	public List<Cliente> seleccionarCLientesApellido(String apellido);
	
	public void eliminarPorCedula(String cedula);

	public Cliente seleccionarPorCedula(String cedula);

	public List<Cliente> seleccionarTodos();

}
