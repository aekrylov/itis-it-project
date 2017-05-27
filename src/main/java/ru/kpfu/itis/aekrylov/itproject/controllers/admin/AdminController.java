package ru.kpfu.itis.aekrylov.itproject.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.services.AdminService;

import java.util.stream.Collectors;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/27/17 3:50 PM
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String doGet(@RequestParam(value = "table", required = false) String entityName,
                        @RequestParam(value = "q", required = false) String q,
                        @PageableDefault Pageable pageable,
                        ModelMap modelMap) {
        if(entityName == null || entityName.equals("")) {
            modelMap.put("tables", adminService.getEntities().stream()
                .collect(Collectors.toMap(s -> s, s -> s))); //todo nice table names
            return "admin/main";
        }

        Page results = adminService.query(entityName, q, pageable);

        modelMap.put("tablename", entityName);
        modelMap.put("q", q);
        modelMap.put("rows", results.iterator());
        modelMap.put("columns", adminService.getColumnNames(entityName));
        modelMap.put("has_next_page", results.isLast());
        modelMap.put("page", pageable);
        return "admin/table";
    }
}
