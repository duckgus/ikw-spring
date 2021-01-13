package com.univa.forum.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="subject")
public class ForumSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@Column(name = "name", length=255)
	private String name;
	
	@OneToMany(mappedBy = "subject")
	private Set<ForumSubjectBridge> forum;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ForumSubjectBridge> getForum() {
		return forum;
	}

	public void setForum(Set<ForumSubjectBridge> forum) {
		this.forum = forum;
	}
}
