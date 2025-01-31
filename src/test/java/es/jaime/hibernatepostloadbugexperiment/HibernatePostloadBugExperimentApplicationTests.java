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
  private TransactionTemplate transactions;
  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  public void setup() {
    transactions.executeWithoutResult(s -> {
      entityManager.createQuery("DELETE FROM Child").executeUpdate();
      entityManager.createQuery("DELETE FROM Parent").executeUpdate();
    });
  }

  @Test
  void contextLoads() {
    Parent parent = new Parent();
    transactions.executeWithoutResult(s -> entityManager.persist(parent));

    transactions.executeWithoutResult(s -> entityManager.persist(new Child(1, parent)));
    transactions.executeWithoutResult(s -> entityManager.persist(new Child(2, parent)));
    transactions.executeWithoutResult(s -> entityManager.persist(new Child(3, parent)));

    List<Child> children = transactions.execute(t ->
        entityManager.createQuery("SELECT c FROM Child c").getResultList());

    assertEquals(3, children.size());
  }
}
