package com.company.controller;

import com.company.dto.ConsultaDto;
import com.company.dto.ConsultaListaExamenDto;
import com.company.exception.ModelNotFoundException;
import com.company.model.Consulta;
import com.company.model.Examen;
import com.company.service.IConsultaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
@RequestMapping("/v1/consultas")
public class ConsultaController {

    @Autowired
    private ModelMapper mapper;

    private final IConsultaService service;

    public ConsultaController(IConsultaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDto>> buscarTodos() {
        List<ConsultaDto> consultas = service.buscarTodos().stream()
                .map(c->mapper.map(c,ConsultaDto.class)).toList();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDto> buscarPorId(@PathVariable("id") Integer idConsulta) {
        ConsultaDto dtoResponse;
        Consulta consulta = service.buscarPorId(idConsulta);
        if (consulta == null) {
            throw new ModelNotFoundException("Consulta no encontrada");
        }
        dtoResponse = mapper.map(consulta, ConsultaDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ConsultaListaExamenDto dtoRequest) {

        Consulta consulta = mapper.map(dtoRequest.getConsultaDto(), Consulta.class);
        List<Examen> examenes = mapper.map(dtoRequest.getLstExamen(), new TypeToken<List<Examen>>() {}.getType());
        Consulta obj = service.registrarTransaccional(consulta, examenes);
        ConsultaDto dtoResponse = mapper.map(obj, ConsultaDto.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getIdConsulta())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /*
    @PutMapping
    public ResponseEntity<ConsultaDto> modificar(@Valid @RequestBody ConsultaDto consulta) {
        ConsultaDto dtoResponse;
        Consulta consultaModificar = service.buscarPorId(consulta.getIdConsulta());
        if (consultaModificar == null) {
            throw new ModelNotFoundException("Consulta no encontrada");
        }
        Consulta dtoRequest = mapper.map(consulta, Consulta.class);
        dtoResponse = mapper.map(service.registrar(dtoRequest), ConsultaDto.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idConsulta) {
        Consulta consulta = service.buscarPorId(idConsulta);
        if (consulta == null) {
            throw new ModelNotFoundException("Consulta no encontrada");
        }
        service.eliminar(idConsulta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultaDto> listarHateoas(@PathVariable Integer id){
        Consulta obj = service.buscarPorId(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
        }
        ConsultaDto dto = mapper.map(obj, ConsultaDto.class);
        EntityModel<ConsultaDto> recurso = EntityModel.of(dto);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).buscarPorId(id));
        recurso.add(link1.withRel("consulta-recurso"));
        return recurso;
    }

}
