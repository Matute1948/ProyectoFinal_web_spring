package com.example.demo.api.repository;

import java.util.List;

import com.example.demo.api.repository.modelo.Vehiculo;

public interface IVehiculoRepository {

	public void ingresar(Vehiculo vehiculo);

	public void actualizar(Vehiculo vehiculo);

	public void eliminarPorPlaca(String placa);

	public Vehiculo seleccionarPorPlaca(String placa);

	public List<Vehiculo> seleccionarPorMarca(String marca);

	public List<Vehiculo> seleccionarTodos();

	public List<Vehiculo> seleccionarPorMarcaModelo(String marca, String modelo);

}
