$(document).ready(function() {
	
	
    $('.menu-icon').click(function() {
        $('.nav-links').toggleClass('active');
    });

	// ドロップダウンメニュー
	$('.dropdown-btn').hover(
	  function() {
	    //カーソルが重なった時
	    $(this).children('.dropdown').addClass('open');
	  }, function() {
	    //カーソルが離れた時
	    $(this).children('.dropdown').removeClass('open');
	  }
	);

});
