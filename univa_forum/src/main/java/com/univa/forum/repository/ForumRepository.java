package com.univa.forum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.univa.forum.domain.ForumFile;
import com.univa.forum.domain.ForumPost;
import com.univa.forum.domain.ForumSubject;
import com.univa.forum.domain.ForumUser;
import com.univa.forum.domain.ForumUserGrade;

public class ForumRepository {
	private final EntityManager em;
	
	public ForumRepository(EntityManager em) {
		this.em = em;
	}
	
	public ForumUser save(ForumUser user) {
		em.persist(user);
		return user;
	}
	
	public ForumPost save(ForumPost forum) {
		em.persist(forum);
		return forum;
	}
	
	public Optional<ForumUser> findUserByIdx(int idx) {
		ForumUser user = em.find(ForumUser.class, idx);
		return Optional.ofNullable(user);
	}
	
	public Optional<ForumUser> findUserByUsername(String username) {
		List<ForumUser> result = em.createQuery("select u from user u where u.username = :username", ForumUser.class)
				.setParameter("username", username)
				.getResultList();
		return result.stream().findAny();
	}
	
	public Optional<ForumUser> findUserByUsernameAndPassword(ForumUser user) {
		return em.createQuery("select u from user u where username = :username AND password = :password", ForumUser.class)
		.setParameter("username", user.getUsername())
		.setParameter("password", user.getPassword()).getResultList().stream().findAny();
	}
	
	public Optional<ForumUserGrade> findGradeByIdx(int idx) {
		ForumUserGrade grade = em.find(ForumUserGrade.class, idx);
		return Optional.ofNullable(grade);
	}
	
	public Optional<ForumPost> findForumByIdx(int idx) {
		ForumPost forum = em.find(ForumPost.class, idx);
		return Optional.ofNullable(forum);
	}
	
	public List<ForumSubject> findAllSubject() {
		return em.createQuery("select s from subject s", ForumSubject.class).getResultList();
	}
	
	public Optional<ForumSubject> findSubjectByIdx(int idx) {
		ForumSubject subject = em.find(ForumSubject.class, idx);
		return Optional.ofNullable(subject);
	}
	
	public List<ForumPost> findAllForumPost() {
		return em.createQuery("select f from forum f order by f.update_date desc", ForumPost.class).getResultList();
	}
	public List<ForumPost> findForumPostByTypeSetLimit(int type, int firstIdx, int max, int user) {
		return em.createQuery("select f from forum f where type = :type and user_idx = :user and state IN( 0, 60 ) order by f.update_date desc", ForumPost.class)
				.setParameter("type", type)
				.setParameter("user", user)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	public List<ForumPost> findForumHeaderListSetLimit(int firstIdx, int max) {
		return em.createQuery("select f from forum f where parent_idx is null and state IN(0, 60) order by f.update_date desc",
				ForumPost.class)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	public List<ForumPost> findForumHeaderList(){
		return em.createQuery("select f from forum f where parent_idx is null and state IN(0, 60) order by f.update_date desc",
				ForumPost.class)
				.getResultList();
	}
	public List<ForumPost> findForumHeaderListSetLimitAndUser(int firstIdx, int max, int user) {
		return em.createQuery("select f from forum f where user_idx = :user and parent_idx is null and state in (0, 60) order by f.update_date desc", ForumPost.class)
				.setParameter("user", user)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	public List<ForumPost> findForumListBySubject(int firstIdx, int max, int[] subjects) {
		String query = "select distinct f from forum f, forum_subject fs ";
		if(subjects.length > 0) {
			query += "where ( ";
			for(int i=0; subjects.length>i; i++) {
				query += "fs.subject ="+subjects[i];
				if(i < subjects.length-1) query += " OR ";
			}
			query += ") and fs.forum = f.idx ";
		}
		query += "and parent_idx is null and state in(0, 60) "
				+ "order by f.update_date desc";
		return em.createQuery(query, ForumPost.class)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	public List<ForumPost> findForumHeaderListByTitle(int firstIdx, int max, String title, String sort) {
		String sortValue = "f."+this.ForumSortValueSet(sort);
		title = "%"+title+"%";
		return em.createQuery("select f "
				+ "from forum f "
				+ "where f.title like :title "
				+ "and parent_idx is null and state IN(0, 60) "
				+ "order by "+sortValue+" desc", ForumPost.class)
				.setParameter("title", title)
				.getResultList();
	}
	public List<ForumPost> findForumHeaderListByTitleAndSubject(int firstIdx, int max, int[] subjects, String title, String sort){
		String sortValue = "f."+this.ForumSortValueSet(sort);
		title = "%"+title+"%";
		String query = "select distinct f from forum f, forum_subject fs ";
		if(subjects.length > 0) {
			query += "where ( ";
			for(int i=0; subjects.length > i; i++) {
				query += "fs.subject ="+subjects[i];
				if(i < subjects.length-1)query += " OR ";
			}
			query += " ) and fs.forum = f.idx ";
		}
		query += "and title like :title and parent_idx is null and state IN(0, 60) "
				+ "order by "+sortValue+" desc";
		return em.createQuery(query, ForumPost.class)
				.setParameter("title", title)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	
	public Long findForumCountByUserIdxSetType(int user_idx, int type) {
		return em.createQuery("select count(f.idx) from forum f where type=:type and user_idx=:user_idx order by f.idx desc", Long.class)
				.setParameter("type", type)
				.setParameter("user_idx", user_idx)
				.getSingleResult();
	}
	public List<ForumPost> findForumByUserIdx(int user_idx) {
		return em.createQuery("select f from forum f where user_idx = :user_idx and state in(0, 60) order by f.update_date desc", ForumPost.class)
				.setParameter("user_idx", user_idx)
				.getResultList();
	}
	public List<ForumPost>findForumByState(int state) {
		return em.createQuery("select f from forum f where state = :state order by f.update_date desc", ForumPost.class)
				.setParameter("state", state)
				.getResultList();
	}
	
	public Long findAllMyForumRecommendedCount(int user_idx) {
		String query = "select count(fr.forum)"
				+" from forum f, forum_recommend fr"
				+" where f.user.idx = :user_idx and fr.forum = f.idx and fr.like >= 1";
		return em.createQuery(query, Long.class)
				.setParameter("user_idx", user_idx)
				.getSingleResult();
	}
	
	public String ForumSortValueSet(String str) {
		String sortValue = "";
		switch(str) {
		case "hits": 
			sortValue = "hits";
			break;
		case "date": 
		default: 
			sortValue = "update_date";
			break;
		}
		return sortValue;
	}
	
	public ForumFile findFile(int idx) {
		ForumFile file = em.find(ForumFile.class, idx);
		return file;
	}
}
