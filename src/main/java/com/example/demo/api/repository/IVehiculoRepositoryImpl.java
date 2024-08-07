package com.example.demo.api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.api.repository.modelo.Vehiculo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Repository
@Transactional
public class IVehiculoRepositoryImpl implements IVehiculoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void ingresar(Vehiculo vehiculo) {
		this.entityManager.persist(vehiculo);

	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(Vehiculo vehiculo) {

		this.entityManager.merge(vehiculo);
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void eliminarPorPlaca(String placa) {
		Vehiculo vehiculoAux = this.seleccionarPorPlaca(placa);
		this.entityManager.remove(vehiculoAux);

	}

	@Override
@Transactional(value = TxType.NOT_SUPPORTED)
public Vehiculo seleccionarPorPlaca(String placa) {
    TypedQuery<Vehiculo> query = this.entityManager.createQuery(
        "SELECT v FROM Vehiculo v WHERE LOWER(v.placa) = LOWER(:datoPlaca)", 
        Vehiculo.class
    );
    query.setParameter("datoPlaca", placa.toLowerCase());
    return query.getSingleResult();
}

@Override
public List<Vehiculo> seleccionarPorMarca(String marca) {
    TypedQuery<Vehiculo> query = this.entityManager.createQuery(
        "SELECT v FROM Vehiculo v WHERE LOWER(v.marca) = LOWER(:datoMarca)", 
        Vehiculo.class
    );
    query.setParameter("datoMarca", marca.toLowerCase());
    return query.getResultList();
}


	@Override
	public List<Vehiculo> seleccionarTodos() {
		TypedQuery<Vehiculo> myQuery = this.entityManager.createQuery("SELECT v FROM Vehiculo v", Vehiculo.class);

		return myQuery.getResultList();
	}

	

	@Override
	public List<Vehiculo> seleccionarPorMarcaModelo(String marca, String modelo) {
		TypedQuery<Vehiculo> query = this.entityManager.createQuery(
			"SELECT v FROM Vehiculo v WHERE LOWER(v.marca) = LOWER(:datoMarca) AND LOWER(v.modelo) = LOWER(:datoModelo)", 
			Vehiculo.class
		);
		query.setParameter("datoMarca", marca.toLowerCase());
		query.setParameter("datoModelo", modelo.toLowerCase());
		return query.getResultList();
	}


	

}
