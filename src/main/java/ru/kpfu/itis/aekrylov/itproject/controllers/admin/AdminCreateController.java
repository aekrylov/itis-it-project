package ru.kpfu.itis.aekrylov.itproject.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.aekrylov.itproject.services.AdminService;

import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/28/17 3:18 PM
 *
 */

@Controller
@RequestMapping("/admin/create")
public class AdminCreateController extends BaseAdminController {

    @ModelAttribute("example")
    public Object getExample(@RequestParam("table") String entityName) {
        return adminService.getExample(entityName);
    }

    @ModelAttribute("values")
    public Map<String, Object> newValues() {
        return new HashMap<>();
    }

    @Autowired
    public AdminCreateController(AdminService adminService) {
        super(adminService);
    }

    @ModelAttribute("action")
    public String action() {
        return "create";
    }

    @GetMapping
    public String doGet() {
        return "admin/create";
    }

    @PostMapping
    public String doPost(@ModelAttribute("example") Object entity, BindingResult result) {
        adminService.insert(entity);
        return "redirect:/admin";
    }
}
