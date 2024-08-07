package com.example.demo.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.repository.IClienteRepository;
import com.example.demo.api.repository.IReservacionRepository;
import com.example.demo.api.repository.IVehiculoRepository;
import com.example.demo.api.repository.dto.ReservaRespuestaDTO;
import com.example.demo.api.repository.dto.ResumenReservaDTO;
import com.example.demo.api.repository.modelo.Cliente;
import com.example.demo.api.repository.modelo.Reservacion;
import com.example.demo.api.repository.modelo.Vehiculo;
import com.example.demo.api.service.to.ReservacionTO;
import com.example.demo.api.service.to.RespuestaReservaTO;
import com.example.demo.api.service.to.ResumenReservaTO;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
@Transactional
public class IReservacionServiceImpl implements IReservacionService {

	@Autowired
	private IReservacionRepository reservacionRepository;
	@Autowired
	private IVehiculoRepository vehiculoRepository;
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void agregar(ReservacionTO reservacion) {

		reservacionRepository.insertar(this.convertir(reservacion));
	}


	@Override
public RespuestaReservaTO reservarVehiculo(String placa, String cedulaCliente, LocalDate fechaInicio, LocalDate fechaFin) {
    // Buscar el vehículo por la placa
    Vehiculo vehiculo = vehiculoRepository.seleccionarPorPlaca(placa);
    if (vehiculo == null) {
        return new RespuestaReservaTO(false, "Vehículo no encontrado", null);
    }

    Cliente cliente = clienteRepository.seleccionarPorCedula(cedulaCliente);
    if (cliente == null) {
        return new RespuestaReservaTO(false, "Cliente no encontrado", null);
    }

    // Verificar si el vehículo ya tiene una reserva en las fechas dadas
    List<Reservacion> reservasExistentes = reservacionRepository.buscarReservasPorVehiculo(placa);
    for (Reservacion reserva : reservasExistentes) {
        if (fechaInicio.isBefore(reserva.getFechaDeFin()) && fechaFin.isAfter(reserva.getFechaDeInicio())) {
            // El vehículo no está disponible, devolver la fecha en que estará disponible
            return new RespuestaReservaTO(false, "El vehículo no está disponible.", reserva.getFechaDeFin().plusDays(1));
        }
    }

    // Crear la nueva reservación
    Reservacion reservacion = new Reservacion();
    reservacion.setVehiculo(vehiculo);
    reservacion.setCliente(cliente);
    reservacion.setFechaDeInicio(fechaInicio);
    reservacion.setFechaDeFin(fechaFin);
    reservacion.setCedulaCliente(cedulaCliente);
    reservacion.setPlacaVehiculo(placa);

    long numDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;

    BigDecimal valorPorDia = vehiculo.getValorPorDia();
    BigDecimal valorSubTotal = valorPorDia.multiply(BigDecimal.valueOf(numDias));
    BigDecimal valorIVA = valorSubTotal.multiply(BigDecimal.valueOf(0.12));
    BigDecimal valorTotalAPagar = valorSubTotal.add(valorIVA);

    reservacion.setValorSubtotal(valorSubTotal);
    reservacion.setValorIVA(valorIVA);
    reservacion.setValorTotalAPagar(valorTotalAPagar);
    reservacion.setEstado("Generada");

    // Generar el número de reserva
    String numeroReserva = generarNumeroReserva();
    reservacion.setNumeroReserva(numeroReserva);

    reservacionRepository.insertar(reservacion);

    return new RespuestaReservaTO(true, "Reserva realizada con éxito.", null);
}

public void agregar(Reservacion reservacion) {
	this.reservacionRepository.insertar(reservacion);
}



private String generarNumeroReserva() {
    return "RES" + System.currentTimeMillis(); // Ejemplo de generación de número de reserva
}


	private List<ReservacionTO> convertirListaTO(List<Reservacion> reservaciones) {
		List<ReservacionTO> reservacionTOs = new ArrayList<>();
		for (Reservacion reservacion : reservaciones) {
			reservacionTOs.add(convertirTO(reservacion));
		}
		return  reservacionTOs;
	}

