package com.company.serviceImp;

import com.company.model.Especialidad;
import com.company.repository.IEspecialidadRepo;
import com.company.repository.IGenericRepo;
import com.company.service.IEspecialidadService;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadServiceImpl extends CRUDImpl<Especialidad, Integer> implements IEspecialidadService {

    private final IEspecialidadRepo repo;

    public EspecialidadServiceImpl(IEspecialidadRepo repo) {
        this.repo = repo;
    }
    @Override
    protected IGenericRepo<Especialidad, Integer> getRepository() {
        return this.repo;
    }

}
