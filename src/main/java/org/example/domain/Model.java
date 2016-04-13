package org.example.domain;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

/**
 * Represents a domain entity.
 */
@MappedSuperclass
public abstract class Model
{
  @Column(name = "id")
  @Generated(GenerationTime.INSERT)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;

  /**
   * Gets the unique identifier for this entity.
   *
   * @return The unique identifier for this entity.
   */
  public Long getID()
  {
    return id;
  }
}
