package com.example.demo.api.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.api.repository.modelo.Pago;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class PagoRepositoryImpl implements IPagoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(Pago pago) {
        this.entityManager.persist(pago);
    }
 


    
}
