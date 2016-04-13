package org.example.service;

import org.example.data.PersonRepository;
import org.example.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic operations for {@link Person}.
 */
@Service
@Transactional
public class PersonService
{
  @Autowired
  private PersonRepository repository;

  /**
   * Gets all the registered persons.
   *
   * @return A {@link List} of {@link Person}s.
   */
  public List<Person> list()
  {
    return repository.findAll(new Sort("firstName", "lastName"));
  }

  /**
   * Saves a person.
   *
   * @param person The person to save.
   * @return The saved person.
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Person save(final Person person)
  {
    return repository.saveAndFlush(person);
  }
}
