package com.company.serviceImp;

import com.company.model.Paciente;
import com.company.repository.IGenericRepo;
import com.company.repository.IPacienteRepo;
import com.company.service.IPacienteService;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente, Integer> implements IPacienteService {

    private final IPacienteRepo repo;

    public PacienteServiceImpl(IPacienteRepo repo) {
        this.repo = repo;
    }

    @Override
    protected IGenericRepo<Paciente, Integer> getRepository() {
        return this.repo;
    }
}

