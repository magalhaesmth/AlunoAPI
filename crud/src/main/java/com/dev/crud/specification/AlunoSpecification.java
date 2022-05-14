//package com.dev.crud.specification;
//
//import javax.persistence.criteria.CriteriaBuilder;
//
//public class AlunoSpecification {
//
//	private Aluno filter;
//	
//	public AlunoSpecification(Aluno filter) {
//		super();
//		this.filter = filter;
//	}
//	
//	@Override
//	public Predicate toPredicate(Root<Aluno> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//		
//		Predicate p = cb.disjunction();
//		
//		if (filter.getNome() != null) {
//			p.getExpressions().add(cb.like(root.get("nome"), "%" + filter.getNome() + "%"));
//		}
//	}
//}