	private ReservacionTO convertirTO(Reservacion reservacion) {
		if (reservacion == null) {
			return null;
		}
	
		return new ReservacionTO(
			reservacion.getId(),
			reservacion.getFechaDeInicio(),
			reservacion.getFechaDeFin(),
			reservacion.getEstado(),
			reservacion.getPlacaVehiculo(),
			reservacion.getVehiculo() != null ? reservacion.getVehiculo().getMarca() : null,
			reservacion.getVehiculo() != null ? reservacion.getVehiculo().getModelo() : null,
			
			reservacion.getCliente() != null ? reservacion.getCliente().getNombre() : null,
			
			reservacion.getCedulaCliente(),
			
			reservacion.getValorSubtotal(),
			reservacion.getValorIVA(),
			reservacion.getValorTotalAPagar(),
			
            reservacion.getNumeroReserva()
        );
    }
		


	private Reservacion convertir(ReservacionTO reservacionTO) {
        Reservacion reservacion = new Reservacion();
        reservacion.setId(reservacionTO.getId());
        reservacion.setPlacaVehiculo(reservacionTO.getPlacaVehiculo());
		reservacion.setMarcaVehiculo(reservacionTO.getMarca());
		reservacion.setModeloVehiculo(reservacionTO.getModelo());
        reservacion.setCedulaCliente(reservacionTO.getCedulaCliente());
		reservacion.setNombreCliente(reservacionTO.getNombreCliente());
        reservacion.setFechaDeInicio(reservacionTO.getFechaDeInicio());
        reservacion.setFechaDeFin(reservacionTO.getFechaDeFin());
        reservacion.setValorSubtotal(reservacionTO.getValorSubtotal());
        reservacion.setValorIVA(reservacionTO.getValorIVA());
        reservacion.setValorTotalAPagar(reservacionTO.getValorTotalAPagar());
        reservacion.setEstado(reservacionTO.getEstado());


        return reservacion;
    }

	@Override
	public ResumenReservaTO obtenerResumenPorPlaca(String placaVehiculo) {
	return this.convertirDTO(this.reservacionRepository.seleccionarResumenPorPlaca(placaVehiculo));
}

private ResumenReservaTO convertirDTO(ResumenReservaDTO resumenReservaDTO){
	if (resumenReservaDTO == null) {
		return null;
	}

	return new ResumenReservaTO(
		resumenReservaDTO.getValorSubtotal(),
		resumenReservaDTO.getValorIVA(),
		resumenReservaDTO.getValorTotalAPagar()
	
	);

}

	public List<ReservacionTO> buscarReservasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
		List<Reservacion> reservaciones = reservacionRepository.buscarReservacionesConDetalles(fechaInicio, fechaFin);
		
		// Imprimir los datos para depuración
		for (Reservacion reservacion : reservaciones) {
			System.out.println("Reservacion ID: " + reservacion.getId());
			System.out.println("Vehiculo Marca: " + (reservacion.getVehiculo() != null ? reservacion.getVehiculo().getMarca() : "null"));
			System.out.println("Cliente Nombre: " + (reservacion.getCliente() != null ? reservacion.getCliente().getNombre() : "null"));
		}
	
