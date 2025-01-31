package es.jaime.hibernatepostloadbugexperiment;

import static org.junit.jupiter.api.Assertions.*;

import es.jaime.hibernatepostloadbugexperiment.entity.Child;
import es.jaime.hibernatepostloadbugexperiment.entity.Parent;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HibernatePostloadBugExperimentApplicationTests {
  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  @Transactional
  public void setup() {
    entityManager.createQuery("DELETE FROM Parent").executeUpdate();
    entityManager.createQuery("DELETE FROM Child").executeUpdate();
  }

  @Test
  @Transactional
  void contextLoads() {
    Parent parent = new Parent();
    entityManager.persist(parent);

    entityManager.persist(new Child(1, parent));
    entityManager.persist(new Child(2, parent));
    entityManager.persist(new Child(3, parent));

    entityManager.flush();
    entityManager.clear();

    List<Child> children = entityManager.createQuery("SELECT c FROM Child c").getResultList();
    assertEquals(3, children.size());
  }
}
