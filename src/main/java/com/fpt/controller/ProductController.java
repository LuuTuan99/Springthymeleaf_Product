package com.fpt.controller;

import com.fpt.entity.Product;
import com.fpt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;


import javax.validation.Valid;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("products", productService.getList(1, 10));
        return "list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "form";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String store(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        productService.create(product);
        return "redirect:/products";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String search(@RequestParam String name, Model model) {
        if (StringUtils.isEmpty(name)) {
            return "redirect:/products";
        }

        model.addAttribute("products", productService.search(name));
        return "list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
    public String editProduct(@PathVariable long id, Model model) {
        model.addAttribute("product", productService.getDetail(id));
        return "form";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
    public String deleteProduct(@PathVariable long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted contact successfully!");
        return "redirect:/products";
    }
}
