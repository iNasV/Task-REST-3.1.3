package ru.itmentor.spring.boot_security.demo.integration.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itmentor.spring.boot_security.demo.integration.annotation.IT;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.model.Person.Fields;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static ru.itmentor.spring.boot_security.demo.model.Person.Fields.*;

@IT
@RequiredArgsConstructor
@AutoConfigureMockMvc
public class PersonControllerIT {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("homepage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("persons"));
    }

    @Test
    @WithMockUser
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .param(name,"ТестКонтроллер")
                        .param(login,"testController")
                        .param(password,"$2a$10$xm2/3JiFKuyuQ70tVS1XsOsgzf86LJiGfoDbMppwa3BwXVqT4bj52")
                        .param(email,"testController@test.com")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }



}
