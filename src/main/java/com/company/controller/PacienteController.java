package com.company.controller;

import com.company.dto.PacienteDto;
import com.company.exception.ModelNotFoundException;
import com.company.model.Paciente;
import com.company.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/pacientes")
public class PacienteController {

    @Autowired
    private ModelMapper modelMapper;
    private final IPacienteService service;
    public PacienteController(IPacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> buscarTodos() {
        List<PacienteDto> pacientes = service.buscarTodos().stream()
                .map(p->modelMapper.map(p, PacienteDto.class)).toList();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPorId(@PathVariable("id") Integer idPaciente) {
        PacienteDto dtoResponse;
        Paciente paciente = service.buscarPorId(idPaciente);
        if (paciente == null) {
            throw new ModelNotFoundException("Paciente no encontrado");
        }
        dtoResponse = modelMapper.map(paciente, PacienteDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody PacienteDto paciente) {
        Paciente dtoRequest = modelMapper.map(paciente, Paciente.class);
        Paciente pacienteRegistrado = service.registrar(dtoRequest);
        PacienteDto dtoResponse = modelMapper.map(pacienteRegistrado, PacienteDto.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                       .buildAndExpand(dtoResponse.getIdPaciente()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<PacienteDto> modificar(@Valid @RequestBody PacienteDto paciente) {
        PacienteDto dtoResponse;
        Paciente pacienteModificar = service.buscarPorId(paciente.getIdPaciente());
        if (pacienteModificar == null) {
            throw new ModelNotFoundException("Paciente no encontrado");
        }
        Paciente dtoRequest = modelMapper.map(paciente, Paciente.class);
        dtoResponse = modelMapper.map(service.registrar(dtoRequest), PacienteDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idPaciente) {
        Paciente paciente = service.buscarPorId(idPaciente);
        if (paciente == null) {
            throw new ModelNotFoundException("Paciente no encontrado");
        }
        service.eliminar(idPaciente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PacienteDto> listarHateoas(@PathVariable Integer id){
        Paciente obj = service.buscarPorId(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
        }
        PacienteDto dto = modelMapper.map(obj, PacienteDto.class);
        EntityModel<PacienteDto> recurso = EntityModel.of(dto);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).buscarPorId(id));
        recurso.add(link1.withRel("paciente-recurso"));
        return recurso;
    }

}
