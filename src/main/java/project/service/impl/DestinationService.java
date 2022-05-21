package project.service.impl;

import project.dao.impl.DestinationRepository;
import project.model.Destination;
import project.service.Service;

import java.util.Collection;

public class DestinationService implements Service<Destination> {
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public Collection<Destination> getAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination getById(Integer id) {
        return destinationRepository.findById(id);
    }

    @Override
    public Destination add(Destination entity) {
        return destinationRepository.create(entity);
    }

    @Override
    public Destination update(Destination entity) {
        return destinationRepository.update(entity);
    }

    @Override
    public Destination delete(Integer id) {
        return destinationRepository.deleteById(id);
    }

    public Destination getByName(String name) {
        return destinationRepository.findByName(name);
    }
}
