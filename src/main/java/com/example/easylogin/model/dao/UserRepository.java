package com.example.easylogin.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.easylogin.model.entity.User;

// Spring BootでのRepositoryとはDBへの基本的なアクセスを自動で実装してくれる便利なインターフェースである
// JpaRepositoryとはSpring Frameworkで使われるデータ検索を行うための仕組みである。インターフェースに予め検索メソッドを定義しておくことで、メソッドを呼び出すだけでデータ検索が行えるようになる
// クラスの宣言の前に@Repositoryとアノテーションをつけることで、このインターフェースがJpaRepositoryであることを示すことができる
@Repository
// JpaRepositoryインターフェースを使うためには、エンティティクラスの名前とIDの型を指定する必要がある
// JPAにはDBのテーブル（ここではuserテーブル）を参照するためのエンティティが必要なので、エンティティクラスに設定したUser.javaを指定する
// JPAはJavaの世界とDBの世界をつないでいるが、DBの世界においては主キーでテーブル同士を紐付けるシステムなので、Java側でも主キーを設定する必要がある
// その主キーに設定しているIDの型をここでは入力する。idはLong型なのでLongと記述する
public interface UserRepository extends JpaRepository<User, Long> {
	// ユーザー名とパスワードでuserテーブルを参照するためのメソッドを定義する
	
	// 戻り値の型をList<User>に設定
	// Listはコレクションフレームワークの一つで、簡単に言うと「要素の上限のない配列」である
	// <>内に型名を記入することでその型の値だけを配列に挿入するようになる（実は<?>と記述するとどの型の値でも挿入することができる）
	// この場合はUser型（User.javaで扱っている値）のみを挿入できる
	
	// このメソッドの意味はDBにあるuserテーブル（エンティティであるUserクラスを使って間接的に接続）からユーザー名とパスワードをキーワードとしてデータを抽出するということ
	List<User> findByUserNameAndPassword(String userName, String password);
}
