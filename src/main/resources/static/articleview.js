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


/*
$(document).ready(function (id) {
    // HTML 문서를 로드할 때마다 실행합니다.
    //let id = response;
    getOneArticle();
})
*/

/*
function getOneArticle() {
    $.ajax({
        type: 'GET',
        url: `/api/articles/detail`,
        success: function (response) {
            let article = response;
            //console.log(response);
            let id = article['id'];
            let title = article['title'];
            let username = article['username'];
            let contents = article['contents'];
            let createdAt = article['createdAt'];
            addOneHTML(id, title, username, contents, createdAt);
        }
    })
}
*/

/*
// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addOneHTML(id, title, username, contents, createdAt) {
    // 1. HTML 태그를 만듭니다.
    //let tempHtml = `<div class="card cursor_active" onclick="location.href='articleview.html'">
    let tempHtml = `<div class="card cursor_active" onclick="window.location.href='/api/articles/?id=${id}'">
                            <!-- date/username 영역 -->
                        <div class="metadata">
                            <div id="${id}-title" class="title">
                                ${title}
                            </div>                       
                            <div id="${id}-username" class="username">
                                ${username}
                            </div>
                            <div id="${id}-createdAt" class="date">
                                ${createdAt}
                            </div>
                        </div>
                            <!-- contents 조회/수정 영역-->
                        <div class="contents">
                            <div id="${id}-contents" class="text">
                                ${contents}
                            </div>
                        </div>                      
                            <!-- 버튼 영역-->
                        <div class="footer">
                            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="" onclick="editPost('${id}')">
                            <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="" onclick="deleteOne('${id}')">
                            <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="" onclick="submitEdit('${id}')">
                        </div>
                    </div>`;
    $('#show-article').append(tempHtml);
}

 */
    /*
    let tempHtml = `<div class="card">
                        <div class="title">
                            <h2 id="${id}-title" class="title">
                                ${title}
                            </h2>
                        </div>
                                        <!-- date/username 영역 -->
                        <div class="metadata">
                        <div id="${id}-createdAt" class="date">
                            ${createdAt}
                        </div>
                        <div id="${id}-username" class="username">
                            ${username}
                        </div>
                        </div>
                            <!-- contents 조회/수정 영역-->
                        <div class="contents">
                        <div id="${id}-contents" class="text">
                            ${contents}
                        </div>
                        <div id="${id}-editarea" class="edit">
                            <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
                        </div>
                        </div>
                            <!-- 버튼 영역-->
                        <div class="footer">
                            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="" onclick="editPost('${id}')">
                            <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="" onclick="deleteOne('${id}')">
                            <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="" onclick="submitEdit('${id}')">
                        </div>
                    </div>`;
        // 2. #cards-box 에 HTML을 붙인다.
        $('#show-article').append(tempHtml);
    }

     */