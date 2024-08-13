$(function() {
	
  // 入力された値を定義
  let name = $("input[name='name']");
  let address = $("input[name='address']");
  let title = $("input[name='title']");
  let content = $("input[name='content']");
   let btn = $("button[type='submit']");

  // 初期状態のボタン活性化
    checkFormValidity();

    // フォームの入力変更を監視
    name.on("input", checkFormValidity);
    address.on("input", checkFormValidity);
    title.on("input", checkFormValidity);
    content.on("input", checkFormValidity);


    function checkFormValidity() {
        if (name.val() != "" && address.val() != "" && title.val() != "" && content.val() != "") {
            // 有効化したらボタンの色を変える
            btn.css("backgroundColor", "#28a745");
            // 有効化時はポインターカーソル
			btn.css("cursor", "pointer");
            // 両方のフィールドが入力されていればボタンを有効化
            btn.prop("disabled", false);
        } else {
            // 無効化したらボタンの色を戻す
            btn.css("backgroundColor", "#808080");
			// 無効化時はカーソルを変更
            btn.css("cursor", "not-allowed");
            // フィールドが空ならボタンを無効化
            btn.prop("disabled", true);
            
        }
    }

});