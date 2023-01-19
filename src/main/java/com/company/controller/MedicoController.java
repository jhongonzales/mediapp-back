package com.company.controller;

import com.company.dto.MedicoDto;
import com.company.exception.ModelNotFoundException;
import com.company.model.Medico;
import com.company.service.IMedicoService;
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
@RequestMapping("/v1/medicos")
public class MedicoController {

    @Autowired
    private ModelMapper modelMapper;

    private final IMedicoService service;

    public MedicoController(IMedicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MedicoDto>> buscarTodos() {
        List<MedicoDto> medicos = service.buscarTodos().stream()
                .map(m->modelMapper.map(m,MedicoDto.class)).toList();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDto> buscarPorId(@PathVariable("id") Integer idMedico) {
        MedicoDto dtoResponse;
        Medico medico = service.buscarPorId(idMedico);
        if (medico == null) {
            throw new ModelNotFoundException("Medico no encontrado");
        }
        dtoResponse = modelMapper.map(medico, MedicoDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody MedicoDto medico) {
        Medico dtoRequest = modelMapper.map(medico, Medico.class);
        Medico medicoRegistrado = service.registrar(dtoRequest);
        MedicoDto dtoResponse = modelMapper.map(medicoRegistrado, MedicoDto.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getIdMedico()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<MedicoDto> modificar(@Valid @RequestBody MedicoDto medico) {
        MedicoDto dtoResponse;
        Medico medicoModificar = service.buscarPorId(medico.getIdMedico());
        if (medicoModificar == null) {
            throw new ModelNotFoundException("Medico no encontrado");
        }
        Medico dtoRequest = modelMapper.map(medico, Medico.class);
        dtoResponse = modelMapper.map(service.registrar(dtoRequest), MedicoDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idMedico) {
        Medico medico = service.buscarPorId(idMedico);
        if (medico == null) {
            throw new ModelNotFoundException("Medico no encontrado");
        }
        service.eliminar(idMedico);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicoDto> listarHateoas(@PathVariable Integer id){
        Medico obj = service.buscarPorId(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
        }
        MedicoDto dto = modelMapper.map(obj, MedicoDto.class);
        EntityModel<MedicoDto> recurso = EntityModel.of(dto);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).buscarPorId(id));
        recurso.add(link1.withRel("medico-recurso"));
        return recurso;
    }

}
