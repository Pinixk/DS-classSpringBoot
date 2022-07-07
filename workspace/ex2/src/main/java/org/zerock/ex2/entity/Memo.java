package org.zerock.ex2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;

@Entity // spl table
@Table(name = "tbl_memo") // table 이름 지정
@ToString // hash code를 string타입으로 변환
public class Memo {
  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
  private Long mno;

  @Column(length = 200, nullable = false)
  private String memoText;
}
