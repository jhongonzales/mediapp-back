package com.company.controller;

import com.company.model.Paciente;
import com.company.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pacientes")
public class PacienteController {

    private final IPacienteService service;

    public PacienteController(IPacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() {
        List<Paciente> pacientes = service.buscarTodos();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable("id") Integer idPaciente) {
        Paciente paciente = service.buscarPorId(idPaciente);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) {
        Paciente pacienteRegistrado = service.registrar(paciente);
        return new ResponseEntity<>(pacienteRegistrado, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Paciente> modificar(@RequestBody Paciente paciente) {
        Paciente pacienteRegistrado = service.registrar(paciente);
        return new ResponseEntity<>(pacienteRegistrado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idPaciente) {
        service.eliminar(idPaciente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
