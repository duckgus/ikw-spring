package univa.univa2.forum.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ForumPostDTO {
	private int user_idx;
	private int parent_idx;
	private int type;
	private String title;
	private String content;
	private int history_parent_idx;
	private LocalDateTime update_date;
	private int state;
	private int hits;
	private List<Integer> subjects;
	private List<MultipartFile> files;
	private List<String> file_url;
	private List<Integer> file_idx;
	private int modify_parent_idx;
	
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public int getParent_idx() {
		return parent_idx;
	}
	public void setParent_idx(int parent_idx) {
		this.parent_idx = parent_idx;
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
	public int getHistory_parent_idx() {
		return history_parent_idx;
	}
	public void setHistory_parent_idx(int history_parent_idx) {
		this.history_parent_idx = history_parent_idx;
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
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public void setSubjects(List<Integer> subjects) {
		this.subjects = subjects;
	}
	public List<Integer> getSubjects() {
		return subjects;
	}
	public int getModify_parent_idx() {
		return modify_parent_idx;
	}
	public void setModify_parent_idx(int modify_parent_idx) {
		this.modify_parent_idx = modify_parent_idx;
	}
	public List<String> getFile_url() {
		return file_url;
	}
	public void setFile_url(List<String> file_url) {
		this.file_url = file_url;
	}
	public List<Integer> getFile_idx() {
		return file_idx;
	}
	public void setFile_idx(List<Integer> file_idx) {
		this.file_idx = file_idx;
	}
}
