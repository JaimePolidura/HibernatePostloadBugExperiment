package es.jaime.hibernatepostloadbugexperiment;

import static org.junit.jupiter.api.Assertions.*;

import es.jaime.hibernatepostloadbugexperiment.entity.Child;
import es.jaime.hibernatepostloadbugexperiment.entity.Parent;
import es.jaime.hibernatepostloadbugexperiment.repository.ChildRepository;
import es.jaime.hibernatepostloadbugexperiment.repository.ParentRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HibernatePostloadBugExperimentApplicationTests {
  @Autowired
  private ParentRepository parentRepository;
  @Autowired
  private ChildRepository childRepository;

  @BeforeEach
  public void setup() {
    parentRepository.deleteAll();
    childRepository.deleteAll();
  }

  @Test
  void contextLoads() {
    Parent parent = new Parent();
    parentRepository.save(parent);
    childRepository.save(new Child(1, parent));
    childRepository.save(new Child(2, parent));
    childRepository.save(new Child(3, parent));

    List<Child> children = childRepository.findAll();

    assertEquals(3, children.size());
  }
}
