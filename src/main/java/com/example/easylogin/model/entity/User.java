package com.example.easylogin.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JavaとデータベースをつなげるためにJPAを使用する
 * JPAではエンティティ（実体）というテーブルとマッピングするクラスが必要になる
 * Java EEサーバー上では@Entityとアノテートするだけで自動的にエンティティとして登録することができる
 * クラス名がDBのテーブル名と一致する場合は自動的に紐付けされるが、異なる場合は@Tableアノテーションでマッピングを定義する必要がある
 * */
@Entity
@Table(name = "user")
public class User {
	// 以下の4つのフィールドはuserテーブルのカラム名
	
	// JPAはJavaの世界とDBの世界をつないでいるが、DBの世界においては主キーでテーブル同士を紐付けるシステムなので、Java側でも主キーを設定する必要がある
	// JPAのルールとして、主キーの項目には@Idアノテーションをつけることになっている
	@Id 
	// @Columnアノテーションでカラム名を指定する
	// @Table同様にフィールド名とDBでのカラム名が一致しない場合、@Columnをアノテートし、nameにカラム名を指定することでフィールドとカラムを紐付けることができる
	@Column(name="id")
	// GeneratedValueアノテーションは@Idで主キーに指定した属性の採番方法を指定する
	// strategy=GenerationType.IDENTITYの場合は、自動的に採番されるAUTO_INCREMENTなIDとして設定される
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="user_name")
	private String userName;

	@Column(name="password")
	private String password;

	@Column(name="full_name")
	private String fullName;

	// フィールドのゲッターとセッター
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
