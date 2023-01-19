package com.company.serviceImp;

import com.company.exception.PacienteNotFounException;
import com.company.model.Paciente;
import com.company.repository.IPacienteRepo;
import com.company.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final IPacienteRepo repo;

    public PacienteServiceImpl(IPacienteRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Paciente> buscarTodos() {
        return repo.findAll();
    }

    @Override
    public Paciente buscarPorId(Integer idPaciente) {
        return repo.findById(idPaciente).orElseThrow(() -> new PacienteNotFounException("Paciente no encontrado"));
    }

    @Override
    public Paciente registrar(Paciente paciente) {
        return repo.save(paciente);
    }

    @Override
    public void eliminar(Integer idPaciente) {
        repo.deleteById(idPaciente);
    }
}

