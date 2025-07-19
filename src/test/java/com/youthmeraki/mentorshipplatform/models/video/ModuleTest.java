package com.youthmeraki.mentorshipplatform.models.video;

import com.youthmeraki.mentorshipplatform.repositories.ModuleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ModuleTest {

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    void testCreateModule() {
        Module module = new Module();
        module.setName("Test Module");
        module.setDescription("This is a test module for unit testing.");

        Module savedModule = moduleRepository.save(module);

        assertNotNull(savedModule);
        assertEquals("Test Module", savedModule.getName());
        assertEquals("This is a test module for unit testing.", savedModule.getDescription());
    }

    @Test
    void getTestModules() {
        List<Module> modules = moduleRepository.findAll();
        modules.stream().forEach(module -> {
            System.out.println("Module ID: " + module.getId());
            System.out.println("Module Name: " + module.getName());
            System.out.println("Module Description: " + module.getDescription());
        });
        assertNotNull(modules);
    }
}