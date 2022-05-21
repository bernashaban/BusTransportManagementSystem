package project.service.impl;

import project.dao.impl.ReservationRepository;
import project.model.Customer;
import project.model.Reservation;
import project.service.Service;

import java.util.Collection;
import java.util.List;

public class ReservationService implements Service<Reservation> {
    private final ReservationRepository reserveRepository;

    public ReservationService(ReservationRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    @Override
    public Collection<Reservation> getAll() {
        return reserveRepository.findAll();
    }

    @Override
    public Reservation getById(Integer id) {
        return reserveRepository.findById(id);
    }

    @Override
    public Reservation add(Reservation entity) {
        return reserveRepository.create(entity);
    }

    @Override
    public Reservation update(Reservation entity) {
        return reserveRepository.update(entity);
    }

    @Override
    public Reservation delete(Integer id) {
        return reserveRepository.deleteById(id);
    }

    public List<Reservation> getCustomerReservations(Customer customer){
        return reserveRepository.customerReservations(customer);
    }
}
