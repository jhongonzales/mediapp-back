package com.company.controller;

import com.company.dto.ExamenDto;
import com.company.exception.ModelNotFoundException;
import com.company.model.Examen;
import com.company.service.IExamenService;
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
@RequestMapping("/v1/examenes")
public class ExamenController {

    @Autowired
    private ModelMapper modelMapper;

    private final IExamenService service;

    public ExamenController(IExamenService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ExamenDto>> buscarTodos() {
        List<ExamenDto> examenes = service.buscarTodos().stream()
                .map(e->modelMapper.map(e,ExamenDto.class)).toList();
        return new ResponseEntity<>(examenes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenDto> buscarPorId(@PathVariable("id") Integer idExamen) {
        ExamenDto dtoResponse;
        Examen examen = service.buscarPorId(idExamen);
        if (examen == null) {
            throw new ModelNotFoundException("Examen no encontrado");
        }
        dtoResponse = modelMapper.map(examen, ExamenDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ExamenDto examen) {
        Examen dtoRequest = modelMapper.map(examen, Examen.class);
        Examen examenRegistrado = service.registrar(dtoRequest);
        ExamenDto dtoResponse = modelMapper.map(examenRegistrado, ExamenDto.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getIdExamen()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<ExamenDto> modificar(@Valid @RequestBody ExamenDto examen) {
        ExamenDto dtoResponse;
        Examen examenModificar = service.buscarPorId(examen.getIdExamen());
        if (examenModificar == null) {
            throw new ModelNotFoundException("Examen no encontrado");
        }
        Examen dtoRequest = modelMapper.map(examen, Examen.class);
        dtoResponse = modelMapper.map(service.registrar(dtoRequest), ExamenDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idExamen) {
        Examen examen = service.buscarPorId(idExamen);
        if (examen == null) {
            throw new ModelNotFoundException("Examen no encontrado");
        }
        service.eliminar(idExamen);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamenDto> listarHateoas(@PathVariable Integer id){
        Examen obj = service.buscarPorId(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
        }
        ExamenDto dto = modelMapper.map(obj, ExamenDto.class);
        EntityModel<ExamenDto> recurso = EntityModel.of(dto);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).buscarPorId(id));
        recurso.add(link1.withRel("examen-recurso"));
        return recurso;
    }

}
