package com.univa.forum.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.univa.forum.domain.ForumFile;
import com.univa.forum.domain.ForumModify;
import com.univa.forum.domain.ForumPost;
import com.univa.forum.domain.ForumRecommend;
import com.univa.forum.domain.ForumSubject;
import com.univa.forum.domain.ForumSubjectBridge;
import com.univa.forum.domain.ForumUser;
import com.univa.forum.domain.ForumUserGrade;
import com.univa.forum.dto.ForumPostDTO;
import com.univa.forum.dto.ForumUserDTO;
import com.univa.forum.repository.ForumRepository;
import com.univa.forum.utils.FileUtil;
import com.univa.forum.utils.StringUtil;

@Transactional
public class ForumService {
	private final ForumRepository forumRepository;
	
	public ForumService(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	/** 회원가입 */
	public String userSignup(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		mUser.setNickname(userDto.getNickname());
		mUser.setEmail(userDto.getEmail());
		if(!userDto.getFile().isEmpty()) {
			//mUser.setImage_url(userDto.getImage_url());
			mUser.setImage_url(imageFileWrite(userDto.getFile()));
		}
		mUser.setNation(userDto.getNation());
		
		if(validateDuplicateUser(mUser)) {
			if( forumRepository.save(mUser).getUsername().equals(userDto.getUsername()) ) {
				return "ok";
			} else {
				return "error";
			}
		} else {
			return "duplicaiton"; //이미 있는 유저
		}
	}
	/** 회원 업데이트 */
	public String updateForumUser(ForumUserDTO user) {
		
		ForumUser originUser = this.findUserByIdx(user.getIdx());
		originUser.setEmail(user.getEmail());
		originUser.setNickname(user.getNickname());
		originUser.setNation(user.getNation());
		if(!user.getFile().isEmpty()) {
			//mUser.setImage_url(userDto.getImage_url());
			originUser.setImage_url(imageFileWrite(user.getFile()));
		}
		
		if( forumRepository.save(originUser).getUsername().equals(user.getUsername())) {
			return "ok";
		} else {
			return "error";
		}
	}
	
	/** 이미지 저장, url 리턴 */
	public String imageFileWrite(MultipartFile file) {
		String dirPath = "uploads/imgs/";
		String randomStr = StringUtil.RandomString(20)+"/";
		String imageUrl = randomStr+"img"+FileUtil.getExtension(file.getOriginalFilename()).get();
		String savePath = dirPath+imageUrl;
		try {
			File mFile = new File(dirPath+randomStr);
			mFile.mkdirs();
		} catch (Exception e) {
			e.getStackTrace();
		}
		FileUtil.FileWrite(file, savePath);
		return imageUrl;
	}
	/** 파일 저장, url 리턴 */
	public String writeFile(MultipartFile file) {
		String dirPath = "uploads/forum_files/";
		String randomStr = StringUtil.RandomString(20)+"/";
		String fileUrl = randomStr+"file"+FileUtil.getExtension(file.getOriginalFilename()).get();
		String savePath = dirPath+fileUrl;
		try {
			File mFile = new File(dirPath+randomStr);
			mFile.mkdirs();
		} catch (Exception e) {
			e.getStackTrace();
		}
		FileUtil.FileWrite(file, savePath);
		return fileUrl;
	}
	
	/** 로그인 */
	public Optional<ForumUserDTO> userSignin(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		
		Optional<ForumUser> foundUser = forumRepository.findUserByUsernameAndPassword(mUser);
		if(foundUser.isPresent()) {
			ForumUser user = foundUser.get();
			ForumUserDTO returnUserDto = new ForumUserDTO();
			returnUserDto.setIdx(user.getIdx());
			returnUserDto.setUsername(user.getUsername());
			//returnUserDto.setPassword(user.getPassword());
			returnUserDto.setNickname(user.getNickname());
			returnUserDto.setEmail(user.getEmail());
			returnUserDto.setImage_url(user.getImage_url());
			returnUserDto.setGrade_idx(user.getGrade().getIdx());
			returnUserDto.setNation(user.getNation());
			returnUserDto.setState(user.getState());
			returnUserDto.setDate(user.getDate());
			return Optional.ofNullable(returnUserDto);
		} else {
			return Optional.ofNullable(null);
		}
	}
	
	/** 인덱스로 게시물 찾기 */
	public ForumPost findOneForumPost(int idx) {
		ForumPost post = forumRepository.findForumByIdx(idx).get();
		post.setRecommendedCount(this.recommendedCount(post.getForumRecommend()));
		post.setChildrenCount(this.findChildrenCount(post));
		return post;
	}
	public ForumPost findOneForumPost(int idx, int userIdx) {
		ForumPost post = forumRepository.findForumByIdx(idx).get();
		
		post.setRecommendedCount(this.recommendedCount(post.getForumRecommend()));
		Set<ForumRecommend> recommend = post.getForumRecommend();
		for (ForumRecommend reco : recommend ) {
			if (reco.getUser().getIdx() == userIdx) {
				if(reco.getLike() > 0) post.setRecommended(true);
			}
		}
		post.setChildrenCount(this.findChildrenCount(post));
		
		return post;
	}
	
	/** 가장 자식이 많은 게시물 정렬 */
	public List<ForumPost> findHeaderForumOrderByChildCnt(int user_idx) {
		List<ForumPost> posts = this.findHeaderForumList(user_idx);
		posts = this.addChildCount(posts);
		
		posts.sort(new Comparator<ForumPost>() {
			@Override
			public int compare(ForumPost post1, ForumPost post2) {
				int cnt1 = post1.getChildrenCount();
				int cnt2 = post2.getChildrenCount();
				
				if(cnt1==cnt2) return 0;
				else if(cnt1 > cnt2) return -1;
				else return 1;
			}
		});
		return posts;
	}
	public List<ForumPost> findHeaderForumOrderByChildCnt() {
		List<ForumPost> posts = this.findHeaderForumList();
		posts = this.addChildCount(posts);
		
		posts.sort(new Comparator<ForumPost>() {
			@Override
			public int compare(ForumPost post1, ForumPost post2) {
				int cnt1 = post1.getChildrenCount();
				int cnt2 = post2.getChildrenCount();
				
				if(cnt1==cnt2) return 0;
				else if(cnt1 > cnt2) return -1;
				else return 1;
			}
		});
		return posts;
	}
	
	/** 유저 비밀번호 검사 */
	public Boolean validateUserPassword(ForumUserDTO userDto, String password) {
		int userIdx = userDto.getIdx();
		Optional<ForumUser> forumUser = forumRepository.findUserByIdx(userIdx); 
		if(forumUser.isPresent()) {
			if(forumUser.get().getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/** 중복검사 */
	public Boolean validateDuplicateUser(ForumUser user) {
		return !forumRepository.findUserByUsername(user.getUsername()).isPresent();
	}
	
	/** 포럼 쓰기, 결과 리턴 */
	public String writeForum(ForumPostDTO forum) {
		ForumPost forumPost = new ForumPost();
		ForumUser user = findUserByIdx(forum.getUser_idx());
		if(user == null) return "user error";
		forumPost.setUser(user);
		forumPost.setType(forum.getType());
		forumPost.setTitle(forum.getTitle());
		forumPost.setContent(forum.getContent());
		forumPost.setState(forum.getState());
		if( forum.getParent_idx() > 0 ) {
			forumPost.setParent(forumRepository.findForumByIdx(forum.getParent_idx()).get());
		}
		if ( forum.getSubjects() != null && forum.getSubjects().size() > 0) {
			for(int subs : forum.getSubjects() ) {
				ForumSubject subject = forumRepository.findSubjectByIdx(subs).get();
				if(subject != null) {
					ForumSubjectBridge subjectEnt = new ForumSubjectBridge();
					subjectEnt.setSubject(subject);
					subjectEnt.setForum(forumPost);
					forumPost.addSubjects(subjectEnt);
				}
			}
		}
			
		if( forum.getFiles() != null && forum.getFiles().size() > 0) {
			for (MultipartFile file : forum.getFiles() ) {
				ForumFile mFile = new ForumFile();
				mFile.setFile_url(writeFile(file));
				mFile.setForum(forumPost);
				mFile.setFile_size((int)file.getSize());
				mFile.setName(file.getOriginalFilename());
				forumPost.addFiles(mFile);
			}
		}
		forumRepository.save(forumPost);
		
		return "ok";
	}
	public String writeForum(ForumPost post) {
		try {
			forumRepository.save(post);
		} catch ( Exception e) {
			return "error";
		}
		
		return "ok";
	}
	
	/* 수정 요청 */
	public String modifyForum(ForumPostDTO forum) {
		ForumPost modifyForumPost = new ForumPost();
		
		ForumPost prePost = this.findOneForumPost(forum.getModify_parent_idx());
		
		ForumUser user = this.findUserByIdx(prePost.getUser().getIdx());
		
		if(user == null) return "user error";
		modifyForumPost.setUser(user);
		modifyForumPost.setTitle(forum.getTitle());
		modifyForumPost.setContent(forum.getContent());
		modifyForumPost.setType(50); // 수정 게시물 타입
		modifyForumPost.setState(10); // 수정 요청 상태
		//modifyForumPost.setUpdate_date(prePost.getUpdate_date());
		if(forum.getSubjects() != null) {
			for(int subs : forum.getSubjects() ) {
				ForumSubject subject = forumRepository.findSubjectByIdx(subs).get();
				if(subject != null) {
					ForumSubjectBridge subjectEnt = new ForumSubjectBridge();
					subjectEnt.setSubject(subject);
					subjectEnt.setForum(modifyForumPost);
					modifyForumPost.addSubjects(subjectEnt);
				}
			}
		}
		
		if( forum.getFiles() != null && forum.getFiles().size() > 0) {
			for (MultipartFile file : forum.getFiles() ) {
				ForumFile mFile = new ForumFile();
				mFile.setFile_url(writeFile(file));
				mFile.setForum(modifyForumPost);
				mFile.setFile_size((int)file.getSize());
				mFile.setName(file.getOriginalFilename());
				modifyForumPost.addFiles(mFile);
			}
		}
		if( forum.getFile_idx() != null && forum.getFile_idx().size() > 0) {
			for(int fileidx : forum.getFile_idx()) {
				ForumFile preFile = this.getFile(fileidx);
				ForumFile mFile = new ForumFile();
				mFile.setFile_url(preFile.getFile_url());
				mFile.setForum(modifyForumPost);
				mFile.setFile_size(preFile.getFile_size());
				mFile.setName(preFile.getName());
				
				modifyForumPost.addFiles(mFile);
			}
		}
		
		modifyForumPost.setModifying_parent(forumRepository.findForumByIdx(forum.getModify_parent_idx()).get());
		ForumModify forumModify = new ForumModify();
		forumModify.setForum(modifyForumPost);
		forumModify.setUser( findUserByIdx(forum.getUser_idx()) );
		
		modifyForumPost = forumRepository.save(modifyForumPost);
		modifyForumPost.setForumModify(forumModify);
		modifyForumPost.setType(50);
		forumRepository.save(modifyForumPost);
		
		return "ok";
	}
	
	/* 수정 승인 */
	public String modifyForumApproval(int idx, int user_idx) {
		ForumUser user = forumRepository.findUserByIdx(user_idx).orElse(null);
		ForumPost prePost = forumRepository.findForumByIdx(idx).get().getModifying_parent();
		
		if( user == null 
				|| (user != null && !prePost.getUser().equals(user)) ) {
			return "user approval error";
		}
		
		ForumPost modifyingPost = forumRepository.findForumByIdx(idx).get();
		
		Set<ForumPost> deletePost = prePost.getModifyChildren();
		for(ForumPost forumModify : deletePost) {
			forumModify.setState(30); // 선택되지 않은 수정요청 게시물
		}
		prePost.setState(20); // 이력으로 남은 이전게시물
		modifyingPost.setState(0); // 수정된 게시물
		
		// 승인자 
		modifyingPost.getForumModify().setApproval_user(user);
		modifyingPost.getForumModify().setApproval_date(LocalDateTime.now());
		
		modifyingPost.setType(prePost.getType());
		if ( prePost.getParent() != null ) modifyingPost.setParent(prePost.getParent());
		modifyingPost.setHits(prePost.getHits()); //조회수
		//modifyingPost.setChildren(prePost.getChildren()); //자식들
		for (ForumPost child : prePost.getChildren() ) {
			child.setParent(modifyingPost);
		}
		//modifyingPost.setForumRecommend(prePost.getForumRecommend()); // 추천한 유저들
		for ( ForumRecommend reco : prePost.getForumRecommend() ) {
			reco.setForum(modifyingPost);
		}
		modifyingPost.setHistory_parent(prePost);
		forumRepository.save(prePost);
		forumRepository.save(modifyingPost);
		
		return "ok";
	}
	
	/** 모든 주제 찾기 */
	public List<ForumSubject> findAllSubject() {
		return forumRepository.findAllSubject();
	}
	
	
	/** 유저 찾기 */
	public ForumUser findUserByIdx(int idx) {
		return forumRepository.findUserByIdx(idx).orElse(null);
	}
	
	/** 모든 주제 포럼 게시물 리스트 */
	public List<ForumPost> findHeaderForumList(int first, int max, int user_idx) {
		List<ForumPost> posts = forumRepository.findForumHeaderListSetLimit(first, max);
		posts = this.addRecommendCount(posts, user_idx);
		
		return posts;
	}
	public List<ForumPost> findHeaderForumList(int user_idx) {
		List<ForumPost> posts = forumRepository.findForumHeaderList();
		posts = this.addRecommendCount(posts, user_idx);
		posts = this.addChildCount(posts);
		
		return posts;
	}
	public List<ForumPost> findHeaderForumList() {
		List<ForumPost> posts = forumRepository.findForumHeaderList();
		posts = this.addRecommendCount(posts);
		posts = this.addChildCount(posts);
		
		return posts;
	}
	/** 특정 주제, 제목 검색 포럼 게시물 리스트 */
	public List<ForumPost> findHeaderForumList(
			int first, int max, 
			int user_idx, int[] subjects, 
			String title, String sort) {
		List<ForumPost> posts = forumRepository.findForumHeaderListByTitleAndSubject(first, max, subjects, title, sort);
		posts = this.addRecommendCount(posts, user_idx);
		posts = this.addChildCount(posts);
		return posts;
	}
	/** 모든 주제 제목검색 포럼 게시물 리스트 */
	public List<ForumPost> findHeaderForumList(
			int first, int max, int user_idx,
			String title, String sort) {
		List<ForumPost> posts = forumRepository.findForumHeaderListByTitle(first, max, title, sort);
		posts = this.addRecommendCount(posts, user_idx);
		posts = this.addChildCount(posts);
		return posts;
	}
	
	/** 특정 주제 포럼 게시물 리스트 */
	public List<ForumPost> findQuestionsBySubject(int first, int max, int[] subjects, int user_idx){
		List<ForumPost> posts = forumRepository.findForumListBySubject(first, max, subjects);
		posts = this.addRecommendCount(posts, user_idx);
		posts = this.addChildCount(posts);
		return posts;
	}
	
	/** 유저 등급 리턴 */
	public ForumUserGrade findOneGrade(int idx) {
		return forumRepository.findGradeByIdx(idx).orElse(null);
	}
	
	/** 나의 포럼 게시물 수 타입별 리턴 */
	public Long findMyForumCountSetType(int user_idx, int type) {
		return forumRepository.findForumCountByUserIdxSetType(user_idx, type);	
	}
	
	/** 나의 포럼 게시물 */
	public List<ForumPost> findMyFormList(int first, int max, int user_idx, int type) {
		List<ForumPost> posts = forumRepository.findForumPostByTypeSetLimit(type, first, max, user_idx);
		posts = this.addRecommendCount(posts, user_idx);
		return posts;
	}
	
	/** 나의 모든 포럼의 받은 추천 수*/
	public Long findMyForumRecommendedCount(int user_idx) {
		return forumRepository.findAllMyForumRecommendedCount(user_idx);
	}
	
	/** 나의 모든 포럼 질문 */
	public List<ForumPost> findMyQuestionHeaderList(int first, int max, int user_idx) {
		//return forumRepository.findForumHeaderListSetLimitAndUser(first, max, user_idx);
		List<ForumPost> posts = new ArrayList<ForumPost>();
		posts = forumRepository.findForumHeaderListSetLimitAndUser(first, max, user_idx);
		posts = this.addRecommendCount(posts, user_idx);
		posts = this.addChildCount(posts);
		
		return posts;
	}
	
	/** 내가 관여한 모든 포럼의 루트 질문 */
	public List<ForumPost> findInvolvedHeaderList(int user_idx) {
		List<ForumPost> allPosts = forumRepository.findForumByUserIdx(user_idx);
		List<ForumPost> involvedPosts = new ArrayList<ForumPost>();
		for(ForumPost post : allPosts) {
			ForumPost rootPost = this.getRoot(post);
			if(!involvedPosts.contains(rootPost)) involvedPosts.add(rootPost);
		}
		
		involvedPosts = this.addRecommendCount(involvedPosts, user_idx);
		involvedPosts = this.addChildCount(involvedPosts);
		
		return involvedPosts;
	}
	
	/** 내가 관여한 모든 포럼중 수정신청된 루트 질문 */
	public List<ForumPost> findRequestedInvolvedHeaderList(int user_idx) {
		List<ForumPost> allPosts = forumRepository.findForumByUserIdx(user_idx);
		
		List<ForumPost> requestedHeaderPosts = new ArrayList<ForumPost>();
		for(ForumPost post : allPosts) {
			
			if(post.getModifyChildren() != null && post.getModifyChildren().size() > 0) {
				ForumPost rootPost = this.getRoot(post);
				if(!requestedHeaderPosts.contains(rootPost)) {
					requestedHeaderPosts.add(rootPost);
				}
			}
		}
		
		requestedHeaderPosts = this.addRecommendCount(requestedHeaderPosts, user_idx);
		requestedHeaderPosts = this.addChildCount(requestedHeaderPosts);
		
		return requestedHeaderPosts;
	}
	
	/** 게시글 과 자식 모두 추천수 추가 */
	public List<ForumPost> addRecommendCount(List<ForumPost> posts) {
		for(ForumPost post: posts) {
			post.setRecommendedCount(this.recommendedCount(post.getForumRecommend()));
			if(post.getChildren() != null) this.addRecommendCount(post.getChildren());
		}
		return posts;
	}
	
	/** 게시글 과 자식 모두 추천수, 내가 추천했는지 추가 */
	public List<ForumPost> addRecommendCount(List<ForumPost> posts, int user_idx) {
		for(ForumPost post: posts) {
			post.setRecommendedCount(this.recommendedCount(post.getForumRecommend()));
			if(user_idx > 0) {
				for(ForumRecommend reco : post.getForumRecommend()) {
					if(reco.getUser().getIdx() == user_idx) {
						if (reco.getLike() > 0) post.setRecommended(true); 
					}
				}
			}
			if(post.getChildren() != null) this.addRecommendCount(post.getChildren(), user_idx);
		}
		return posts;
	}
	
	/** 게시물에 모든 자식 수 할당*/
	public List<ForumPost> addChildCount(List<ForumPost> posts) {
		for(ForumPost post: posts) {
			post.setChildrenCount(this.findChildrenCount(post));
		}
		return posts;
	}
	
	/** 게시글의 모든 자식 수 */
	public int findChildrenCount(ForumPost post) {
		int count = 0;
		count += post.getChildren().size();
		for(ForumPost forumPost : post.getChildren()) {
			count += this.findChildrenCount(forumPost);
		}
		return count;
	}
	
	/** 게시글 최상위 루트 찾기 */
	public ForumPost getRoot(ForumPost post) {
		while (post.getParent() != null) {
			post = post.getParent();
		}
		return post;
	}
	
	/** 게시글 이력 불러오기 */
	public List<ForumPost> getHisotry(ForumPost post) {
		List<ForumPost> history = new ArrayList<ForumPost>();
		while(post.getHistory_parent() != null) {
			history.add(0, post.getHistory_parent());
			post = post.getHistory_parent();
		}
		return history;
	}
	
	/** 게시글 좋아요 숫자 계산 */
	public int recommendedCount(Set<ForumRecommend> list) {
		int result = 0;
		for(ForumRecommend reco : list) {
			if(reco.getLike()>0) result++;
		}
		return result;
	}
	
	/** 게시글 추천 */
	public Integer recommend(int forum_idx, ForumUserDTO user, Boolean flag) {
		ForumPost post = this.findOneForumPost(forum_idx);
		Set<ForumRecommend> recoList = post.getForumRecommend();
		
		for(ForumRecommend reco: recoList) {
			if (reco.getUser().getIdx() == user.getIdx()) {
				if(flag) reco.setLike(1);
				else reco.setLike(0);
				forumRepository.save(post);
				return this.recommendedCount(post.getForumRecommend());
			}
		}
		
		ForumUser mUser = this.findUserByIdx(user.getIdx());
		ForumRecommend reco = new ForumRecommend();
		reco.setForum(post);
		reco.setUser(mUser);
		if(flag)reco.setLike(1);
		else reco.setLike(0);
		
		post.addForumRecommend(reco);
		forumRepository.save(post);
		return this.recommendedCount(post.getForumRecommend());
	}
	
	/** 파일 url 받아오기 */
	public String getFileUrl(int idx) {
		return forumRepository.findFile(idx).getFile_url();
	}
	
	/** 파일 받아오기 */
	public ForumFile getFile(int idx) {
		return forumRepository.findFile(idx);
	}
	
	/** 삭제요청 게시물 리스트 */
	public List<ForumPost> getDeleteRequestForumList(){
		List<ForumPost> posts = forumRepository.findForumByState(60);
		return posts;
	}
	
	/** 유저 정보 업데이트 */
	public ForumUser updateForumUser(ForumUser user) {
		return forumRepository.save(user);
	}
}
