package cz.fi.muni.pa165.service;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import cz.fi.muni.pa165.dao.CategoryDao;
import cz.fi.muni.pa165.entity.Category;
import java.util.List;

/**
 * Implementation of the {@link ProductService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */

@Service
public class CategoryServiceImpl implements CategoryService {
	@Inject
	private CategoryDao categoryDao;

	@Override
	public Category findById(Long id) {
		return categoryDao.findById(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	public void create(Category category) {
		categoryDao.create(category);
	}

	@Override
	public void remove(Category c) {
		categoryDao.delete(c);
	}

	@Override
	public Category findByName(String categoryName) {
		return categoryDao.findByName(categoryName);
	}


}
