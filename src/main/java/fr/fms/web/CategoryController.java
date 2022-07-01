package fr.fms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;

@Controller
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ArticleRepository articleRepository;

	@GetMapping("/category/{id}")
	public String index(Model model, @PathVariable int id) {
		System.out.println("id =" + id);
		articleRepository.findByCategoryId((long) id).forEach(article -> System.out.println(article));
		articleRepository.findAll().forEach(article -> System.out.println(article));
//		Page<Article> articles = articleRepository.findByCategoryId((long) id, PageRequest.of(page - 1, 5));

//		List<Article> articles = articleRepository.findByCategoryId((long) id);
//		System.out.println(articles);
//		model.addAttribute("articles", articles);l
		
//		model.addAttribute("pages", new int[articles.getTotalPages()]);
//		model.addAttribute("currentPage", page);
		return "articlesByCategories";
	}
}
