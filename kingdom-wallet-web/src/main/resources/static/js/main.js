/* main.js */
var $devWidth; //디바이스 가로크기 체크
var $limitSize = 768; //태블릿 미만 사이즈 체크

$(function(){
	$devWidth = $("body").width();

	$(window).resize(function(){
		$devWidth = $("body").width();
		console.log($devWidth);
	});

	/* 중간메뉴 */

	$("#menu_list1").click(function(e){
		$("#menu_list1 ul").slideDown("fast");
	});
	$("#menu_list1").mouseleave(function(e){
		$("#menu_list1 ul").slideUp("fast");
	});
	$("#menu_list2").click(function(e){
		$("#menu_list2 ul").slideDown("fast");
	});
	$("#menu_list2").mouseleave(function(e){
		$("#menu_list2 ul").slideUp("fast");
	});









	/* 탭메뉴 */
	/*
	$("#tapmenu .btn1, #tapmenu .btn2").click(function(e){
		e.preventDefault();

		$("#item1,#item2").hide();
		$(this).parent().next().show();

	});
*/
	/* 배너 */
	/*

	var $bnnNum=0; //배너1:$bnnNum=0, 배너2:$bnnNum=1

	$("#arrow > #next").click(function(e){
		e.preventDefault();

		if($bnnNum>=4) return false;
		// 현재 $bnnNum=0
		$bnnNum++;
		//$bnnNum=1
		$book_w=$(".main_banner").width();
		$(".banner_ul").animate({left:-$book_w*$bnnNum},500);
	});
	
	$("#arrow > #prev").click(function(e){
		e.preventDefault();

		if($bnnNum<=0) return false;
		//현재 $bnnNum=1
		$bnnNum--;
		$book_w=$(".main_banner").width();
		$(".banner_ul").animate({left:-$book_w*$bnnNum},500);
	});	

		//자동 롤링
		count = $('.banner_ul li').length;
		index = 0;
		$('.arrow li').eq(index).addClass('active');
		autoplay();


		function autoplay()  {
			play = setInterval(function(){
				index = ((index+1) == count) ? 0 : index+1;
				$('.arrow li').removeClass('active');
				$('.banner_ul').animate({'left':-(index*1800)}, 400);
				$('.arrow li').eq(index).addClass('active');
			},4000);
		}
*/





	//모바일





});