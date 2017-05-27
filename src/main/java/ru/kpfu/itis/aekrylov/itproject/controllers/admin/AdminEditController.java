package ru.kpfu.itis.aekrylov.itproject.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.aekrylov.itproject.services.AdminService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String doGet(@RequestParam("table") String entityName, ModelMap map) {
        map.put("action", "edit");
        map.put("columns", adminService.getColumnNames(entityName));
        return "admin/create";
    }

    //todo
    @PostMapping
    public String doPost( @ModelAttribute("values") Object entity,
                          final @RequestParam Map<String, Object> values,
                          @RequestParam("id") int id, ModelMap modelMap) {
        Class entityClass = entity.getClass();
        /*Map<String, Object> newValues = Arrays.stream(entityClass.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, field -> {
                    Class c = field.getType();
                    return c.cast(values.get(field.getName()));
                }));*/

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
