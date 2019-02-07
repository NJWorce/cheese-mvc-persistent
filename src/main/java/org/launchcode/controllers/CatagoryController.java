package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("category")
public class CatagoryController {
    @Autowired
    private CategoryDao categoryDao;

    //Request path "category"
    @RequestMapping("")
    public String index(Model model) {
        Iterable<Category> categories = categoryDao.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("title", "Categories in Which Cheese Exists in an Abstract Manner");
        return "category/index" ;


    }



}
