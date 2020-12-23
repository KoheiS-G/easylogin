package com.example.easylogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.easylogin.model.dao.UserRepository;
import com.example.easylogin.model.entity.User;

// クラスをControllerに設定し、クライアントからのリクエストを処理しレスポンスを返せるようにする
// MVCモデルにおけるCにあたり、画面遷移機能に直結し、ViewとModelの架け橋になる
@Controller
public class LoginController {

	// Autowiredアノテーションを付与されたフィールドは、new演算子を使うことなくインスタンス化される
	// DependencyInjection（依存性の注入）のために必要（あるオブジェクトとあるオブジェクトの依存関係をオブジェクト内のコードを記述せずに実行時に外部から引数などで呼び出す手法）
	//　この場合だと、メソッドごとに毎回UserRepositoryインターフェースをnewによってインスタンス化する手間を省くためだと考えられる
	@Autowired
	UserRepository userRepos;

	// 「http://localhost:8080/」にアクセスが合ったときにindexメソッドを実行する
	@RequestMapping("/")
	// トップページへの遷移を担うindexメソッド
	public String index() {
		return "index";
	}

	// 「http://localhost:8080/login」にアクセスが合ったときにloginメソッドを実行する
	@RequestMapping("/login")
	// Stringのデータを戻り値として返すloginメソッド
	// 最初の2つの引数にはアノテーション@RequestParamが付与されている
	// このアノテーションが付与された引数は、クライアントからのリクエストであることを意味し、HTML側で定義されたname属性を指定することで判断する
	// 最後のModelは、レスポンスとしてクライアント側に返すためのオブジェクト
	// 今回は、addAttributeメソッドを使い"message"というキー文字列に対して、ログイン結果によって分岐するメッセージを値に設定している
	public String login(@RequestParam("userName") String userName, @RequestParam("password") String password, Model m) {

		String message = "Welcome! ";

		// UserRepositoryに追加したメソッドを呼び出し、Userの一覧を取得する
		// このメソッドはList型でUserクラスの値を戻り値として設定しているので、代入する変数（users）もList型かつUserクラスを指定する
		List<User> users = userRepos.findByUserNameAndPassword(userName, password);
		// クライアントが入力したユーザー名とパスワードに一致するデータがDBに存在すれば、フルネームを表示
		// なければゲストと表示する
		if (users.size() > 0) {
			User user = users.get(0);
			message += user.getFullName();
		} else {
			message += "guest";
		}
		
		// Thymeleaf側でmessageという名前で設定した変数に、上でローカル変数として定義したmessageを代入
		// ModelインターフェースのaddAttributeメソッドを使ってコンテキストにmessageという値を保存する
		m.addAttribute("message", message);

		// 戻り値のStringに対応するテンプレート（ここではlogin.html）に遷移
		return "login";
	}

	@RequestMapping("/showUser")
	@ResponseBody
	public String showUsers() {

		// UserRepositoryのインスタンスからfindAllメソッドを呼び出し、Userエンティティのリストを取得
		// ちなみにfindAllメソッドはJpaRepositoryの標準メソッドである
		// UserRepositoryインターフェースはJpaRepositoryインターフェースを継承しているため特に記述しなくても親クラスのメソッドが使えるというわけである
		// このファイルを作成した時点ではDBに一件しかレコードが存在していないが、Listで取得する。なぜならfindAllメソッドの戻り値のデータ型がListのため
		List<User> users = userRepos.findAll();

		// 上記で取得したリストの0番目を取得
		User user = users.get(0);

		// Userエンティティのインスタンスから、UserNameとpasswordを連結した文字列を作成する
		String info = user.getUserName() + " " + user.getPassword();

		return info;
	}
}
