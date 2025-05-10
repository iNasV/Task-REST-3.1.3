package ru.itmentor.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.service.PersonService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.util.PersonValidator;

import java.util.Collections;
import java.util.List;

@Controller
public class PersonController {

    private final PersonService personService;
    private final RoleService roleService;
    private final PersonValidator personValidator;

    public PersonController(PersonService personService, RoleService roleService, PersonValidator personValidator) {
        this.personService = personService;
        this.roleService = roleService;
        this.personValidator = personValidator;
    }

    // Определение типа главной страницы в соответствии с ролью
    @GetMapping("/{id}")
    public String showInfo(Model model,@PathVariable("id") long id) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        String hightRole = person.getRoles().get(0).toString();
        if (hightRole.equals("ROLE_ADMIN")) {
            String redirect = "redirect:/admin/"+person.getId();
            return redirect;
        }
        String redirect = "redirect:/user/"+person.getId();
        return redirect;
    }

    // главная страница аккаунта АДМИН
    @GetMapping("/admin/{id}")
    public String showAdminInfo(Model model,@PathVariable("id") long id) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        return "account/admin";
    }

    // Операция UPDATE доступная только с ролью АДМИН
    @GetMapping("/admin/edit/")
    public String editAdminInfo(Model model, @RequestParam("id") long id) {
        model.addAttribute("person", personService.findOne(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "edit/view_edit_person";
    }

    // Внесение изменений в БД
    @PutMapping("/admin/edit/{id}")
    public String updateAccount(@PathVariable("id") long id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "edit/view_edit_person";
        }
        personService.update(id, person);
        return "redirect:/";
    }

    // главная страница аккаунта ЮЗЕР
    @GetMapping("/user/{id}")
    public String showUserInfo(Model model,@PathVariable("id") long id) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        return "account/user";
    }

    // главная страница приложения
    @GetMapping()
    public String getHomePage(Model model) {
        List<Person> personList = personService.findAll();
        model.addAttribute("persons", personList);
        return "homepage";
    }

    // страница регистрации
    @GetMapping("/admin/new")
    public String createPersonReq(@ModelAttribute("person") Person person) {
        return "new/view_create_user";
    }

    // обработка ответа на создание
    @PostMapping("/registration")
    public String createAccount(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        person.setRoles(Collections.emptyList());
        person.setRoles(Collections.singletonList(roleService.findById(2)));
        if (bindingResult.hasErrors()) {
            return "new/view_create_user";
        }
        personService.save(person);
        return "redirect:/";
    }

    // удаление пользователя
    @DeleteMapping("/admin/")
    public String removePerson(@RequestParam("id") long id) {
        personService.delete(id);
        return "redirect:/";
    }
}
