$('#addPost').on('click', function () {
    request = '/Post/toggleAdd';
    $.ajax({
        url: request,
        type: 'POST',
        contentType: 'text/plain',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (frag) {
            console.log(frag);
            $('#postView').replaceWith(frag);
        },
        error: function(jqXHR, status, error) {
            alert('error')
            console.log(jqXHR)
        },
    })
})

// 이미지 추가
$(document).on('change', '#inputGroupFile', function () {
    const cloneInput = $(this).clone();
    cloneInput.attr('id', '');
    cloneInput.attr('name', 'imgFiles');
    cloneInput.addClass('d-none');
    
    const file = $(this)[0].files[0];
    const fileType = file.name.substring(file.name.lastIndexOf('.') + 1);
    if(fileType == 'jpg' || fileType == 'png' || fileType == 'jpeg' || fileType == 'bmp' || fileType == 'gif') {
    }
    else {
        alert('이미지 파일 (jpg, png, jpeg, gif, bmp) 만 업로드 할 수 있습니다.');
        return false;
    }
    const imgCount = $('.img-prev');
    if(imgCount.length > 4) {
        alert('이미지는 최대 5장까지 업로드 가능합니다')
        $('#inputGroupFile').val(null);
        return false;
    }
    
    if($(this)[0].files && file){
        const fr = new FileReader();
        const appendPreview = '<div class="img-prev">'
            + '<button type="button" class="btn">'
                + '<svg width="25" height="25" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">'
                    + '<path d="M10 1.875C8.39303 1.875 6.82214 2.35152 5.486 3.24431C4.14985 4.1371 3.10844 5.40605 2.49348 6.8907C1.87852 8.37535 1.71762 10.009 2.03112 11.5851C2.34463 13.1612 3.11846 14.6089 4.25476 15.7452C5.39106 16.8815 6.8388 17.6554 8.41489 17.9689C9.99099 18.2824 11.6247 18.1215 13.1093 17.5065C14.594 16.8916 15.8629 15.8502 16.7557 14.514C17.6485 13.1779 18.125 11.607 18.125 10C18.1209 7.84638 17.2635 5.78216 15.7407 4.25932C14.2178 2.73648 12.1536 1.87913 10 1.875ZM12.9453 12.0547C13.0627 12.1732 13.1285 12.3332 13.1285 12.5C13.1285 12.6668 13.0627 12.8268 12.9453 12.9453C12.8259 13.0608 12.6662 13.1254 12.5 13.1254C12.3338 13.1254 12.1742 13.0608 12.0547 12.9453L10 10.8828L7.94532 12.9453C7.82585 13.0608 7.66618 13.1254 7.5 13.1254C7.33383 13.1254 7.17415 13.0608 7.05469 12.9453C6.93733 12.8268 6.87149 12.6668 6.87149 12.5C6.87149 12.3332 6.93733 12.1732 7.05469 12.0547L9.11719 10L7.05469 7.94531C6.95503 7.82388 6.90409 7.66971 6.9118 7.51281C6.91951 7.3559 6.9853 7.20747 7.09639 7.09638C7.20747 6.9853 7.3559 6.9195 7.51281 6.9118C7.66972 6.90409 7.82388 6.95502 7.94532 7.05469L10 9.11719L12.0547 7.05469C12.1761 6.95502 12.3303 6.90409 12.4872 6.9118C12.6441 6.9195 12.7925 6.9853 12.9036 7.09638C13.0147 7.20747 13.0805 7.3559 13.0882 7.51281C13.0959 7.66971 13.045 7.82388 12.9453 7.94531L10.8828 10L12.9453 12.0547Z" fill="black"/>'
                + '</svg>'
                + '</button>'
                + '</div>';
                $('#preview').append(appendPreview);
                fr.readAsDataURL(file);
                fr.onload = function (e) {
                    $('#preview div:last-child').css('background-image', 'url(' + e.target.result + ')');
        }
        $('#preview div:last-child').append(cloneInput);
    }
})

//추가된 이미지 삭제
$(document).on('click', '.img-prev button', function () {
    $(this).parent('.img-prev').remove();
    $('#inputGroupFile').val(null);
})

// 게시글 업로드
$(document).on('submit', '.post-add form.form', function (e) {
    e.preventDefault();
    const formTextData = {
        postId: $(this).find('#postId').val(),
        postTitle : $(this).find('#postName').val(),
        postDetail : $(this).find('#postContent').val(),
    }
    
    if (formTextData.postTitle == '' || formTextData.postDetail == '') {
        alert('게시글 제목과 내용을 입력해 주세요')
        return false;
    }
    const formData = new FormData();
    formData.append('postTexts', new Blob([JSON.stringify(formTextData)] , {type: "application/json"}))
    $(this).find('input[name="imgFiles"]').each((index, item) => {
        formData.append('postImages', item.files[0]);
    }) 
    
    if (formData.postId == undefined) request = '/Post/add';
    else request = '/Post/edit';
    // console.log(formData.postId)
    
    $.ajax({
        url: request,
        type: 'POST',
        contentType: false,        
        processData: false,
        data: formData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (result, status) {
            console.log(result);
            location.reload();
        },
        error: function (jqXHR, status, error) {
            console.log(jqXHR);
        }
    });
})

