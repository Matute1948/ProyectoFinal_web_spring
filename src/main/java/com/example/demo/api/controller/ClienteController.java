package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.repository.modelo.Cliente;
import com.example.demo.api.service.IClienteService;
import com.example.demo.api.service.to.ClienteTO;

@CrossOrigin
@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

   
  

// http://localhost:8082/API/v1.0/Concesionario/clientes
@PostMapping(path = "/empleado", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) {
    this.clienteService.guardar(cliente);
    HttpHeaders headers = new HttpHeaders();
    headers.add("mensaje_201", "Insercion de un recursos");

    return new ResponseEntity<>(cliente, headers, 236);
}

// http://localhost:8082/API/v1.0/Concesionario/clientes/
@PutMapping(path = "/empleado/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Cliente> actualizar(@RequestBody Cliente cliente, @PathVariable Integer id) {
    cliente.setId(id);
    this.clienteService.actualizar(cliente);
    HttpHeaders headers = new HttpHeaders();
    headers.add("mensaje_238", "Actualizacion de un recursos");

    return new ResponseEntity<>(cliente, headers, 238);
}



  // http://localhost:8082/API/v1.0/Concesionario/clientes/cliente
	@PostMapping(path = "/cliente", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> agregarCliente(@RequestBody Cliente cliente) {
	    // Verificar que la cédula tenga 10 caracteres
	    if (cliente.getCedula().length() != 10) {
	        return new ResponseEntity<>("La cédula debe tener 10 caracteres", HttpStatus.BAD_REQUEST);
	    }
 
	 // Verificar si la cédula ya existe
	    Cliente clienteExistente = clienteService.buscarPorCedula(cliente.getCedula());
	    if (clienteExistente != null) {
	        return new ResponseEntity<>("La cédula ya existe", HttpStatus.CONFLICT);
	    }
 
	    // Guardar el nuevo cliente
	    cliente.setRegistro("C");
	    clienteService.guardar(cliente);
	    HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.add("mensaje_201", "Corresponde a la agregacion del recurso.");
		return new ResponseEntity<>(cliente, cabeceras, 201);
	}



    // http://localhost:8082/API/v1.0/Concesionario/clientes/{id}
    @DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> borrar(@PathVariable Integer id) {
        this.clienteService.borrar(this.clienteService.seleccionarC(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("mensaje_240", "Eliminar un recurso");
        return new ResponseEntity<>("Borrado", headers, 240);
    }

    // http://localhost:8082/API/v1.0/Concesionario/clientes/{apellido}
    @GetMapping(path = "/{apellido}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteTO>> buscarHateoas(@PathVariable String apellido) {
        List<ClienteTO> ls = this.clienteService.buscarCLientesApellidoTO(apellido);
        HttpHeaders headers = new HttpHeaders();
        headers.add("mensaje_236", "Consulta de recursos");
        return new ResponseEntity<>(ls, headers, 236);
    }


  

    // http://localhost:8080/API/v1.0/Budget/clientes/{cedula}
    @PutMapping(path = "/cedula/{cedula}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actualizarPorCedula(@RequestBody Cliente cliente, @PathVariable String cedula) {
	    // Verificar si la cédula ingresada es de 10 caracteres
	    if (cedula.length() != 10) {
	        return new ResponseEntity<>("La cédula debe tener 10 caracteres", HttpStatus.BAD_REQUEST);
	    }
 
	    // Buscar el cliente existente por cédula
	    Cliente clienteExistente = clienteService.buscarPorCedula(cedula);
	    if (clienteExistente == null) {
	        return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
	    }
 
	    cliente.setRegistro("C");
	    clienteExistente.setRegistro("C");
 
	    // Guardar el cliente actualizado
	    clienteService.actualizar(clienteExistente);
	    HttpHeaders cabeceras = new HttpHeaders();
	    cabeceras.add("mensaje_238", "Corresponde a la actualizacion del recurso.");
	    return new ResponseEntity<>(clienteExistente, cabeceras, 238);
	}

	// http://localhost:8082/API/v1.0/Concesionario/clientes/id/{id}
    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteTO> buscarId(@PathVariable Integer id) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("mensaje_236", "Buscar un recurso");
        return new ResponseEntity<>(this.clienteService.seleccionar(id), headers, 236);
    }


	

		
}
