package com.example.demo.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.repository.IVehiculoRepository;
import com.example.demo.api.repository.modelo.Vehiculo;
import com.example.demo.api.service.to.VehiculoTO;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class IVehiculoServiceImpl implements IVehiculoService {

	@Autowired
	private IVehiculoRepository iVehiculoRepository;
	
	@Override
	@Transactional(value = TxType.REQUIRED)
	public void agregar(Vehiculo vehiculo) {

		this.iVehiculoRepository.ingresar(vehiculo);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void actualizar(Vehiculo vehiculo) {
		this.iVehiculoRepository.actualizar(vehiculo);

	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void eliminarPorPlaca(String placa) {
		this.iVehiculoRepository.eliminarPorPlaca(placa);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public Vehiculo buscarPorPlaca(String placa) {
		return this.iVehiculoRepository.seleccionarPorPlaca(placa);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void actualizarEstado(String placa, String nuevoEstado) {
		Vehiculo vehiculoAuxiliar = this.iVehiculoRepository.seleccionarPorPlaca(placa);
		vehiculoAuxiliar.setEstado(nuevoEstado);
		this.iVehiculoRepository.actualizar(vehiculoAuxiliar);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public List<Vehiculo> buscarTodos() {
		return this.iVehiculoRepository.seleccionarTodos();
	}

	

	@Override
	@Transactional(value = TxType.REQUIRED)
	public List<VehiculoTO> buscarPorMarcaModelo(String marca, String modelo) {
		return convertirListaTO(this.iVehiculoRepository.seleccionarPorMarcaModelo(marca, modelo));
	}

	 private List<VehiculoTO> convertirListaTO(List<Vehiculo> vehiculos) {
        List<VehiculoTO> vehiculoTOs = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            vehiculoTOs.add(convertirTO(vehiculo));
        }
        return vehiculoTOs;
    }
		private VehiculoTO convertirTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }

        return new VehiculoTO(
            vehiculo.getId(),
			vehiculo.getPlaca(),
			vehiculo.getModelo(),
            vehiculo.getMarca(),
            vehiculo.getAnio(),
            vehiculo.getEstado(),
            vehiculo.getPaisOrigen(),
			vehiculo.getCilindraje(),
			vehiculo.getAvaluo(),
			vehiculo.getValorPorDia()
        );
    }

	
	@Transactional(value = TxType.REQUIRED)
	public List<Vehiculo> buscarPorMarca(String marca) {
		return this.iVehiculoRepository.seleccionarPorMarca(marca);
	}

	/*@Override
	@Transactional(value = TxType.REQUIRED)
	public ReservaDTO retirarReserva(Integer numReserva) {
		
		Reservacion reservacion = this.iReservaRepository.seleccionarPorId(numReserva);
		reservacion.setEstado("Ejecutado");
		Vehiculo vehiculo = reservacion.getVehiculo();
		vehiculo.setEstado("Indisponible");
		this.iVehiculoRepository.actualizar(vehiculo);
		this.iReservaRepository.actualizar(reservacion);
		return this.iReservaRepository.seleccionarPorIdDTO(numReserva);
	}*/

}
