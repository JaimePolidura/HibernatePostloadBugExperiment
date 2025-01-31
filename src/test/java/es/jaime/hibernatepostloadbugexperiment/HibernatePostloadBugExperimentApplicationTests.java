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
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
class HibernatePostloadBugExperimentApplicationTests {
  @Autowired
  private TransactionTemplate trasactions;
  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  @Transactional
  public void setup() {
    trasactions.executeWithoutResult(s -> {
      entityManager.createQuery("DELETE FROM Child").executeUpdate();
      entityManager.createQuery("DELETE FROM Parent").executeUpdate();
    });
  }

  @Test
  void contextLoads() {
    Parent parent = new Parent();
    trasactions.executeWithoutResult(s -> entityManager.persist(parent));

    trasactions.executeWithoutResult(s -> entityManager.persist(new Child(1, parent)));
    trasactions.executeWithoutResult(s -> entityManager.persist(new Child(2, parent)));
    trasactions.executeWithoutResult(s -> entityManager.persist(new Child(3, parent)));

    List<Child> children = trasactions.execute(t ->
        entityManager.createQuery("SELECT c FROM Child c").getResultList());

    assertEquals(3, children.size());
  }
}
