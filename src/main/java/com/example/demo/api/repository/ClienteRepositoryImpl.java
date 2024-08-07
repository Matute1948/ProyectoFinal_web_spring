package com.example.demo.api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.api.repository.modelo.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Repository
@Transactional
public class ClienteRepositoryImpl implements IClienteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertar(Cliente cliente) {
		// TODO Auto-generated method stub
		this.entityManager.persist(cliente);
	}

	@Override
	public void actualizar(Cliente cliente) {
		// TODO Auto-generated method stub
		this.entityManager.merge(cliente);
	}

	@Override
	public void eliminar(Cliente cliente) {
		// TODO Auto-generated method stub
		this.entityManager.remove(cliente);
	}
	
	@Override
	public Cliente seleccionar(Integer id) {
		// TODO Auto-generated method stub
		return this.entityManager.find(Cliente.class, id);
	}

	@Override
	public List<Cliente> seleccionarCLientesApellido(String apellido) {
		// TODO Auto-generated method stub
		TypedQuery<Cliente> myQuery = this.entityManager
				.createQuery("SELECT c FROM Cliente c WHERE c.apellido = :apellido", Cliente.class);
		myQuery.setParameter("apellido", apellido);
		return myQuery.getResultList();
	}
	
	@Override
	@Transactional(value = TxType.MANDATORY)
	public void eliminarPorCedula(String cedula) {

		Cliente clienteAux = this.seleccionarPorCedula(cedula);
		this.entityManager.remove(clienteAux);

	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Cliente seleccionarPorCedula(String cedula) {

		TypedQuery<Cliente> query = this.entityManager.createQuery("SELECT c FROM Cliente c WHERE c.cedula =: datoCedula", Cliente.class);
		query.setParameter("datoCedula", cedula);	
		return query.getSingleResult();

	}

	@Override
	public List<Cliente> seleccionarTodos() {

		TypedQuery<Cliente> query = this.entityManager.createQuery("SELECT c FROM Cliente c ", Cliente.class);

		return query.getResultList();
	}



}
