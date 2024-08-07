package com.example.demo.api.service;

import java.util.List;

import com.example.demo.api.repository.modelo.Vehiculo;
import com.example.demo.api.service.to.VehiculoTO;

public interface IVehiculoService {

	public void agregar(Vehiculo vehiculo );

	public void actualizar(Vehiculo vehiculo);

	public void eliminarPorPlaca(String placa);

	public Vehiculo buscarPorPlaca(String placa);

	public List<Vehiculo> buscarPorMarca(String marca);

	//METODO PAR ACTUALZIAR ESTADO DEL VEHICULO
	public void actualizarEstado(String placa, String nuevoEstado);

	//BUSCAMOS TODOS LOS VEHICULOS
	public List<Vehiculo> buscarTodos();

	//public ReservaDTO retirarReserva(Integer numReserva);

		public List<VehiculoTO> buscarPorMarcaModelo(String marca, String modelo);

}
