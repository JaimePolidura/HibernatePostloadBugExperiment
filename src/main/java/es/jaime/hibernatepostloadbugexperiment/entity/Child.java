package es.jaime.hibernatepostloadbugexperiment.entity;

import static lombok.AccessLevel.NONE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "child")
@Getter
@Setter
@NoArgsConstructor
public class Child {
  @Setter(NONE)
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Integer value;

  @ManyToOne(optional = false)
  @JoinColumn(name = "parent_id", nullable = false)
  private Parent parent;

  public Child(Integer value, Parent parent) {
    this.parent = parent;
    this.value = value;
  }
}
