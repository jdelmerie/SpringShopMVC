package fr.fms.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;

@Controller
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;

	@GetMapping("/index")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "search", defaultValue = "") String search) {
		
		Page<Article> articles = articleRepository.findByDescriptionContainsOrBrandContains(search, search, PageRequest.of(page - 1, 5));
		model.addAttribute("articles", articles.getContent());
		model.addAttribute("pages", new int[articles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("search", search);
		return "articles";
	}

	@GetMapping("/article")
	public String article(Model model) {
		model.addAttribute("article", new Article());
		return "article";
	}

	@PostMapping("/save")
	public String save(@Valid Article article, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "article";
		}
		articleRepository.save(article);
		return "redirect:/index";
	}

	@GetMapping("/delete")
	public String delete(Long id, int page, String search) {
		articleRepository.deleteById(id);
		return "redirect:/index?page=" + page + "&search=" + search;
	}
}