// 페이지네이션
$(document).on('click', '.page-item', function () {
        const targetNum = $(this).attr('data-pagenum');
        const pageData = {
            targetPage: targetNum,
            userId: location.pathname.substring(1, location.pathname.lastIndexOf('/')),
        }
        request = '/Post/loadMore';
        $.ajax({
            url: request,
            type: 'POST',
            dataType: 'text',
            data: pageData,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            cache: false,
            success: function (result, status) {
                console.log(result);
                $('#postView').replaceWith(result);
            },
            error: function (jqXHR, status, error) {
                console.log(jqXHR);
            }
        })
})
    
// 댓글달기 버튼
$(document).on('click', '.btn.addComments', function () {
    const postLi = $(this).parents('.postEach');
    const commentUl = postLi.children('.commentList');
    const commentUlHeight = parseInt(commentUl.css('height'));
    const closestParentUl = $(this).closest('ul');
    let currPostComm;
    let dropItemsHeight = 150;

    if (closestParentUl.hasClass('post')) {
        currPostComm = $(this).closest('li').find('.comment.postComment');
    } else {
        currPostComm = $(this).siblings('.comment.commComment');
    }

    if (currPostComm.css('height') == '0px') {
        currPostComm.css('height', dropItemsHeight);
        if(!currPostComm.hasClass('postComment')) commentUl.css('height', commentUlHeight + dropItemsHeight);
    } else {
        currPostComm.css('height', 0);
        if(!currPostComm.hasClass('postComment')) commentUl.css('height', commentUlHeight - dropItemsHeight);
    }
})

// 댓글 작성
$(document).on('submit', '.comment .form', function (e) {
    e.preventDefault();
    const closestParentUl = $(this).closest('ul');
    let postId = $(this).parents('li').find('input[name="postId"]').val();
    let currComm = $(this).closest('li').find('.comment.postComment');
    let parentCommId = -1;
    let commId = null;
    if (!closestParentUl.hasClass('post')) {
        currComm = $(this).closest('li').find('.comment.commComment');
        parentCommId = $(this).closest('li').find('input[name="commId"]').val();
    } 
    let commentDetail = currComm.find('textarea[name="commentDetail"]').val();
    if (commentDetail == '') {
        alert('댓글 내용을 입력해 주세요');
        return false;
    }

    if (commId == null) request = '/Comment/add'
    else request = '/Comment/edit'

    data = {
        commId: commId,
        postId: postId,
        parentCommId: parentCommId,
        commDetail: commentDetail,
    }
    const newCommentData = JSON.stringify(data);
    $.ajax({
        url: request,
        type: 'POST',
        contentType: 'application/json',
        data: newCommentData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (result, status) {
            const targetNum = $('.page-item.active').attr('data-pagenum');
            const pageData = {
                targetPage: targetNum,
                userId: location.pathname.substring(1, location.pathname.lastIndexOf('/')),
            }
            request = '/Post/loadMore';
            $.ajax({
                url: request,
                type: 'POST',
                dataType: 'text',
                data: pageData,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                cache: false,
                success: function (result, status) {
                    console.log(result);
                    $('#postView').replaceWith(result);
                },
                error: function (jqXHR, status, error) {
                    console.log(jqXHR);
                }
            })
        },
        error: function (jqXHR, status, error) {
            if(jqXHR.responseText == "Need Login") alert('로그인이 필요한 서비스 입니다.')
            console.log(jqXHR);
        }
    })
})

// 댓글보기
$(document).on('click', '.btn.showComment', function () {
    const parent = $(this).closest('li');
    const dropmenu = parent.children('.commentList');
    const arrowDown = $(this).children('.icon-arrowDrop');

    // 화살표 회전 
	if (arrowDown.css('transform') == 'matrix(0, 1, -1, 0, 0, 0)') {
		arrowDown.css('transform', 'rotateZ(0deg)');
	} else {
		arrowDown.css('transform','rotateZ(90deg)');
    }

	// transition 을 위해 드롭다운 메뉴 내용물 총 크기에 따라서 부모 높이 지정.
    let dropItemsHeight = 0;
    dropmenu.children().each(function () {
        dropItemsHeight += $(this).outerHeight(true);
    });
    if (dropmenu.css('height') == '0px') {
        dropmenu.css('height', dropItemsHeight);
    } else {
        dropmenu.css('height', 0);
    }
})

// 좋아요
$(document).on('click', '.btn.likes', function () {
    request = '/Post/like'
    const postId = $(this).parents('.postEach').children('input[name="postId"]').val();
    $.ajax({
        url: request,
        type: 'POST',
        dataType: 'application/json',
        data: {
            postId: postId,
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function () {
            const targetNum = $('.page-item.active').attr('data-pagenum');
            const pageData = {
                targetPage: targetNum,
                userId: location.pathname.substring(1, location.pathname.lastIndexOf('/')),
            }
            request = '/Post/loadMore';
            $.ajax({
                url: request,
                type: 'POST',
                dataType: 'text',
                data: pageData,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                cache: false,
                success: function (result, status) {
                    console.log(result);
                    $('#postView').replaceWith(result);
                },
                error: function (jqXHR, status, error) {
                    console.log(jqXHR);
                }
            })
        },
        error: function (jqXHR, status, error) {
            if(jqXHR.responseText == "Need Login") alert('로그인이 필요한 서비스 입니다.')
            console.log(jqXHR);
        }
    })
})