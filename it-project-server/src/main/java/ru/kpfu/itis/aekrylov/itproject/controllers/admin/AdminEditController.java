package ru.kpfu.itis.aekrylov.itproject.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.aekrylov.itproject.services.AdminService;

import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/27/17 6:50 PM
 */

@Controller
@RequestMapping("/admin/edit")
public class AdminEditController extends BaseAdminController {

    @ModelAttribute("values")
    public Object getEntity(@RequestParam("id") Integer id, @RequestParam("table") String entityName) {
        return adminService.findOne(entityName, id);
    }

    @ModelAttribute("action")
    public String action() {
        return "edit";
    }

    @Autowired
    public AdminEditController(AdminService adminService) {
        super(adminService);
    }

    @GetMapping
    public String doGet() {
        return "admin/create";
    }

    @PostMapping
    public String doPost(@RequestParam Map<String, Object> values,
                          @RequestParam("id") int id, ModelMap modelMap) {
        try{
            adminService.save((String) modelMap.get("tablename"), id, values);
            return "redirect:/admin/?table="+modelMap.get("tablename");
        } catch (RuntimeException e) {
            e.printStackTrace();
            modelMap.put("values", values);
            return "admin/create";
        }
    }
}
