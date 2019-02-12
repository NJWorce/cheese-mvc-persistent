package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController {


    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    MenuDao menuDao;


    // mapped to /menu
    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

    // mapped to /menu
    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAdd(Model model){
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());

        return "menu/add";
    }

    // mapped to /menu
    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAdd(@ModelAttribute @Valid Menu menu, Errors errors){
        if (errors.hasErrors()){
            return "menu/add";
        }
        menuDao.save(menu);
        return "redirect:view/"+ menu.getId();
    }
    // mapped to menu
    @RequestMapping(value="view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable int menuId, Model model){

       model.addAttribute("menu",  menuDao.findOne(menuId));
       model.addAttribute("title", "Menu #"+menuId);

       return "menu/view";
    }
    // mapped to menu
    @RequestMapping(value="add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(@PathVariable int menuId, Model model){
        Menu menu  = menuDao.findOne(menuId);

        // Form Object to help with form display
        model.addAttribute(new AddMenuItemForm(menu, cheeseDao.findAll()));
        model.addAttribute("title", "Add Item to menu " + menu.getName());

        return "menu/add-item";
    }
    @RequestMapping(value="add-item", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm addMenuItemForm, Errors errors){
        if (errors.hasErrors()){
            return "menu/add-item";
        }
        Menu theMenu = menuDao.findOne(addMenuItemForm.getMenuId());
        Cheese theCheese = cheeseDao.findOne(addMenuItemForm.getCheeseId());
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:/menu/view/" + theMenu.getId();
    }

}
