package com.example.demo.api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.api.repository.dto.ResumenReservaDTO;
import com.example.demo.api.repository.modelo.Reservacion;
import com.example.demo.api.service.to.ResumenReservaTO;
import com.example.demo.api.repository.modelo.Cliente;
import com.example.demo.api.repository.modelo.Reservacion;
import com.example.demo.api.repository.modelo.Vehiculo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Repository
@Transactional
public class IReservacionRepositoryImpl implements IReservacionRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void insertar(Reservacion reservacion) {
		this.entityManager.persist(reservacion);
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<Reservacion> seleccionarFechasPorPlaca(String placa) {
		TypedQuery<Reservacion> query = this.entityManager
				.createQuery("SELECT r FROM Reserva r WHERE r.placaVehiculo =: datoPlaca", Reservacion.class);
		query.setParameter("datoPlaca", placa);
		return query.getResultList();
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Reservacion seleccionarPorId(Integer id) {
		return this.entityManager.find(Reservacion.class, id);
	}

	/*@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public ReservaDTO seleccionarPorIdDTO(Integer numReserva) {
		TypedQuery<ReservaDTO> query = this.entityManager.createQuery(
				"SELECT NEW com.example.demo.repository.modelo.dto.ReservaDTO(r.vehiculo.placa,r.vehiculo.modelo,r.vehiculo.estado,r.fechaDeInicio,r.fechaDeFin,r.cedulaCliente) FROM Reserva r WHERE r.id =: numReserva",
				ReservaDTO.class);
		query.setParameter("numReserva", numReserva);
		return query.getSingleResult();
	}*/

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(Reservacion reservacion) {
		this.entityManager.merge(reservacion);
	}

	// 3a
	public List<Reservacion> seleccionarValor(BigDecimal valorTotal) {

		TypedQuery<Reservacion> myQuery = this.entityManager.createQuery(
				"SELECT r FROM Reserva r WHERE r.valorTotalAPagar >= :datoValor and r.valorTotalAPagar >= :datoCondicion",
				Reservacion.class);
		myQuery.setParameter("datoValor", valorTotal);
		myQuery.setParameter("datoCondicion", 100.00);

		return myQuery.getResultList();
	}

	@Override
	public List<Reservacion> buscarPorPlacaVehiculoFechaInicioFin(String placaVehiculo, String cedula,
			LocalDate fechaInicio, LocalDate fechaFin) {
		String jpql = "SELECT r FROM Reservacion r WHERE r.vehiculo.placa = :placaVehiculo AND r.cliente.cedula =:cedula AND"
				+
				"((r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio))";

		TypedQuery<Reservacion> query = this.entityManager.createQuery(jpql, Reservacion.class);
		query.setParameter("placaVehiculo", placaVehiculo);
		query.setParameter("cedula", cedula);
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaFin", fechaFin);

		return query.getResultList();
	}

	public List<Reservacion> buscarReservasPorVehiculo(String placaVehiculo) {
		TypedQuery<Reservacion> query = entityManager.createQuery(
				"SELECT r FROM Reservacion r WHERE r.placaVehiculo = :placaVehiculo",
				Reservacion.class);
		query.setParameter("placaVehiculo", placaVehiculo);
		return query.getResultList();
	}

	@Override
	public ResumenReservaDTO seleccionarResumenPorPlaca(String placaVehiculo) {
		String jpql = "SELECT new com.example.demo.api.repository.dto.ResumenReservaDTO("
				+ "SUM(r.valorSubtotal), "
				+ "SUM(r.valorIVA), "
				+ "SUM(r.valorTotalAPagar)) "
				+ "FROM Reservacion r "
				+ "WHERE r.placaVehiculo = :placaVehiculo";

		TypedQuery<ResumenReservaDTO> query = this.entityManager.createQuery(jpql, ResumenReservaDTO.class);
		query.setParameter("placaVehiculo", placaVehiculo);

		return query.getSingleResult();
	}

	@Override
	public Reservacion seleccionarUltimaReservacion() {
		String jpql = "SELECT r FROM Reservacion r ORDER BY r.fechaDeInicio DESC";
		TypedQuery<Reservacion> query = this.entityManager.createQuery(jpql, Reservacion.class);
		query.setMaxResults(1); // Solo queremos la última reservación
		List<Reservacion> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
@Override
    public List<Reservacion> buscarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        TypedQuery<Reservacion> query = this.entityManager.createQuery(
            "SELECT r FROM Reservacion r WHERE r.fechaDeInicio >= :fechaInicio AND r.fechaDeFin <= :fechaFin",
            Reservacion.class
        );
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }


	//----------
	public List<Reservacion> buscarReservacionesConDetalles(LocalDate fechaInicio, LocalDate fechaFin) {
		String jpql = "SELECT r FROM Reservacion r " +
					  "JOIN r.vehiculo v " +
					  "JOIN r.cliente c " +
					  "WHERE r.fechaDeInicio >= :fechaInicio AND r.fechaDeFin <= :fechaFin";
	
		TypedQuery<Reservacion> query = this.entityManager.createQuery(jpql, Reservacion.class);
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaFin", fechaFin);
	
		return query.getResultList();
	}

	@Override
	public Reservacion seleccionarPorNumeroDeReserva(String numeroReserva) {
		
		TypedQuery<Reservacion> mQuery = this.entityManager
		.createQuery("SELECT r FROM Reservacion r WHERE r.numeroReserva =:numeroReserva", Reservacion.class);
		mQuery.setParameter("numeroReserva", numeroReserva);
		return mQuery.getSingleResult();
	}
	



	@Override
	public List<String> buscarCedulas() {

		TypedQuery<String> myQuery = this.entityManager.createQuery(
				"SELECT r.cedulaCliente FROM Reserva r GROUP BY r.cedulaCliente ORDER BY r.cedulaCliente",
				String.class);
		return myQuery.getResultList();
	}

	@Override

	public List<Reservacion> seleccionarPorPlacaYFechas(String pl, LocalDate fehaInicio, LocalDate fechaFin) {



		   String jpql = "SELECT r FROM Reserva r " +
		   "WHERE r.placaVehiculo = :datoPl AND("+
		   "(r.fechaDeInicio >= :datoInicio AND r.fechaDeInicio <= :datoFin) " +
		   "OR (r.fechaDeFin >= :datoInicio AND r.fechaDeFin <= :datoFin) " +
		   "OR (r.fechaDeInicio <= :datoInicio AND r.fechaDeFin >= :datoInicio) " +
		   "OR (r.fechaDeInicio <= :datoFin AND r.fechaDeFin >= :datoFin)) " ;


		   TypedQuery<Reservacion> myQuery = this.entityManager.createQuery(jpql,
		   Reservacion.class);
		   myQuery.setParameter("datoInicio", fehaInicio);
		   myQuery.setParameter("datoFin", fechaFin);
		   myQuery.setParameter("datoPl", pl);

			List<Reservacion> reservacions = myQuery.getResultList(); 


		return reservacions;
	}

	@Override
	public List<Reservacion> seleccionarTodo() {
		TypedQuery<Reservacion> myQuery = this.entityManager.createQuery(
				"SELECT r FROM Reserva r ",
				Reservacion.class);

		return myQuery.getResultList();
	}


	@Override
    public LocalDate seleccionarPorPlacaUltimaFecha(String placa) {
        TypedQuery<Reservacion> mTypedQuery = this.entityManager.createQuery("SELECT r FROM Reserva r WHERE r.placaVehiculo =:datoPlacaVehiculo", Reservacion.class);
        mTypedQuery.setParameter("datoPlacaVehiculo", placa);
        List<LocalDate> listaFecha = new ArrayList<>();



        for (Reservacion reserIt : mTypedQuery.getResultList()) {
            listaFecha.add(reserIt.getFechaDeFin());
        }
        Collections.sort(listaFecha,Collections.reverseOrder());

        return listaFecha.get(0);
    }

}
