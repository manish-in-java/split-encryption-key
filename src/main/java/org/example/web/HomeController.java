package org.example.web;

import org.example.domain.Person;
import org.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home page controller.
 */
@Controller
@RequestMapping("/")
public class HomeController
{
  @Autowired
  private PersonService service;

  /**
   * Saves a person and displays the home page.
   */
  @RequestMapping(method = RequestMethod.POST)
  public String save(final Person person, final Model model)
  {
    service.save(person);

    return show(model);
  }

  /**
   * Displays the home page.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String show(final Model model)
  {
    model.addAttribute("persons", service.list());

    return "home";
  }
}
