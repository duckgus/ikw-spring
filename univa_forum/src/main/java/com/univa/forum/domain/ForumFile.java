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

@Entity(name = "forum_file")
@DynamicInsert
public class ForumFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="forum_idx")
	private ForumPost forum;
	/* 또는 */
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="forum_idx", nullable = false)
	private ForumPost forum;*/
	
	@Column(name="file_url")
	private String file_url;
	
	@Column(name="file_size", columnDefinition = "UNSIGNED INT")
	private int file_size;
	
	@Column(name="original_name")
	private String name;
	
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

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public int getFile_size() {
		return file_size;
	}

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
