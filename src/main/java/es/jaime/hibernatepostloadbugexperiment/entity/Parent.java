package es.jaime.hibernatepostloadbugexperiment.entity;

import static lombok.AccessLevel.NONE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parent")
@Getter
@Setter
@NoArgsConstructor
public class Parent {
  @Setter(NONE)
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToMany(mappedBy = "parent")
  private List<Child> children;

  @PostLoad
  public void postLoad() {
    children.sort(Comparator.comparing(Child::getValue));
  }
}
