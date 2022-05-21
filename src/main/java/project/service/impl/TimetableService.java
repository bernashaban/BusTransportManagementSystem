package project.service.impl;

import project.dao.impl.TimetableRepository;
import project.model.Destination;
import project.model.Timetable;
import project.service.Service;

import java.util.Collection;
import java.util.List;

public class TimetableService implements Service<Timetable> {
    private final TimetableRepository timetableRepository;

    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    @Override
    public Collection<Timetable> getAll() {
        return timetableRepository.findAll();
    }

    @Override
    public Timetable getById(Integer id) {
        return timetableRepository.findById(id);
    }

    @Override
    public Timetable add(Timetable entity) {
        return timetableRepository.create(entity);
    }

    @Override
    public Timetable update(Timetable entity) {
        return timetableRepository.update(entity);
    }

    @Override
    public Timetable delete(Integer id) {
        return timetableRepository.deleteById(id);
    }

    public List<Timetable> getByDestination(Destination destination){
        return timetableRepository.findTimetablesByDestination(destination);
    }
}
