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
    @Transactional(value = TxType.MANDATORY)
    public void insertar(Cliente cliente) {
        this.entityManager.persist(cliente);
    }

    @Override
    @Transactional(value = TxType.MANDATORY)
    public void actualizar(Cliente cliente) {
        this.entityManager.merge(cliente);
    }

    @Override
    @Transactional(value = TxType.MANDATORY)
    public void eliminar(Cliente cliente) {
        this.entityManager.remove(cliente);
    }

    @Override
    @Transactional(value = TxType.NOT_SUPPORTED)
    public Cliente seleccionar(Integer id) {
        return this.entityManager.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> seleccionarCLientesApellido(String apellido) {
        TypedQuery<Cliente> query = this.entityManager.createQuery(
            "SELECT c FROM Cliente c WHERE LOWER(c.apellido) = LOWER(:datoApellido)", 
            Cliente.class
        );
        query.setParameter("datoApellido", apellido.toLowerCase());
        return query.getResultList();
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
        TypedQuery<Cliente> query = this.entityManager.createQuery(
            "SELECT c FROM Cliente c WHERE LOWER(c.cedula) = LOWER(:datoCedula)", 
            Cliente.class
        );
        query.setParameter("datoCedula", cedula.toLowerCase());
        return query.getSingleResult();
    }

    @Override
    public List<Cliente> seleccionarTodos() {
        TypedQuery<Cliente> query = this.entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }
}
