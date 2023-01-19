package com.company.serviceImp;

import com.company.repository.IGenericRepo;
import com.company.service.ICRUD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepository();
    @Override
    public List<T> buscarTodos() {
        return getRepository().findAll();
    }

    @Override
    public T buscarPorId(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public T registrar(T obj) {
        return getRepository().save(obj);
    }

    @Override
    public void eliminar(ID id) {
        getRepository().deleteById(id);
    }
}

