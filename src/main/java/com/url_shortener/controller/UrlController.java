package com.url_shortener.controller;

import com.url_shortener.dto.UrlDto;
import com.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/")
    public String getUrlForm(Model model) {
        model.addAttribute("url",new UrlDto());
        model.addAttribute("aliases", urlService.getAllAliases());
        return "index";
    }

    @PostMapping("/")
    public String postUrlForm(@Valid @ModelAttribute("url") UrlDto url,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index";
        }

        if (urlService.aliasAlreadyExists(url.getAlias())) {
            redirectAttributes.addFlashAttribute("error", "Alias already in use");
        } else {
            urlService.saveUrl(url);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Your URL has been shortened");
        }
        return "redirect:/";
    }

    @GetMapping("/{alias}")
    public RedirectView getUrlByAlias(@PathVariable String alias) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlService.getUrl(alias));
        urlService.incrementHitCount(alias);
        return redirectView;
    }

}
