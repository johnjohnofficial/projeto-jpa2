package br.com.caelum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.caelum.model.Produto;

public class TesteLockPessimista {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfigurator.class);
		EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
		
		EntityManager em1 = emf.createEntityManager();
		EntityManager em2 = emf.createEntityManager();
		
		em1.getTransaction().begin();
		em2.getTransaction().begin();
		
		Produto produto = em1.find(Produto.class, 1);
		em1.lock(produto, LockModeType.PESSIMISTIC_WRITE);
		
		produto.setNome("Maria");
		
		Produto produto2 = em2.find(Produto.class, 1);
		em2.lock(produto2, LockModeType.PESSIMISTIC_WRITE);
	}

}
