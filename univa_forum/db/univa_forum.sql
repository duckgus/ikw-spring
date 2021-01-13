/* 유저 등급 테이블 생성 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`grade` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `grade` INT UNSIGNED NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idx`))
ENGINE = InnoDB

/* 유저 테이블 생성*/
CREATE TABLE IF NOT EXISTS `univa_forum`.`user` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NULL,
  `image_url` VARCHAR(255) NULL,
  `grade_idx` INT UNSIGNED NOT NULL DEFAULT 0,
  `nation` VARCHAR(255) NULL,
  `state` INT NOT NULL DEFAULT 1,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idx`),
  INDEX `fk_user_grade1_idx` (`grade_idx` ASC) VISIBLE,
  CONSTRAINT `fk_user_grade1`
    FOREIGN KEY (`grade_idx`)
    REFERENCES `univa_forum`.`grade` (`idx`)
    ON DELETE SET DEFAULT
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 포럼 테이블 생성 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`forum` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_idx` INT UNSIGNED NOT NULL DEFAULT 0,
  `parent_idx` INT UNSIGNED NULL,
  `type` INT UNSIGNED NOT NULL COMMENT '글의 타입 (answer 인지 question 인지)\n',
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `delete_request` INT NULL,
  `history_parent_idx` INT UNSIGNED NULL,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` INT NOT NULL COMMENT '글의 상태 (변경 요청? 변경 완료??)',
  `hits` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`idx`),
  INDEX `fk_forum_user1_idx` (`user_idx` ASC) VISIBLE,
  INDEX `fk_forum_forum1_idx` (`parent_idx` ASC) VISIBLE,
  INDEX `fk_forum_forum2_idx` (`history_parent_idx` ASC) VISIBLE,
  CONSTRAINT `fk_forum_user1`
    FOREIGN KEY (`user_idx`)
    REFERENCES `univa_forum`.`user` (`idx`)
    ON DELETE SET DEFAULT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_forum1`
    FOREIGN KEY (`parent_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_forum2`
    FOREIGN KEY (`history_parent_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 포럼 주제 테이블 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`subject` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idx`))
ENGINE = InnoDB

/* 포럼게시글 주제 테이블 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`forum_subject` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `forum_idx` INT UNSIGNED NOT NULL,
  `subject_idx` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idx`),
  INDEX `fk_forum_subject_forum_idx` (`forum_idx` ASC) VISIBLE,
  INDEX `fk_forum_subject_subject1_idx` (`subject_idx` ASC) VISIBLE,
  CONSTRAINT `fk_forum_subject_forum`
    FOREIGN KEY (`forum_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_subject_subject1`
    FOREIGN KEY (`subject_idx`)
    REFERENCES `univa_forum`.`subject` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 포럼게시글 유저 추천 테이블 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`forum_recommend` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `forum_idx` INT UNSIGNED NOT NULL,
  `user_idx` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idx`),
  INDEX `fk_forum_recommend_forum1_idx` (`forum_idx` ASC) VISIBLE,
  INDEX `fk_forum_recommend_user1_idx` (`user_idx` ASC) VISIBLE,
  CONSTRAINT `fk_forum_recommend_forum1`
    FOREIGN KEY (`forum_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_recommend_user1`
    FOREIGN KEY (`user_idx`)
    REFERENCES `univa_forum`.`user` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 게시글 댓글 테이블 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`comment` (
  `idx` INT NOT NULL AUTO_INCREMENT,
  `forum_idx` INT UNSIGNED NOT NULL,
  `state` INT NULL COMMENT '수정 요청 상태 (히스토리?)\n',
  INDEX `fk_comment_forum1_idx` (`forum_idx` ASC) VISIBLE,
  PRIMARY KEY (`idx`),
  CONSTRAINT `fk_comment_forum1`
    FOREIGN KEY (`forum_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 포럼게시글 파일 테이블 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`forum_file` (
  `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_url` VARCHAR(255) NOT NULL,
  `forum_idx` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idx`),
  INDEX `fk_forum_file_forum1_idx` (`forum_idx` ASC) VISIBLE,
  CONSTRAINT `fk_forum_file_forum1`
    FOREIGN KEY (`forum_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 유저 포럼게시글 신고 테이블 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`forum_report` (
  `idx` INT NOT NULL,
  `forum_idx` INT UNSIGNED NOT NULL,
  `user_idx` INT UNSIGNED NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idx`),
  INDEX `fk_forum_report_forum1_idx` (`forum_idx` ASC) VISIBLE,
  INDEX `fk_forum_report_user1_idx` (`user_idx` ASC) VISIBLE,
  CONSTRAINT `fk_forum_report_forum1`
    FOREIGN KEY (`forum_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_report_user1`
    FOREIGN KEY (`user_idx`)
    REFERENCES `univa_forum`.`user` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB

/* 포럼 게시물 delete_request 컬럼 제거 */
ALTER TABLE `univa_forum`.`forum` DROP `delete_request`;

/* 포럼신고 테이블 인덱스 타입 변경 */
ALTER TABLE `forum_report` modify `idx` INT UNSIGNED;

/* 포럼 게시물 수정요청 게시물 추가 */ 
ALTER TABLE `univa_forum`.`forum` ADD `modifying_parent_idx` INT UNSIGNED;

/* 수정요청 제약조건 추가 */
ALTER TABLE `univa_forum`.`forum` 
ADD CONSTRAINT `fk_forum_forum3`
  FOREIGN KEY (`modifying_parent_idx`)
  REFERENCES `univa_forum`.`forum` (`idx`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

/* 수정 이력 저장 */
CREATE TABLE IF NOT EXISTS `univa_forum`.`forum_modifiy` (
  `idx` INT UNSIGNED NOT NULL,
  `forum_idx` INT UNSIGNED NOT NULL,
  `user_idx` INT UNSIGNED NOT NULL,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approval_user` INT UNSIGNED NULL,
  `approval_date` DATETIME NULL,
  PRIMARY KEY (`idx`),
  INDEX `fk_forum_modifiy_forum1_idx` (`forum_idx` ASC) VISIBLE,
  INDEX `fk_forum_modifiy_user1_idx` (`user_idx` ASC) VISIBLE,
  INDEX `fk_forum_modifiy_user2_idx` (`approval_user` ASC) VISIBLE,
  CONSTRAINT `fk_forum_modifiy_forum1`
    FOREIGN KEY (`forum_idx`)
    REFERENCES `univa_forum`.`forum` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_modifiy_user1`
    FOREIGN KEY (`user_idx`)
    REFERENCES `univa_forum`.`user` (`idx`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_modifiy_user2`
    FOREIGN KEY (`approval_user`)
    REFERENCES `univa_forum`.`user` (`idx`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB

/* 수정이력 Auto increment 추가 */
ALTER TABLE `univa_forum`.`forum_modify`
 MODIFY `idx` INT UNSIGNED NOT NULL AUTO_INCREMENT;

/* 파일 용량, 원본 파일 이름 테이블에 추가 */
ALTER TABLE `forum_file`
ADD (
  `file_size` int unsigned null default 0,
  `original_name` VARCHAR(255) null
)

/* 포럼 추천 여부 추가 */
ALTER TABLE `forum_recommend`
ADD (
  `like` int unsigned not null default 1
)

select count(fr.forum_idx)
from forum f, forum_recommend fr
where f.user_idx = 4 and fr.forum_idx = f.idx and fr.like >= 1;
