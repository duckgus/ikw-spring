package com.univa.forum.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="forum_modify")
public class ForumModify {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@ManyToOne(targetEntity = ForumPost.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "forum_idx")
	private ForumPost forum;
	
	@ManyToOne(targetEntity = ForumUser.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "user_idx")
	private ForumUser user;
	
	@Column(name = "update_date", insertable = false, updatable=false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime update_date;
	
	@ManyToOne(targetEntity = ForumUser.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "approval_user")
	private ForumUser approval_user;
	
	@Column(name = "approval_date")
	private LocalDateTime approval_date;

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

	public ForumUser getUser() {
		return user;
	}

	public void setUser(ForumUser user) {
		this.user = user;
	}

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

	public ForumUser getApproval_user() {
		return approval_user;
	}

	public void setApproval_user(ForumUser approval_user) {
		this.approval_user = approval_user;
	}

	public LocalDateTime getApproval_date() {
		return approval_date;
	}

	public void setApproval_date(LocalDateTime approval_date) {
		this.approval_date = approval_date;
	}
}
