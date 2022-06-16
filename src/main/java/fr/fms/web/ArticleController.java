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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Controller
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/index")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "search", defaultValue = "") String search) {

		List<Category> categories = categoryRepository.findAll();
		Page<Article> articles = articleRepository.findByDescriptionContainsOrBrandContains(search, search,
				PageRequest.of(page - 1, 5));
		model.addAttribute("title", "Tous les articles");
		model.addAttribute("articles", articles.getContent());
		model.addAttribute("categories", categories);
		model.addAttribute("pages", new int[articles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("search", search);

		return "articles";
	}

	@GetMapping("/articlesByCat")
	public String articleByCat(Model model, @RequestParam(name = "category", defaultValue = "") int catId,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		List<Category> categories = categoryRepository.findAll();
		Page<Article> articles = articleRepository.findByCategoryId((long) catId, PageRequest.of(page - 1, 5));
		Category category = categoryRepository.findById((long) catId).get();
		model.addAttribute("title", "Catégorie : " + category.getName());
		model.addAttribute("articlesByCat", true);
		model.addAttribute("currentCat", catId);
		model.addAttribute("articles", articles.getContent());
		model.addAttribute("categories", categories);
		model.addAttribute("pages", new int[articles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "articles";
	}

	@GetMapping("/add")
	public String article(Model model) {
		model.addAttribute("add", true);
		model.addAttribute("title", "Ajouter un article");
		model.addAttribute("article", new Article());
		model.addAttribute("categories", categoryRepository.findAll());
		return "article";
	}

	@PostMapping("/save")
	public String save(Model model, @Valid Article article, BindingResult bindingResult,
			RedirectAttributes attributes) {
		model.addAttribute("categories", categoryRepository.findAll());
		if (bindingResult.hasErrors()) {
			return "article";
		}
		articleRepository.save(article);
//		attributes.addFlashAttribute("success", "Article bien enregistré");
		return "redirect:/index";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable(name = "id") int id) {
		Article article = articleRepository.getReferenceById((long) id);
		model.addAttribute("edit", true);
		model.addAttribute("title", "Modifier un article");
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("article", article);
		return "article";
	}

	@GetMapping("/delete")
	public String delete(Long id, int page, String search) {
		articleRepository.deleteById(id);
		return "redirect:/index?page=" + page + "&search=" + search;
	}

}
