package com.example.demo.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
import com.example.demo.api.repository.modelo.Vehiculo;
import com.example.demo.api.service.IVehiculoService;
import com.example.demo.api.service.to.VehiculoTO;

@CrossOrigin
@RestController
@RequestMapping(path = "vehiculos")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;


    //http://localhost:8082/API/v1.0/Concesionario/vehiculos
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehiculo> guardar(@RequestBody Vehiculo vehiculo) {
        vehiculo.setEstado("D");
        this.vehiculoService.agregar(vehiculo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("mensaje_201", "Insercion de un recurso");
        return new ResponseEntity<>(vehiculo, headers, 201);
    }

    // 2d: Buscar vehículos por marca
    @GetMapping(path = "/{marca}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehiculoTO>> buscarVehiculosPorMarca(@PathVariable String marca) {
        List<Vehiculo> vehiculos = vehiculoService.buscarPorMarca(marca);
        if (vehiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
    // Convertir Vehiculo a VehiculoTO
        List<VehiculoTO> vehiculoTOs = vehiculos.stream()
            .map(v -> new VehiculoTO(v.getPlaca(), v.getModelo(), v.getMarca(), v.getAnio(), v.getEstado(), v.getValorPorDia()))
            .collect(Collectors.toList());
    
        return new ResponseEntity<>(vehiculoTOs, HttpStatus.OK);
    }


    // 2d: Actualizar un vehículo
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehiculo> actualizarVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo vehiculoExistente = vehiculoService.buscarPorPlaca(vehiculo.getPlaca());
        if (vehiculoExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vehiculoService.actualizar(vehiculo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("mensaje_200", "Vehículo actualizado con éxito");
        return new ResponseEntity<>(vehiculo, headers, HttpStatus.OK);
    }

    // 2d: Eliminar un vehículo
    @DeleteMapping(path = "/{placa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String placa) {
        Vehiculo vehiculoExistente = vehiculoService.buscarPorPlaca(placa);
        if (vehiculoExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vehiculoService.eliminarPorPlaca(placa);
        HttpHeaders headers = new HttpHeaders();
        headers.add("mensaje_200", "Vehículo eliminado con éxito");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // 2d: Visualizar un vehículo
    @GetMapping(path = "/placa/{placa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehiculo> visualizarVehiculo(@PathVariable String placa) {
        Vehiculo vehiculo = vehiculoService.buscarPorPlaca(placa);
        if (vehiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    @GetMapping(path = "/marca-modelo/{marca}/{modelo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehiculoTO> obtenerVehiculosPorMarcaModelo(@PathVariable String marca, @PathVariable String modelo) {
        List<VehiculoTO> vehiculoTOs = this.vehiculoService.buscarPorMarcaModelo(marca, modelo);
        for (VehiculoTO vehiculoTO : vehiculoTOs) {
            Link link = linkTo(methodOn(VehiculoController.class).obtenerVehiculosPorMarcaModelo(vehiculoTO.getMarca(), vehiculoTO.getModelo()))
                    .withRel("vehiculosPorMarca");
            vehiculoTO.add(link);
        }
        return vehiculoTOs;
    }

    
}
