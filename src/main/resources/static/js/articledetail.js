/*
특정 Article에 대한 조회, 수정, 삭제 기능 function
해당 Article에 대한 Comment 생성, 조회, 수정, 삭제 기능 function
페이징...????? 일단 제외
 */
$(document).ready(function () {
    let id = location.search.split('=')[1]
    getDetail(id)
    getComment(id)
    //backHome()
    showArticleDetail()
    //showHide()
    //deleteArticle()
    $('.comment__card-box').empty();
})

//특정 Article에 대한 조회
function showArticleDetail() {
    let id = location.search.split('=')[1]
    //let index = id

    $.ajax({
        type: 'GET',
        url: `/api/articles/detail/${id}`,
        success: function (response) {
            let createdAt = response.createdAt.split('T');
            createdAt[1] = createdAt[1].split('.')[0];
            addDetailArticle(response['id'], response['username'], response['title'], response['contents'], response['createdAt'])
        }
    })
}

function addDetailArticle(id, username, title, contents, createdAt) {
    let tempHtml = `<div class="card cardblock" onclick="window.location.href='/'">
                        <div class="metadata">
                            <div class="title">
                                ${title}
                            </div>
                            <div id="username" class="username" >
                                ${username}
                            </div>
                            <div class="date" >
                                ${createdAt}
                            </div>
                        </div>
                        <!-- contents 조회/수정 영역-->
                        <div class="contents">
                            <div id="contents" class="text" >
                                ${contents}
                            </div>
                        </div>
                        <div class="footer" style="text-align: right">
                            <button type="button" class="btn btn-success" onclick="onClickMainPage()">목록으로 이동</button>
                            <button type="button" class="btn btn-info" onclick="onClickMoveEditpage(${id})">수정하기</button>
                            <button type="button" class="btn btn-danger" onclick="onClickArticleDelete(${id})">삭제하기</button>
                        </div>
                        <div class="footer-btns">
                            <div class="col-md-12 bg-light text-right">
                                <button type="button" class="btn btn-primary" id="gobackBtn" onclick="window.location.href='/'">목록으로</button>
                                <button type="button" class="btn btn-primary" id="article_edit_Btn" onclick="editArticle(${id})">수정</button>
                                <button type="button" class="btn btn-primary" id="article_delete_Btn" onclick="deleteArticle(${id})">삭제</button>
                            </div>
                        </div>
                    </div>`;
    $('#show-article-detail').append(tempHtml);
}

//특정 게시글에 대한 수정
//#edit이라는 id값을 가진 요소를 눌렀을 경우
//#cancel이라는 id값을 가진 요소를 눌렀을 경우
function editBtn() {
    let username = $('.username').text()
    let cur_username = $('.username01').text()
    if (cur_username != username){
        alert("자신이 작성한 글만 수정이 가능합니다!")
        return;
    }
    $('.detail').hide()
    $('.detail_edit').show()
    let title = $('.title').text()
    let contents = $('.contents').text().trim()
    let author = $('.post-author').text().trim()
    $('.post-author-edit').text(author)
    $('.detail-input').val(title)
    $('.detail-textarea').val(contents)
}

function cancelBtn() {
    $('.detail').show()
    $('.detail_edit').hide()
    $('.detail-input').val('')
    $('.detail-textarea').val('')
}

//실질적인 수정에 대한 함수
//쓰여있던 내용 중 수정이 가능한 영역 구분은 .val() - 제목, 글내용
//수정이 불가한 내용에 대해서는 애초에 .text()
function editArticle() {
    let id = location.search.split('=')[1]
    let title = $('#title').val();
    let contents = $('#contents').val();
    let username = $('#username').text()

    if (title == '') {
        alert("수정하실 제목을 입력하시지 않으셨습니다.")
        return
    }
    if (contents == '') {
        alert("수정하실 내용을 입력하시지 않으셨습니다.")
        return;
    }
    let data = {'username': username, 'title': title, 'contents': contents}

    $.ajax({
        type: "PUT",
        url: `/api/articles/${id}`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            alert("수정이 완료되었습니다!")
            window.location.reload();
        }
    })
}

//특정 게시글에 대한 삭제기능
//#delete라는 id가 속한 요소를 눌렀을때에 작동하도록 onclick
function deleteArticle() {
    let id = location.search.split('=')[1]
    let username = $('#username').text()
    let cur_username = $('.username01').text()

    if (cur_username != username){
        alert("자신이 작성한 글만 삭제가 가능합니다!")
        return;
    }
    //let id = location.search.split('=')[1]
    $.ajax({
        type: "DELETE",
        url:`/api/articles/${id}`,
        success:function (response){
            alert("삭제 되었습니다.")
            window.location.href = "/"
        }
    })
}

