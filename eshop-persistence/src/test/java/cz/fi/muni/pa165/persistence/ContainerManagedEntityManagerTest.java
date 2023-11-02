package cz.fi.muni.pa165.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.CategoryDao;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class ContainerManagedEntityManagerTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	@PersistenceContext 
	private EntityManager em;
	
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void nameNotNull(){
		Category cat = new Category();
		cat.setName(null);
		categoryDao.create(cat);		
	}
	
	@Test(expectedExceptions=DataAccessException.class)
	public void nameUnique(){
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);	
		cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);
	}
	

	@Test
	public void createCategory(){
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);
		
		em.flush();
		em.clear();
		
		Category created = categoryDao.findById(cat.getId());
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> p = cq.from(Product.class);
		cq.select(p).where(cb.equal(p.get("name"),"Guitar"));
		TypedQuery<Product> tq= em.createQuery(cq);
		tq.getResultList();
		
		
		Assert.assertEquals(created, cat);		
	}
	
	@Test
	public void productsInCategory(){
		Category categoryElectro = new Category();
		categoryElectro.setName("Electronics");
		categoryDao.create(categoryElectro);	
		
		Product p = new Product();
		p.setName("TV");
		
		productDao.create(p);
		p.addCategory(categoryElectro);
		
		em.flush();
		em.clear();
		
		Category found = categoryDao.findById(categoryElectro.getId());
		Assert.assertEquals(found.getProducts().size(), 1);
		Assert.assertEquals(found.getProducts().iterator().next().getName(), "TV");
	}
}
