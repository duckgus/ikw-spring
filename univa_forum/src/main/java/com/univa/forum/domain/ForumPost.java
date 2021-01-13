package com.univa.forum.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name="forum")
public class ForumPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@ManyToOne(targetEntity=ForumUser.class, fetch=FetchType.EAGER)
	@JoinColumn(name="user_idx")
	private ForumUser user;
	
	@ManyToOne(targetEntity=ForumPost.class, fetch=FetchType.LAZY)
	@JoinColumn(name="parent_idx")
	private ForumPost parent;
	
	@OneToMany(targetEntity=ForumPost.class, mappedBy = "parent", fetch=FetchType.EAGER)
	private List<ForumPost> children;
	
	@Column(columnDefinition = "UNSIGNED INT")
	private int type;
	
	@Column(name = "title", length=255)
	private String title;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	
	@OneToOne(targetEntity = ForumPost.class, fetch=FetchType.EAGER)
	@JoinColumn(name="history_parent_idx")
	private ForumPost history_parent;
	
	@Column(name = "update_date", insertable=false, updatable=false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	//@Column(name = "update_date", columnDefinition = "CURRENT_TIMESTAMP")
	private LocalDateTime update_date;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "hits")
	private int hits;
	
	@ManyToOne(targetEntity=ForumPost.class, fetch=FetchType.LAZY)
	@JoinColumn(name="modifying_parent_idx")
	private ForumPost modifying_parent;
	
	@OneToMany(targetEntity=ForumPost.class, mappedBy = "modifying_parent", fetch=FetchType.EAGER)
	private Set<ForumPost> modifyChildren;
	
	@OneToMany(targetEntity=ForumFile.class, mappedBy="forum", cascade = CascadeType.PERSIST)
	private List<ForumFile> files;
	
	@OneToMany(targetEntity=ForumSubjectBridge.class, mappedBy="forum", cascade = CascadeType.PERSIST)
	private Collection<ForumSubjectBridge> subjects;
	
	@OneToMany(targetEntity=ForumRecommend.class, mappedBy="forum", cascade = CascadeType.PERSIST)
	private Set<ForumRecommend> forumRecommend;
	
	@OneToOne(targetEntity=ForumModify.class, mappedBy="forum", cascade = CascadeType.PERSIST)
	private ForumModify forumModify;
	
	/* 컬럼이 아닌 프로퍼티(리턴용 데이터?) */
	@Transient
	private int recommendedCount;
	
	@Transient
	private Boolean recommended = false; 
	
	@Transient
	private int childrenCount;
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public ForumUser getUser() {
		return user;
	}

	public void setUser(ForumUser user) {
		this.user = user;
	}

	public ForumPost getParent() {
		return parent;
	}

	public void setParent(ForumPost parent) {
		this.parent = parent;
	}

	public List<ForumPost> getChildren() {
		return children;
	}

	public void setChildren(List<ForumPost> children) {
		this.children = children;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public List<ForumFile> getFiles() {
		if(files == null) files = new ArrayList<ForumFile>();
		return files;
	}
	public void setFiles(List<ForumFile> files) {
		this.files = files;
	}
	public void addFiles(ForumFile file) {
		List<ForumFile> files = getFiles();
		files.add(file);
	}

	public Collection<ForumSubjectBridge> getSubjects() {
		if( subjects == null) {
			subjects = new ArrayList<ForumSubjectBridge>();
		}
		return subjects;
	}

	public void setSubjects(Collection<ForumSubjectBridge> subjects) {
		this.subjects = subjects;
	}
	public void addSubjects(ForumSubjectBridge subject) {
		Collection<ForumSubjectBridge> subjects = getSubjects();
		subjects.add(subject);
	}

	public Set<ForumRecommend> getForumRecommend() {
		if(forumRecommend == null) {
			forumRecommend = new HashSet<ForumRecommend>();
		}
		return forumRecommend;
	}

	public void setForumRecommend(Set<ForumRecommend> forumRecommend) {
		this.forumRecommend = forumRecommend;
	}
	
	public void addForumRecommend(ForumRecommend forumRecommend) {
		Set<ForumRecommend> recommends = getForumRecommend();
		recommends.add(forumRecommend);
	}

	public int getRecommendedCount() {
		return recommendedCount;
	}

	public void setRecommendedCount(int recommend) {
		this.recommendedCount = recommend;
	}

	public Boolean getRecommended() {
		return recommended;
	}

	public void setRecommended(Boolean recoomended) {
		this.recommended = recoomended;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	public ForumPost getModifying_parent() {
		return modifying_parent;
	}

	public void setModifying_parent(ForumPost modifying_parent) {
		this.modifying_parent = modifying_parent;
	}

	public ForumPost getHistory_parent() {
		return history_parent;
	}

	public void setHistory_parent(ForumPost history_parent) {
		this.history_parent = history_parent;
	}

	public Set<ForumPost> getModifyChildren() {
		return modifyChildren;
	}

	public void setModifyChildren(Set<ForumPost> modifyChildren) {
		this.modifyChildren = modifyChildren;
	}

	public ForumModify getForumModify() {
		return forumModify;
	}

	public void setForumModify(ForumModify forumModify) {
		this.forumModify = forumModify;
	}
	
}
