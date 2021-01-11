// 수정요청중에 선택했을때,
function modifyApply(idx){
  var formData = new FormData();
  formData.append("id",idx);

  postAPI("/forum/main/modifyApply",formData, function(result){
     switch(result){
       case "ok":
         alert("변경되었습니다.");
         location.href="/forum/mypage?type=update_request"
         break;
       case "error" :
        alert("오류가 발생하였습니다.")
        break;
     }
   });
}

// 삭제요청
function requestRemove(idx){
  if(!idx) return;
  var formData = new FormData();

  formData.append("id",idx);

  postAPI("/forum/main/remove", formData , function(result){
     switch(result){
       case "ok":
         alert("삭제요청이 되었습니다.");
         window.location.reload();
         break;
       case "error" :
        alert("오류가 발생하였습니다.")
        break;
     }
   });
}

// 하단 Content 변경
function switchContent(target, url, callback){
  $("#"+target).load(url);
  callback(url);
}

// 검색결과를 가지고 restful api
function search(){
  var inputText = $("#input-search").val();
  var subjects = [];

  if(inputText=="") {
    alert("키워드를 입력해주세요.");
    return;
  }

  switchContent("result-row",
   "/forum/main/board?title="+inputText+"&min=0&max=10&sort=date",
   function (url){
     loadContent.push(url);
   });
}

// 댓글달기 전송
function submitReply(id){
  var formData = new FormData();
  var files = document.getElementById('reply-uploads-' + id).files;

  for(let i=0; i < files.length; i++) {
     formData.append('files', files[i]);
  }

  formData.append("title", $("input[name=reply-title-" + id + "]").val());
  formData.append("content", $("textarea[name=reply-content-" + id + "]").val());
  formData.append("parent_idx", id);

  postAPI("/forum/main/reply",formData, function(result){
     switch(result){
       case "ok":
         alert("댓글이 등록되었습니다.");
         location.href="/forum/main?type=content&id="+id;
         break;
     }
   });
}

// 사진 클릭시 새창이동
function newWindowImage(url){
  window.open(url, '_black');
}

// 모든 파일 다운로드
function allDownload(id){
  var divs = $("#file-list-"+id).children();

  for (var i = 0; i < divs.length; i++) {
    var link = document.createElement('a');

    link.setAttribute('href', divs[i].dataset.url);
    link.setAttribute('download', divs[i].dataset.name);
    link.style.display = 'none';
    document.body.appendChild(link);

    link.click();
    document.body.removeChild(link);
  }
}

function onclickRecommend(target, id, userIdx){
  if(!userIdx){
    alert("로그인이 필요합니다.");
    return;
  };

  var formData = new FormData();

  if(target.dataset.enable == "true"){
    target.className = "ion ion-md-heart-empty text-primary";
    target.dataset.enable = "false";
  }
  else{
    target.className = "ion ion-md-heart text-primary"
    target.dataset.enable = "true";
  }

  formData.append("idx", id);
  formData.append("like", target.dataset.enable);

  postAPI("/forum/main/like",formData, function(result){
    if(result)
      $("#like-count").text(result);
  });
}
