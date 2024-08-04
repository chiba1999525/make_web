$(function () {

    // h1要素がクリックされたら
    $("h1").click(function() {
        alert("h1要素がクリックされました！");
    });

    // リッチエディター
	$('#editor').trumbowyg({
	    lang: 'ja',
		autogrow: true,
		btns:[
		        ['fullscreen']
		    ]
	});
	
});
