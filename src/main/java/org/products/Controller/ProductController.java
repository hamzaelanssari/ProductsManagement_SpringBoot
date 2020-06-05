package org.products.Controller;

import org.products.Model.Product;
import org.products.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @RequestMapping(value="/products",method= RequestMethod.GET)
    public ModelAndView products(Model model,
                                 @RequestParam(name="page",defaultValue="0")int page,
                                 @RequestParam(name="size",defaultValue="4")int size,
                                 @RequestParam(name="mc",defaultValue="")String keyword){
        Page<Product> pageProducts = productRepository.search("%"+keyword+"%", PageRequest.of(page, size));
        model.addAttribute("products", pageProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("mc",keyword);
        int[] pages=new int[pageProducts.getTotalPages()];
        for(int i=0;i<pages.length;i++) pages[i]=i;
        model.addAttribute("pages", pages);
        return new ModelAndView("products");
    }

    @RequestMapping(value = "/admin/products/delete",method= RequestMethod.GET)
    public RedirectView delete(Model model, @RequestParam(name="id",required = true)Long id){
        productRepository.deleteById(id);
        return new RedirectView("/products");
    }

    @RequestMapping(value="/admin/form",method= RequestMethod.GET)
    public ModelAndView form(Model model, @RequestParam(name="id",defaultValue = "0") Long id){
        Product p = (productRepository.existsById(id))? productRepository.getOne(id):new Product();
        model.addAttribute("product", p);
        return new ModelAndView("form");
    }
    @RequestMapping(value="/admin/save",method=RequestMethod.POST)
    public ModelAndView save(Model model, @Valid Product p, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return new ModelAndView("form");
        productRepository.save(p);
        model.addAttribute("product", p);
        return new ModelAndView("confirmation");
    }
    @RequestMapping(value="/403",method= RequestMethod.GET)
    public ModelAndView error(){
        return new ModelAndView("error/403");
    }
}
