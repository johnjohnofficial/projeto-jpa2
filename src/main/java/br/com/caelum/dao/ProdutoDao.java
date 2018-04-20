package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}

	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
		
		// CriteriaBuilder é uma fábrica auxiliar para criar expressões sobre as funções que utilizaremos na busca. 
		// A fábrica não executa a query, ela apenas ajuda a criá-la.
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		// Inicialmente invocamos o método createQuery passando para ele a classe que é o tipo de retorno da nossa consulta.
		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
		// O root é usado para definir os caminhos (Path) até os atributos do objeto.
		Root<Produto> root = query.from(Produto.class);

		// caminho até o atributo NOME do produto selecionado
		Path<String> nomePath = root.<String> get("nome");
		// caminho até o atributo LOJA do produto selecionado
		Path<Integer> lojaPath = root.<Loja> get("loja").<Integer> get("id");
		//caminho até o atributo CATEGORIAS do produto selecionado
		Path<Integer> categoriaPath = root.join("categorias").<Integer> get("id");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!nome.isEmpty()) {
			Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome + "%");
			predicates.add(nomeIgual);
		}
		if (categoriaId != null) {
			Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath, categoriaId);
			predicates.add(categoriaIgual);
		}
		if (lojaId != null) {
			Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
			predicates.add(lojaIgual);
		}

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Produto> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();

	}
	
//	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
//		
//		// CriteriaBuilder é uma fábrica auxiliar para criar expressões sobre as funções que utilizaremos na busca. 
//		// A fábrica não executa a query, ela apenas ajuda a criá-la.
//		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//		// Inicialmente invocamos o método createQuery passando para ele a classe que é o tipo de retorno da nossa consulta.
//		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
//		// O root é usado para definir os caminhos (Path) até os atributos do objeto.
//		Root<Produto> root = query.from(Produto.class);
//
//		// caminho até o atributo NOME do produto selecionado
//		Path<String> nomePath = root.<String> get("nome");
//		// caminho até o atributo LOJA do produto selecionado
//		Path<Integer> lojaPath = root.<Loja> get("loja").<Integer> get("id");
//		//caminho até o atributo CATEGORIAS do produto selecionado
//		Path<Integer> categoriaPath = root.join("categorias").<Integer> get("id");
//
//		List<Predicate> predicates = new ArrayList<Predicate>();
//		Predicate conjuncao = criteriaBuilder.conjunction();
//
//		if (!nome.isEmpty()) {
//			Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome + "%");
//			conjuncao = criteriaBuilder.and(nomeIgual);
//		}
//		if (categoriaId != null) {
//			Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath, categoriaId);
//			conjuncao = criteriaBuilder.and(conjuncao, categoriaIgual);
//		}
//		if (lojaId != null) {
//			Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
//			conjuncao = criteriaBuilder.and(conjuncao, lojaIgual);
//		}
//
//		query.where(conjuncao);
//
//		TypedQuery<Produto> typedQuery = em.createQuery(query);
//		return typedQuery.getResultList();
//
//	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
