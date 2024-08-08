package com.example.demo.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.api.repository.IClienteRepository;
import com.example.demo.api.repository.modelo.Cliente;
import com.example.demo.api.service.to.ClienteTO;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
@Transactional
public class ClienteServiceImpl implements IClienteService{
	@Autowired
	private IClienteRepository repository;

	@Override
	public void guardar(Cliente cliente) {
		this.repository.insertar(cliente);
	}

	@Override
	public void actualizar(Cliente cliente) {
		this.repository.actualizar(cliente);
	}

	@Override
	public void borrar(Cliente cliente) {
		this.repository.eliminar(cliente);
	}

	@Override
	public ClienteTO seleccionar(Integer id) {
		return this.convertir(this.repository.seleccionar(id));
	}


	//EstudianteTO *******************************************************************

	public ClienteTO convertir(Cliente cliente) {
		ClienteTO cTO = new ClienteTO();
		cTO.setId(cliente.getId());
		cTO.setApellido(cliente.getApellido());
		cTO.setCedula(cliente.getCedula());
		cTO.setFechaNacimiento(cliente.getFechaNacimiento());
		cTO.setGenero(cliente.getGenero());
		cTO.setNombre(cliente.getNombre());
		cTO.setRegistro(cliente.getRegistro());
		
		return cTO;
	}
	
	@Override
	public List<ClienteTO> buscarCLientesApellidoTO(String apellido) {
		List<Cliente> lsCliente = this.repository.seleccionarCLientesApellido(apellido);
		List<ClienteTO> lsTO = new ArrayList<>();
		
		for (Cliente cliente : lsCliente) {
			lsTO.add(this.convertir(cliente));
		}
		
		return lsTO;
	}
	
	@Override
	@Transactional(value = TxType.REQUIRED)
	public void borrarPorCedula(String cedula) {

		this.repository.eliminarPorCedula(cedula);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public Cliente buscarPorCedula(String cedula) {
 
		try {
	        return this.repository.seleccionarPorCedula(cedula);
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}

	@Override
	public Cliente seleccionarC(Integer id) {
		return this.repository.seleccionar(id);
	}

	@Override
	public Cliente convertirTO(ClienteTO cleinetTO) {
		throw new UnsupportedOperationException("Unimplemented method 'convertirTO'");
	}


}