//////////////////////////////////////////////
//댓글에 관한 함수

//댓글작성
function create_comment() {
    let article_id = location.search.split('=')[1]
    let name = $('.username01').text();
    let contents = $('#comment_textarea').val();
    if (!name || $('.link-signup').text() == '로그인 하러 가기') {
        alert("로그인을 하셔야 댓글을 달수 있습니다!")
        return;
    }
    if (contents == '') {
        alert("댓글을 적어주세요!!")
        return
    }
    let data = {'article_id':article_id, 'username':name, 'contents':contents};

    $.ajax({
        type:'POST',
        url:'/api/comments',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response){
            console.log(data)
            alert('댓글이 성공적으로 작성되었습니다!')
            window.location.reload();
        }

    })
}

//댓글읽기
function getComment(id) {
    let index = id
    $.ajax({
        type: 'GET',
        url: `/api/comment/${index}`,
        success: function (response) {
            for(let i=0; i< response.length; i++){
                console.log(response[i])
                let comment = response[i];
                let tempHtml = addComments(comment)
                $('.comment__card-box').append(tempHtml);
            }
        }
    });
}

//댓글추가하기
function addComments(comment){
    return `<div class="comment__card">
            <div class="comment__card-header">
              <span id="${comment.id}-comment_user">${comment.username}</span><span class="comment-date">${comment.modifiedAt}</span>
            </div>
            <div class="comment__card-body">
              <div class="edit-comment-wrap">
                <div id="${comment.id}-comment" class="comment">${comment.contents}</div>
                <textarea
                  class="edit-comment-textarea"
                  id="${comment.id}-edit-comment-textarea"
                  name=""
                  id=""
                  cols="30"
                  rows="10"
                ></textarea>
              </div>
              <div class="comment-btn">
                <div class="befor" id="${comment.id}-befor">
                  <i class="far fa-edit" id="${comment.id}-edit-btn" onclick="edit_start_comment(${comment.id})" ></i>
                  <i class="fas fa-trash-alt" id="${comment.id}-delete-btn" onclick="delete_comment(${comment.id})"></i>
                </div>
                <div class="after" id="${comment.id}-after">
                  <i class="fas fa-check"  id="${comment.id}-check-btn" onclick="edit_comment(${comment.id})" ></i>
                  <i class="fas fa-times" id="${comment.id}-cancel-btn" onclick="edit_end_comment(${comment.id})" ></i>
                </div>
              </div>
            </div>
          </div>`;

}

//댓글수정시도
function edit_start_comment(id){
    let comment_user = $(`#${id}-comment_user`).text()
    let cur_username = $('.username01').text()

    if (comment_user != cur_username){
        alert("본인의 댓글만 수정 하실수 있습니다.")
        return;
    }
    $(`#${id}-befor`).hide()
    $(`#${id}-after`).show()
    $(`#${id}-edit-comment-textarea`).show()
    $(`#${id}-comment`).hide()
    $(`#${id}-edit-comment-textarea`).val($(`#${id}-comment`).text())

}

//댓글수정시행
function edit_comment(id){
    let article_id = location.search.split('=')[1]
    let username = $('.username01').text()
    let contents = $(`#${id}-edit-comment-textarea`).val()
    if(contents==''){
        alert('수정하실 내용을 작성 해주세요!')
        return;
    }
    let data = {'article_id':article_id,'username':username,'contents':contents}

    $.ajax({
        type:'PUT',
        url:`/api/comments/${id}`,
        contentType:'application/json',
        data:JSON.stringify(data),
        success:function (response){
            alert('댓글이 수정 되었습니다.')
            window.location.reload();
        }
    })
}

//댓글수정종료
function edit_end_comment(id){
    $(`#${id}-befor`).show()
    $(`#${id}-after`).hide()

    $(`#${id}-edit-comment-textarea`).hide()
    $(`#${id}-comment`).show()
    $(`#${id}-edit-comment-textarea`).val('')
}

//댓글삭제
function delete_comment(id){
    let comment_user = $(`#${id}-comment_user`).text()
    let cur_username = $('.username01').text()

    if (comment_user != cur_username){
        alert("본인의 댓글만 삭제하실수 있습니다.")
        return;
    }
    $.ajax({
        type:'DELETE',
        url:`api/comments/${id}`,
        success: function (response){
            alert('댓글이 삭제 되었습니다.')
            window.location.reload();
        }
    })
}