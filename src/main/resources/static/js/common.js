// ajax 용 전역변수
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let request = '';
let data = {};


// let ajaxOjb = {
//     url: '',
//     type: 'POST',
//     contentType: 'application/json',
//     data: '',
//     beforeSend: function (xhr) {
//         xhr.setRequestHeader(header, token);
//         console.log(loginDataJson);
//     },
//     dataType: 'json',
//     cache: false,
//     success: null,
//     error: null,
// }

// nav 페이지 이동
$('nav.navbar a[name="pageMove"]').each(function () {
    $(this).on('click', function (e) {
        e.preventDefault();
        let userId = location.pathname.substring(1, location.pathname.lastIndexOf('/'));
        if (user != null) userId = user.usersId;

        const pageTo = $(this).attr('href');
        request = '/' + userId + pageTo;

        $.ajax({
            url: request,
            type: 'GET',
            contentType: 'text/plain',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            cache: false,
            success: function () {
                location.href = request;
            },
            error: function(jqXHR, status, error) {
                alert('error')
                console.log(jqXHR)
            },
        })
    })
})


// 헤더 드롭다운 기능
$('.offcanvas-mod .btn-toggleDrop').on('click', function () {
    const dropmenu = $('.offcanvas-mod .dropList');
    const arrowDown = $(this).children('.offcanvas-mod .icon-arrowDrop');
    
    // 화살표 회전 
    if (arrowDown.css('transform') == 'matrix(0, -1, 1, 0, 0, 0)') {
        arrowDown.css('transform','rotateZ(0deg)');
    } else {
        arrowDown.css('transform', 'rotateZ(-90deg)');
    }
    
    // transition 을 위해 드롭다운 메뉴 내용물 총 크기에 따라서 부모 높이 지정.
    let dropItemsHeight = 0;
    dropmenu.children().each(function () {
        dropItemsHeight += $(this).outerHeight();
    });
    if (dropmenu.css('height') == '0px') {
        dropmenu.css('height', dropItemsHeight);
    } else {
        dropmenu.css('height', 0);
    }
    if ($(window).innerWidth() <= 992) {
        $(this).toggleClass('clicked');
    }
});

// 로그인
$('.form.login').on('submit', function (e) {
    e.preventDefault();
    request = '/user/login';
    data = {
        loginId: $('#user_id').val(),
        loginPw: $('#user_pw').val(),
        isRemember: $('#rememberMe').is(':checked')
    };
    let loginDataJson = JSON.stringify(data);
    
    $.ajax({
        url: request,
        type: 'POST',
        contentType: 'application/json',
        data: loginDataJson,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
            console.log(loginDataJson);
        },
        dataType: 'json',
        cache: false,
        success: function (result, status) {
            console.log(result);
            if (result.code == 'OK') {
                alert(result.message);
                if (location.pathname != '/') {
                    // const currentPage = location.pathname;
                    // pageLoad(null, null, currentPage);
                    location.reload();
                } else {
                    // pageLoad(data.loginId, 'Schedule', null);
                    location.href = '/' + data.loginId + '/';
                }
            } else {
                alert('통신은 성공 로그인 실패');
                console.log(result);
                $('#modal_login').toggle();
            }
        },
        error: function (jqXHR, status, error) {
            alert('로그인 실패');
            console.log(jqXHR);
        }
    })  
})

// 페이지 리로드 테스트 보류
// function pageLoad(user, path, fullPath) {
//     let url;
//     if (user == null) url = fullPath;
//     else url = '/' + user + '/' + path;
//     history.pushState(null, null, url);
//     $.ajax({
//         type: "GET",
//         url: url,
//         error: function (jqXHR) {
//             alert("fail");
//             console.log(jqXHR);
//         },
//         success: function (respData) {
//             $('#section').replaceWith(respData);
//             alert("success");
//         }
//     })
// }


// 비밀번호 확인
function checkPw() {
    let pw = $('#regist_pw').val();
    let pwC = $('#regist_pwConf').val();
    if (pw != '' && pw == pwC) {
        $('#regist_pwConf ~ svg').css('display', 'block');
    } else {
        $('#regist_pwConf ~ svg').css('display', 'none');
    }
};

// 이벤트에 함수줄때는 함수면에 () 없어야함.
$('#regist_pw').on('change', checkPw);
$('#regist_pwConf').on('change', checkPw);

// 회원가입
$('.form.register').submit(function (e) {
    e.preventDefault();
    let pw = $('#regist_pw').val();
    let pwC = $('#regist_pwConf').val();
    if (pw != pwC || pwC == '') {
        if (!$('#regist_pwConf').hasClass('error')) {
            $('#regist_pwConf').addClass('error')
        } 
        $('#regist_pwConf + span').text('비밀번호가 일치하지 않습니다.');
        return false
    };
    // 여기서 정하는 데이터의 키값을 연결할 DTO 의 필드명과 동일하게 맞춰줘야함...
    request = '/user/regist';
    data = {
        registId: $('#regist_id').val(),
        registPw: $('#regist_pw').val(),
        registEmail: $('#regist_email').val()
    };
    var registData = JSON.stringify(data);


    $.ajax({
        url: request,
        type: 'POST',
        contentType: 'application/json',
        data: registData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        // dataType: 'json',
        cache: false,
        success: function (result, status) {
            alert(result);
            location.href = '/';
        },
        error: function (jqXHR, status, error) {
            let ErrorMsg = jqXHR.responseJSON;          
            if ('registId' in ErrorMsg) {
                if (!$('#regist_id').hasClass('error')) {
                    $('#regist_id').addClass('error')
                } 
                $('#regist_id + span').text(ErrorMsg.registId);
            }
            if ('registPw' in ErrorMsg) {
                if (!$('#regist_pw').hasClass('error')) {
                    $('#regist_pw').addClass('error')
                } 
                $('#regist_pw + span').text(ErrorMsg.registPw);
            }
            if ('registEmail' in ErrorMsg) {
                if (!$('#regist_email').hasClass('error')) {
                    $('#regist_email').addClass('error')
                } 
                $('#regist_email + span').text(ErrorMsg.registEmail);
            }
            if ('Exception' in ErrorMsg) {
                alert(jqXHR.status + '\n' + ErrorMsg.Exception);
                $('.signin')[0].reset();
            }
        }
    })
});

// 로그아웃
$('#btn_logOut').on('click', function () {
    request = '/logout'
    $.ajax({
        url: request,
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (result, status) {
            console.log("logout");
            location.reload();
        },
        error: function (jqXHR, status, console) {
            console.log(jqXHR);
        }
    })
})

// 모달 사라질때 공통으로 이벤트 발생.
const modalGroups = $('.modal.fade');
modalGroups.each( function() {
	$(this).on('hidden.bs.modal', function() {
		$(this).find('form')[0].reset();
		const modalType = $(this).attr('id');
		const select = $('#' + modalType).find('select#groups');
		const h3Tag = $('#' + modalType).find('h3');
		$('#' + modalType).find('input[type="hidden"]').val('');
		if (modalType != 'modal_event-select') h3Tag.text('');
		if (modalType == 'modal_shce-addEdit') {
			$('#strDateTime').removeClass('d-none');
			$('#endDateTime').removeClass('d-none');
			$('#strDate').addClass('d-none');
			$('#endDate').addClass('d-none');
			$('.reccur-select').addClass('d-none');
		}
		if (select != 'undefined') select.children().remove();
	})
});