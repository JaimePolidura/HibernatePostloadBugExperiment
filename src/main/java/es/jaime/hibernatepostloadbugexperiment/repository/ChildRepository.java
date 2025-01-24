package es.jaime.hibernatepostloadbugexperiment.repository;

import es.jaime.hibernatepostloadbugexperiment.entity.Child;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, UUID> {
}
