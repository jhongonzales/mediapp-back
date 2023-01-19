package com.company.controller;

import com.company.dto.EspecialidadDto;
import com.company.exception.ModelNotFoundException;
import com.company.model.Especialidad;
import com.company.service.IEspecialidadService;
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
@RequestMapping("/v1/especialidades")
public class EspecialidadController {

    @Autowired
    private ModelMapper modelMapper;

    private final IEspecialidadService service;

    public EspecialidadController(IEspecialidadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadDto>> buscarTodos() {
        List<EspecialidadDto> especialidades = service.buscarTodos().stream()
                .map(e->modelMapper.map(e,EspecialidadDto.class)).toList();
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDto> buscarPorId(@PathVariable("id") Integer idEspecialidad) {
        EspecialidadDto dtoResponse;
        Especialidad especialidad = service.buscarPorId(idEspecialidad);
        if (especialidad == null) {
            throw new ModelNotFoundException("Especilidad no encontrada");
        }
        dtoResponse = modelMapper.map(especialidad, EspecialidadDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody EspecialidadDto especialidad) {
        Especialidad dtoRequest = modelMapper.map(especialidad, Especialidad.class);
        Especialidad especialidadRegistrada = service.registrar(dtoRequest);
        EspecialidadDto dtoResponse = modelMapper.map(especialidadRegistrada, EspecialidadDto.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getIdEspecialidad()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<EspecialidadDto> modificar(@Valid @RequestBody EspecialidadDto especialidad) {
        EspecialidadDto dtoResponse;
        Especialidad especialidadModificar = service.buscarPorId(especialidad.getIdEspecialidad());
        if (especialidadModificar == null) {
            throw new ModelNotFoundException("Especialidad no encontrada");
        }
        Especialidad dtoRequest = modelMapper.map(especialidad, Especialidad.class);
        dtoResponse = modelMapper.map(service.registrar(dtoRequest), EspecialidadDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idEspecialidad) {
        Especialidad especialidad = service.buscarPorId(idEspecialidad);
        if (especialidad == null) {
            throw new ModelNotFoundException("Especialidad no encontrada");
        }
        service.eliminar(idEspecialidad);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<EspecialidadDto> listarHateoas(@PathVariable Integer id){
        Especialidad obj = service.buscarPorId(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
        }
        EspecialidadDto dto = modelMapper.map(obj, EspecialidadDto.class);
        EntityModel<EspecialidadDto> recurso = EntityModel.of(dto);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).buscarPorId(id));
        recurso.add(link1.withRel("especialidad-recurso"));
        return recurso;
    }

}
