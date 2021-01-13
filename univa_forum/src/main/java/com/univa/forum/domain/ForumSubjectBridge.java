package com.univa.forum.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;

@Entity(name="forum_subject")
@DynamicInsert
public class ForumSubjectBridge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@ManyToOne(targetEntity = ForumPost.class, fetch=FetchType.EAGER)
	@JoinColumn(name="forum_idx")
	private ForumPost forum;
	
	@ManyToOne(targetEntity = ForumSubject.class, fetch=FetchType.EAGER)
	@JoinColumn(name="subject_idx")
	private ForumSubject subject;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public ForumPost getForum() {
		return forum;
	}

	public void setForum(ForumPost forum) {
		this.forum = forum;
	}

	public ForumSubject getSubject() {
		return subject;
	}

	public void setSubject(ForumSubject subject) {
		this.subject = subject;
	}
}
