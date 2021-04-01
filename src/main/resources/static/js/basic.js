// 미리 작성된 영역 - 수정하지 않으셔도 됩니다.
// 사용자가 내용을 올바르게 입력하였는지 확인합니다.

function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    //trim() : 앞뒤의 공백을 잘라(중간공백은 포함)
    if (contents.trim().length > 250) {
        alert('공백 포함 250자 이하로 입력해주세요');
        return false;
    }
    return true;
}


// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
}

function hideEdits(id) {
    $(`#${id}-editarea`).hide();
    $(`#${id}-submit`).hide();
    $(`#${id}-delete`).hide();

    $(`#${id}-contents`).show();
    $(`#${id}-edit`).show();
}

//게시글 상세페이지에서 목록페이지로 이동
function onClickMainPage() {
    window.location.href = "/index.html";
}

//게시글 상세페이지에서 수정페이지로 이동
function onClickMoveEditpage(id) {
    window.location.href = "/edit.html?" + id;
}

//게시글 상세페이지에서 게시글 삭제
function onClickArticleDelete(id) {
    $.ajax({
        type: "DELETE",
        url: "/board/" + id,
        success: function (response) {
            alert("삭제 완료!");
            window.location.href = "/index.html";
        }
    });
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 여기서부터 코드를 작성해주시면 됩니다.

$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    alert("안녕하세요!")
    getMessages();
    $("#myBtn").click(function(){
        $("#myModal").modal();
    });
})

// 메모를 불러와서 보여줍니다.
// 게시물을 목록으로 불러와주는데서 상세내용은 필요하지 않다.
// 게시물 제목을 누르거나 해당 칸을 눌렀을때 각각 글에 대한 상세 게시물 페이지로 넘어갈 수 있는 링크 심어주
function getMessages() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#article-infos').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/articles',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let index = i+1;
                let id = message['id'];
                let title = message['title']
                let username = message['username'];
                let contents = message['contents'];
                let createdAt = message['createdAt'];
                addHTML(index, id, title, username, contents, createdAt);

            }
        }
    })
}

function addHTML(index, id, title, username, contents, createdAt) {

    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<tr class="cursor_active" onclick="window.location.href='/api/articles/detail/${id}'">
                        <td class="index">${index}</td>
                        <td id="${id}-title" class="title">${title}</td>
                        <td id="${id}-username" class="username">${username}</td>
                        <td id="${id}-createdAt" class="date">${createdAt}</td>
                    </tr>`
    $('#article-infos').append(tempHtml);
}

///////////////////detail.html JS
// 메모를 불러와서 보여줍니다.
// 수정에 관한 부분을 할꺼면 여기서 가능하도록 처리!
function getDetail(id) {
    $.ajax({
        type: "GET",
        url: "/board/articles/detail/" + id,
        success: function (response) {
            let article = response;
            let id = article.id;
            let title = article.title;
            let username = article.username;
            let contents = article.contents;
            let createdAt = article.createdAt.split('T');
            createdAt[1] = createdAt[1].split('.')[0];
            addDetailHTML(id, title, username, contents, createdAt);
        }
    })
}

// 저장된 글을 볼 수 있도록 HTML로 그려줌
function addDetailHTML(id, title, username, contents, createdAt) {
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
                        
                    </div>`;
    $('#show-article-datail').append(tempHtml);
}



// 게시글 생성합니다.
function writeArticle() {
    let title = $('#title').val();
    // 1. 작성한 메모를 불러옵니다.
    let contents = $('#contents').val();
    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
    if (isValidContents(contents) == false) {
        return;
    }
    // 3. username은 로그인되어있는 사용자의 이름을 씁니다.
    let username = $('#username').text();
    //let username = genRandomName(10);
    // 4. 전달할 data JSON으로 만듭니다.
    if (username == '') {
        alert('이름을 적어주세요.');
        return;
    } else if (title == '') {
        alert('제목을 적어주세요.');
        return;
    } else if (contents == '') {
        alert('내용을 적어주세요.');
        return;
    }
    let data = {'title':title, 'username': username, 'contents': contents};
    // 5. POST /api/memos 에 data를 전달합니다.
    //이전까지 ARC 통해 해오던 것을 CLI로 하는 방법
    $.ajax({
        type: "POST",
        url: "/api/articles",
        contentType: "application/json", // JSON 형식으로 전달함을 알리기
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지가 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

// 메모를 수정합니다.
function submitEdit(id) {
    // 1. 작성 대상 메모의 username과 contents 를 확인합니다.
    let username = $(`#${id}-username`).text().trim();
    let contents = $(`#${id}-contents`).val().trim();

    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
    if (isValidContents(contents) == false) {
        return;
    }

    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'title': title, 'username': username, 'contents': contents};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/api/articles/detail/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지 변경에 성공하였습니다.');
            window.location.reload();
        }
    });
}


// 1. DELETE /api/memos/{id} 에 요청해서 메모를 삭제합니다.
function deleteOne(id) {
    $.ajax({
        type: "DELETE",
        url: `/api/articles/detail/${id}`,
        success: function (response) {
            alert('메시지 삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}


