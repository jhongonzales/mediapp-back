package com.company.serviceImp;

import com.company.model.Examen;

import com.company.repository.IExamenRepo;
import com.company.repository.IGenericRepo;
import com.company.service.IExamenService;
import org.springframework.stereotype.Service;

@Service
public class ExamenServiceImpl extends CRUDImpl<Examen, Integer> implements IExamenService {
    private final IExamenRepo repo;

    public ExamenServiceImpl(IExamenRepo repo) {
        this.repo = repo;
    }
    @Override
    protected IGenericRepo<Examen, Integer> getRepository() {
        return this.repo;
    }
}
