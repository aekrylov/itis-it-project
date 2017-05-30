package ru.kpfu.itis.aekrylov.itproject.controllers.admin;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.services.AdminService;

import java.util.Set;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 11:33 PM
 */
public class BaseAdminController {

    protected AdminService adminService;

    public BaseAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ModelAttribute("tablename")
    public String entityName(@RequestParam(value = "table", required = false) String name) {
        return name;
    }

    @ModelAttribute("columns")
    public Set<String> columns(@RequestParam(value = "table", required = false) String name) {
        if(name != null) {
            return adminService.getColumnNames(name);
        }
        return null;
    }

}