		return reservaciones.stream()
			.map(this::convertirTO)
			.collect(Collectors.toList());
	}

	//--------------
	@Override
    public ReservacionTO retirarVehiculoReservado(String numeroReserva) {

		Reservacion reservacion = reservacionRepository.seleccionarPorNumeroDeReserva(numeroReserva);
        
        if (reservacion == null) {
            throw new RuntimeException("Reservación no encontrada");
        }

        // Cambiar el estado del vehículo a "No Disponible"
        reservacion.getVehiculo().setEstado("ND");

        // Cambiar el estado de la reservación a "Ejecutada"
        reservacion.setEstado("E");

        // Guardar los cambios en la base de datos
        reservacionRepository.actualizar(reservacion);

        // Retornar la reservación actualizada
        return convertirTO(reservacion);
    }

	@Override
	public ReservacionTO buscarPorNumeroDeReserva(String numeroReserva) {

		return this.convertirTO(this.reservacionRepository.seleccionarPorNumeroDeReserva(numeroReserva));
		
	}

	
	@Override
	public ReservacionTO buscarPorId(Integer Id) {
		return this.convertirTO( this.reservacionRepository.seleccionarPorId(Id));
	}

	@Override
	public List<ReservacionTO> buscarTodo() {
		throw new UnsupportedOperationException("Unimplemented method 'buscarTodo'");
	}
	

	

	
	

	/*@Override
	@Transactional(value = TxType.REQUIRED)
	public ReservaDTO buscarPorNumReserva(Integer numFactura) {
		return this.iReservaRepository.seleccionarPorIdDTO(numFactura);
	}
	@Override
	public List<Reservacion> reportesValor(BigDecimal valorTotal) {
		return this.iReservaRepository.seleccionarValor(valorTotal);
	}
	@Override
	public List<ReservaDTO2> reportesClientesVIP() {
		List<Reservacion> clientesOrdenados = this.iReservaRepository.seleccionClientesVIP();
		List<String> numerosCedula = this.iReservaRepository.buscarCedulas();
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		List<ReservaDTO2> lista = new ArrayList<>();
		ReservaDTO2 res = null;
		String cedula = "";
		String nombre = "";
		Cliente cliente;
		for (String r : numerosCedula) {
			for (Reservacion c : clientesOrdenados) {
				if (r.equals(c.getCedulaCliente())) {
					subtotal = subtotal.add(c.getValorSubtotal());
					total = total.add(c.getValorTotalAPagar());
					cedula = c.getCedulaCliente();
					cliente = this.clienteService.buscarPorCedula(cedula);
					nombre = cliente.getApellido();
				}
				res = ReservaDTO2.builder().cedula(cedula).nombre(nombre).subtotal(subtotal).total(total).build();
			}
			lista.add(res);
			subtotal = new BigDecimal(0);
			total = new BigDecimal(0);
		}
		return lista;
	}
	@Override
	public List<ReservaDTO3> reporteVehiculosVIP(String mes, String anio) {
		LocalDate fechaInicio = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), 1);
		LocalDate fechaFin = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), 31);
		List<Reservacion> listaVehiculosEnFecha = this.reportesValor(null);
		List<String> listaPlacaVehiculos = this.iReservaRepository.seleccionPlacasVehiculos();
		List<ReservaDTO3> lista = new ArrayList<>();
		ReservaDTO3 res = null;
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		String placa = "";
		for (String p : listaPlacaVehiculos) {
			for (Reservacion c : listaVehiculosEnFecha) {
				if (p.equals(c.getPlacaVehiculo())) {
					subtotal = subtotal.add(c.getValorSubtotal());
					total = total.add(c.getValorTotalAPagar());
					placa = c.getPlacaVehiculo();
				}
				res = ReservaDTO3.builder().placa(placa).subtotal(subtotal).total(total).build();
			}
			lista.add(res);
		}
		return lista;
	}
	@Override
	public List<VehiculosVIPDTO> reporteVehiculosVIP2(String mes, String anio) {
		LocalDate fechaInicio = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), 1);
		LocalDate fechaFin = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), fechaInicio.lengthOfMonth());
		List<VehiculosVIPDTO> vips = iVehiculoRepository.seleccionarTodos().parallelStream().map(
				vehiculo -> {
					List<Reservacion> reservacions = vehiculo.getReservasV().stream()
							.filter(reserva -> (fechaInicio.isBefore(reserva.getFechaDeInicio()))
									&& fechaFin.isAfter(reserva.getFechaDeFin()))
							.collect(Collectors.toList());
					;
					BigDecimal total = reservacions.stream().map(Reservacion::getValorTotalAPagar)
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					BigDecimal subTotal = reservacions.stream().map(Reservacion::getValorSubtotal)
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					VehiculosVIPDTO vip = new VehiculosVIPDTO();
					vip.setTotal(total);
					vip.setSubtotal(subTotal);
					vip.setVehiculo(vehiculo);
					return vip;
				}).collect(Collectors.toList());
		Collections.sort(vips, new Comparator<VehiculosVIPDTO>() {
			public int compare(VehiculosVIPDTO r1, VehiculosVIPDTO r2) {
				return r1.getTotal().compareTo(r2.getTotal());
			};
		});
		Collections.reverse(vips);
		return vips;
	}
	@Override
	public List<ReservaDTO2> reporteClientesVIP2() {
		List<Cliente> listaClientes = this.clienteRepository.seleccionarTodos();
		List<ReservaDTO2> reservas = listaClientes.parallelStream().map(cliente -> {
			BigDecimal total = cliente.getReservasC().stream().map(r -> r.getValorTotalAPagar()).reduce(BigDecimal.ZERO,
					BigDecimal::add);
			BigDecimal subTotal = cliente.getReservasC().stream().map(r -> r.getValorSubtotal()).reduce(BigDecimal.ZERO,
					BigDecimal::add);
			return ReservaDTO2.builder().cedula(cliente.getCedula()).total(total).subtotal(subTotal)
					.nombre(cliente.getNombre()).build();
		}).collect(Collectors.toList());
		Collections.sort(reservas, new Comparator<ReservaDTO2>() {
			public int compare(ReservaDTO2 r1, ReservaDTO2 r2) {
				return r1.getTotal().compareTo(r2.getTotal());
			};
		});
		Collections.reverse(reservas); // Orden inverso por la vista
		return reservas;
	}
	@Override
	@Transactional(TxType.NOT_SUPPORTED)
	public boolean estaDisponible(String placa, LocalDate fechaInicio, LocalDate fechaFin) {
		boolean estaDisponible = true;
		List<Reservacion> reservacions = new ArrayList<Reservacion>();
		try {
			reservacions = iReservaRepository.seleccionarPorPlacaYFechas(placa, fechaInicio, fechaFin);
		} catch (Exception e) {
			estaDisponible = true;
		}
		estaDisponible = reservacions.size() == 0;
		return estaDisponible;
	}
	@Override
	public ReservaClienteRedirectDTO reservarVehiculo(ReservaClienteVehiculoDTO dto) {
		String redirect = "redirect:/clientes/no-disponible";
		Reservacion reservacion = new Reservacion();
		if (this.estaDisponible(dto.getPlacaVehiculo(), dto.getFechaInicio(), dto.getFechaFin())) {
			redirect = "redirect:/clientes/cobro";
			Vehiculo vehiculo = this.iVehiculoRepository.seleccionarPorPlaca(dto.getPlacaVehiculo());
			Cliente cliente = this.clienteService.buscarPorCedula(dto.getCedulaCliente());
			long tiempo = dto.getFechaInicio().until(dto.getFechaFin(), ChronoUnit.DAYS);
			BigDecimal valorSubTotal = vehiculo.getValorPorDia().multiply(new BigDecimal(tiempo));
			BigDecimal valorIce = (valorSubTotal.multiply(new BigDecimal(15)).divide(new BigDecimal(100)));
			BigDecimal valorTotalPagar = valorSubTotal.add(valorIce);
			reservacion = Reservacion.builder().cedulaCliente(dto.getCedulaCliente()).cliente(cliente).estado("G")
					.fechaDeInicio(dto.getFechaInicio()).fechaDeFin(dto.getFechaFin()).vehiculo(vehiculo)
					.placaVehiculo(dto.getPlacaVehiculo()).valorSubtotal(valorSubTotal).valorIEC(valorIce)
					.valorTotalAPagar(valorTotalPagar).build();
		}
		ReservaClienteRedirectDTO dto2 = new ReservaClienteRedirectDTO(reservacion, redirect);
		return dto2;
	}*/



	//---------------------
	@Override
    @Transactional(value = TxType.REQUIRED)
    public void retirarVehiculoReservado(Integer reservaId) {
        // Buscar la reserva por ID
        Reservacion reservacion = reservacionRepository.seleccionarPorId(reservaId);
        
        if (reservacion != null) {
            // Obtener el vehículo asociado a la reserva
            Vehiculo vehiculo = reservacion.getVehiculo();
            
            if (vehiculo != null) {
                // Cambiar el estado del vehículo a "No Disponible" (ND)
                vehiculo.setEstado("ND");
                vehiculoRepository.actualizar(vehiculo);
            }

            // Cambiar el estado de la reserva a "Ejecutada" (E)
            reservacion.setEstado("E");
            reservacionRepository.actualizar(reservacion);
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + reservaId);
        }
    }


	@Override
	public LocalDate buscarPorPlacaUltimaFecha(String placa) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'buscarPorPlacaUltimaFecha'");
	}

}
