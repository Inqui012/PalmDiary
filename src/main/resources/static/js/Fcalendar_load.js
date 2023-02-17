// 화면 로드될때 실행하는 기능. 
document.addEventListener('DOMContentLoaded', function () {
	var calendarEl = document.getElementById('Fcalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
			headerToolbar: {
				left: "prev,next today",
				center: "title",
				right: "dayGridMonth,timeGridWeek,timeGridDay,listMonth"
			},
            initialView: 'dayGridMonth',
			timeZone: 'local',
			locale: 'kr',
            events: UserEvents, 
			selectable: true,
			editable: true,
			dayMaxEvents: true,
			select: function (arg) {
				console.log(arg);
					if (OwnedUser) {
						// 이벤트 선택 모달 토글 (로그인 && 해당페이지 유저)
						const modalEventSelect = new bootstrap.Modal($('#modal_event-select'));
						modalEventSelect.toggle();
					} else {
						alert('다른 사람의 달력에는 일정을 추가할 수 없습니다.');
					}
			},
			eventClick: function (info) {
				fCalendarEventClick(info);
			},
        });
	calendar.render();
	createRandomColorBtn();
});

function createRandomColorBtn() {
	let colorSElectDiv = $('.form.calGroups-add div:first-of-type');
	for(var i = 0; i < 18; i++){
		let randomColor = '#' + Math.floor(Math.random() * (256 * 256 * 256)).toString(16).padStart(6, '0');
		let colorSelectBtn = 
			'<input class="form-check-input d-none" type="radio" name="groupsBgcolor" id="' + randomColor + '" value="' + randomColor + '">'
			+ '<label class="form-check-label position-relative" for="' + randomColor + '">'
			+ '		<svg class="position-absolute top-0 start-0" width="25" height="25" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">'
			+ '			<path d="M10 18.125C14.4873 18.125 18.125 14.4873 18.125 10C18.125 5.51269 14.4873 1.875 10 1.875C5.51269 1.875 1.875 5.51269 1.875 10C1.875 14.4873 5.51269 18.125 10 18.125Z"' 
				+ 'fill="' + randomColor + '"/>'
			+ '		</svg>'
			+ '		<svg class="position-absolute top-0 start-0" width="25" height="25" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">'
			+ '			<path d="M6.9943 9.86065C6.87801 9.74952 6.72335 9.6875 6.5625 9.6875C6.56237 9.6875 6.54832 9.68766 6.54832 9.68766C6.38261 9.69142 6.22517 9.76086 6.11065 9.8807C5.99952 9.99699 5.9375 10.1516 5.9375 10.3125L5.93766 10.3267C5.94142 10.4924 6.01086 10.6498 6.1307 10.7644L8.41976 12.9519C8.66119 13.1826 9.04135 13.1827 9.28298 12.9522L13.8688 8.57737C13.9887 8.46295 14.0584 8.30542 14.0623 8.13971C14.0626 8.1297 14.0626 8.11969 14.0623 8.10968C14.0585 7.95435 13.997 7.806 13.8897 7.69358C13.7718 7.56995 13.6084 7.5 13.4375 7.5L13.4199 7.50025C13.2653 7.50461 13.1179 7.56608 13.0061 7.67278L8.85193 11.6359L6.9943 9.86065Z"' 
				+ 'fill="white"/>'
			+ '			<path d="M10 1.875C8.39303 1.875 6.82214 2.35152 5.486 3.24431C4.14985 4.1371 3.10844 5.40605 2.49348 6.8907C1.87852 8.37535 1.71762 10.009 2.03112 11.5851C2.34463 13.1612 3.11846 14.6089 4.25476 15.7452C5.39106 16.8815 6.8388 17.6554 8.41489 17.9689C9.99099 18.2824 11.6247 18.1215 13.1093 17.5065C14.594 16.8916 15.8629 15.8502 16.7557 14.514C17.6485 13.1779 18.125 11.607 18.125 10C18.1209 7.84638 17.2635 5.78216 15.7407 4.25932C14.2178 2.73648 12.1536 1.87913 10 1.875ZM13.8672 8.57813L9.28907 12.9531C9.17071 13.0645 9.01406 13.126 8.85157 13.125C8.77214 13.1261 8.69328 13.1115 8.61953 13.082C8.54578 13.0525 8.47861 13.0087 8.42188 12.9531L6.13282 10.7656C6.06933 10.7102 6.01769 10.6426 5.98102 10.5667C5.94434 10.4909 5.92338 10.4084 5.9194 10.3242C5.91542 10.24 5.92849 10.1559 5.95784 10.077C5.98719 9.99798 6.03221 9.92575 6.09019 9.86461C6.14816 9.80347 6.2179 9.75469 6.29522 9.72119C6.37253 9.68769 6.45582 9.67017 6.54007 9.66968C6.62433 9.66919 6.70781 9.68574 6.78551 9.71834C6.86321 9.75094 6.93351 9.79891 6.99219 9.85937L8.85157 11.6328L13.0078 7.67187C13.1293 7.56585 13.2871 7.51091 13.4482 7.51853C13.6093 7.52615 13.7612 7.59575 13.8722 7.71277C13.9832 7.8298 14.0446 7.98519 14.0437 8.14646C14.0428 8.30773 13.9795 8.4624 13.8672 8.57813Z"' 
				+ 'fill="' + randomColor + '"/>'
			+ '		</svg>'
			+ '</label>'
		colorSElectDiv.append(colorSelectBtn);
	}
}

function fCalendarEventClick(info) {
	const modalScheduleEdit = new bootstrap.Modal($('#modal_shce-addEdit'));
	const scheduleEditForm = $('#modal_shce-addEdit').find('.form.shce-addEdit');
	scheduleEditForm.find('#scheId').val(info.event._def.extendedProps.eventId);
	scheduleEditForm.find('#shceName').val(info.event._def.title);
	scheduleEditForm.find('#groups').val(info.event._def.groupId).prop('selected', true);
	
	console.log(info);
	let endDateStr;
	if (info.event.endStr != '') {
		const endDate = new Date(info.event.endStr);
		const yesterDay = new Date(endDate.setDate(endDate.getDate() - 1));
		const yesterDayMonthStr = yesterDay.getMonth() + 1 > 9 ? yesterDay.getMonth() + 1 : '0' + (yesterDay.getMonth() + 1);
		const yesterDayStr = yesterDay.getDate() > 9 ? yesterDay.getDate() : '0' + yesterDay.getDate();
		endDateStr = yesterDay.getFullYear() + '-' + yesterDayMonthStr + '-' + yesterDayStr;
	} else endDateStr = info.event.startStr;

	if (info.event._def.allDay) {
		scheduleEditForm.find('#isAllday').prop('checked', true);
		scheduleEditForm.find('input[type="datetime-local"]').addClass('d-none');
		scheduleEditForm.find('input[type="date"]').removeClass('d-none');
		scheduleEditForm.find('#strDate').val(info.event.startStr);
		scheduleEditForm.find('#endDate').val(endDateStr);
	} else {
		endDateStr = info.event.endStr.substr(0, 16);
		scheduleEditForm.find('#isAllday').prop('checked', false);
		scheduleEditForm.find('input[type="datetime-local"]').removeClass('d-none');
		scheduleEditForm.find('input[type="date"]').addClass('d-none');
		scheduleEditForm.find('#strDateTime').val(info.event.startStr.substr(0, 16));
		scheduleEditForm.find('#endDateTime').val(endDateStr);
	}
	const recurringDef = info.event._def.recurringDef;
	if (recurringDef != null) {
		let reccur_FreqNum = recurringDef.typeData.rruleSet._rrule[0].options.freq;
		const reccur_IntervalNum = recurringDef.typeData.rruleSet._rrule[0].options.interval;
		if (reccur_FreqNum == 0) reccur_FreqNum = 'YEARLY';
		if (reccur_FreqNum == 1) reccur_FreqNum = 'MONTHLY';
		if (reccur_FreqNum == 2) reccur_FreqNum = 'WEEKLY';
		if (reccur_FreqNum == 3) reccur_FreqNum = 'DAILY';
		scheduleEditForm.find('#isReccur').prop('checked', true);
		scheduleEditForm.find('.reccur-select').removeClass('d-none');
		scheduleEditForm.find('#reccur_Interval').val(reccur_IntervalNum);
		scheduleEditForm.find('#reccur_Freq').val(reccur_FreqNum);
	} else {
		scheduleEditForm.find('#isReccur').prop('checked', false);
		scheduleEditForm.find('.reccur-select').addClass('d-none');
	}
	scheduleEditForm.find('#shceDetail').val(info.event._def.extendedProps.details);
	modalScheduleEdit.toggle();
}

// 그룹 추가버튼 눌렀을때.
$('.menu-group .btn.btn-add').on('click', function () {
	const groupsLists = {
		add_Shce: {
			title: '일정 그룹 추가',
			group: 'SCHEDULES',
			lists: subListSche,
		},
		add_Accn: {
			title: '가계부 그룹 추가',
			group: 'ACCOUNTS',
			lists: subListAccn
		},
		add_Memo: {
			title: '메모 그룹 추가',
			group: 'MEMO',
			lists: subListMemo,
		},
	};
	let buttonId = $(this).attr('id');
	let groupsDiv = $('#modal_groups-add');
	var groupsModal = new bootstrap.Modal($('#modal_groups-add'));
	
	function changeTextAndHiddenVal(buttonId) {
		let identifiedGroup = {};
		if (buttonId == 'add_Shce') identifiedGroup = groupsLists.add_Shce;
		if (buttonId == 'add_Accn') identifiedGroup = groupsLists.add_Accn;
		if (buttonId == 'add_Memo') identifiedGroup = groupsLists.add_Memo;
		groupsDiv.find('h3').text(identifiedGroup.title);
		groupsDiv.find('#groups_div').val(identifiedGroup.group);
		groupsDiv.find('#groups_st').append('<option cla value="new">새로운 상위 그룹 추가</option>');
		identifiedGroup.lists.forEach(el => groupsDiv.find('#groups_st').append('<option cla value="' + el.groupsName + '">' + el.groupsName + '</option>'));
		groupsModal.toggle();
	}

	changeTextAndHiddenVal(buttonId);
})


// 그룹 추가기능
$('.form.calGroups-add').on('submit', function (e) {
	e.preventDefault();
	
	if($('#groups_name').val() == ''){
		alert('그룹 이름을 입력해 주세요');
		return false;
	}
	let selectedSt = $('#groups_st').val();
	let groupsStName, groupsNdName, groupsBgSelected;
	if(selectedSt == 'new') {
		groupsStName = $('#groups_name').val();
		groupsNdName = 'NULL';
	} else {
		groupsStName = selectedSt;
		groupsNdName = $('#groups_name').val();
	}
	
	if($('input[name = "groupsBgcolor"]:checked').val() != undefined) {
		groupsBgSelected = $('input[name = "groupsBgcolor"]:checked').val();
	} else {
		alert('그룹 색상을 지정해 주세요');
		return false;
	}
	
	request = '/CalGroups/add';
    data = {
        groupsDiv: $('#groups_div').val(),
        groupsSt: groupsStName,
		groupsNd: groupsNdName,
		groupsBgcolor: groupsBgSelected
    };
    var newGroupsData = JSON.stringify(data);
	$.ajax({
        url: request,
        type: 'POST',
        contentType: 'application/json',
        data: newGroupsData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (result, status) {
			console.log(result);
			location.reload();
        },
        error: function (jqXHR, status, error) {
			let ErrorMsg = jqXHR.responseJSON;
			console.log(jqXHR);
        }
    })
})

// 모바일 화면 그룹 드롭다운
$('.big-menu > .btn-toggleDrop').on('click', function () {
	const parent = $(this).parents('.menu-group');
    const dropmenu = parent.children('ul.dropList');
    const arrowDown = $(this).children('.icon-arrowDrop');
    
    // 화살표 회전 
	if (arrowDown.css('transform') == 'matrix(0, -1, 1, 0, 0, 0)') {
		arrowDown.css('transform', 'rotateZ(0deg)');
	} else {
		arrowDown.css('transform','rotateZ(-90deg)');
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

// 모바일 화면 대그룹 드롭다운
$('.sub .btn-toggleDrop').on('click', function () {
	const parent = $(this).parents('.dropList-item');
    const dropmenu = parent.children('ul.dropList');
	const arrowDown = $(this).children('.icon-arrowDrop');
	const parentUl = parent.parents('ul.dropList');
    // 화살표 회전 
	if (arrowDown.css('transform') == 'matrix(0, -1, 1, 0, 0, 0)') {
        arrowDown.css('transform', 'rotateZ(0deg)');
	} else {
        arrowDown.css('transform','rotateZ(-90deg)');
    }
    
    // transition 을 위해 드롭다운 메뉴 내용물 총 크기에 따라서 부모 높이 지정.
    let dropItemsHeight = 0;
    dropmenu.children().each(function () {
		dropItemsHeight += $(this).outerHeight();
    });
	let cuurParentHeight = parseInt(parentUl.css('height'));
    if (dropmenu.css('height') == '0px') {
		dropmenu.css('height', dropItemsHeight);
		parentUl.css('height', cuurParentHeight + dropItemsHeight);
    } else {
		dropmenu.css('height', 0);
		parentUl.css('height', cuurParentHeight - dropItemsHeight);
    }
    if ($(window).innerWidth() <= 992) {
        $(this).toggleClass('clicked');
	}

});

// 이벤트 선택 모달
const ModalEventSelect = $('#modal_event-select');
function createEventGroupSelectList (buttonId, targetSelect) {
	let groupsList = [];
	if(buttonId == 'shceEvent') groupsList = subListSche;
	if(buttonId == 'accnEvent') groupsList = subListAccn;
	if(buttonId == 'memoEvent') groupsList = subListMemo;
	groupsList.forEach(parent => {
		const appendParentOption = '<option value="' + parent.groupsId + '">' + parent.groupsName + '</option>';
		targetSelect.append(appendParentOption);
		if (parent.childrens.length != 0) {
			parent.childrens.forEach(child => {
				const appendChildOption = '<option value="' + child.groupsId + '">' + child.groupsName + '</option>';
				targetSelect.append(appendChildOption);
			})
		}
	})
}

// 이벤트 선택 버튼 눌렀을때
ModalEventSelect.find('button[type="button"]').on('click', function() {
	const buttonId = $(this).attr('id');
	const targetModal = $(this).attr('data-bs-target');
	const targetSelect = $(targetModal).find('#groups');

	createEventGroupSelectList(buttonId, targetSelect);
})

// 종일 체크박스 선택시 인풋창 변경
$('#isAllday').on('change', function () {
	$('#strDateTime').toggleClass('d-none');
	$('#endDateTime').toggleClass('d-none');
	$('#strDate').toggleClass('d-none');
	$('#endDate').toggleClass('d-none');
})

// 반복 체크박스 선택시 인풋창 표시
$('#isReccur').on('change', function () {
	$('.reccur-select').toggleClass('d-none');
})

// 일정추가 버튼 클릭시
$('.form.shce-addEdit').on('submit', function (e) {
	e.preventDefault();
	const secondAndMilsec = ':00'
	const defaultTime = 'T00:00:00'
	let scheId = $(this).find('#scheId').val();
	let shceName = $(this).find('#shceName').val();
	let groups = $(this).find('#groups').val();
	let strDateTime = $(this).find('#strDateTime').val();
	let endDateTime = $(this).find('#endDateTime').val();
	let strDate = $(this).find('#strDate').val();
	let endDate = $(this).find('#endDate').val();
	let reccur_Interval = $(this).find('#reccur_Interval').val();
	let reccur_Freq = $(this).find('#reccur_Freq').val();
	let shceDetail = $(this).find('#shceDetail').val();
	let isAllday = $(this).find('#isAllday').prop('checked');
	let isReccur = $(this).find('#isReccur').prop('checked');
	let start, end;

	if (shceName == '') { alert('일정 이름을 입력해주세요'); return false; }
	if (groups == '') { alert('일정 그룹을 선택해주세요'); return false; }
	if (strDate == '' && strDateTime == '') { alert('일정 시작날자를 선택해 주세요'); return false; };
	if (isAllday) {
		if (endDate == '') { alert('일정 종료날자를 선택해 주세요'); return false; }
		start = strDate + defaultTime;
		end = endDate + defaultTime;
		isAllday = 'TRUE';
	} else {
		if (endDateTime == '') { alert('일정 종료시간을 선택해 주세요'); return false; }
		start = strDateTime + secondAndMilsec;
		end = endDateTime + secondAndMilsec;
		isAllday = 'FALSE';
	}
	if (shceDetail == '') { shceDetail = 'NONE'; } 
	if (isReccur) { reccur_Interval = parseInt(reccur_Interval); }
	else { reccur_Interval = 0; reccur_Freq = 'NONE'; }

	console.log(scheId);
	if (scheId == '') request = '/Schedule/add';
	else request = '/Schedule/edit';

    data = {
        scheName: shceName,
        shceDetail: shceDetail,
		shceGroupId: parseInt(groups),
		strDate: start,
		endDate: end,
		isAllDay: isAllday,
		reccurInterval: reccur_Interval,
		reccurFreq: reccur_Freq,
	};
	
	var newSchedulesData = JSON.stringify(data);
	
	$.ajax({
        url: request,
        type: 'POST',
        contentType: 'application/json',
        data: newSchedulesData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (result, status) {
			console.log(result);
			location.reload();
        },
        error: function (jqXHR, status, error) {
			let ErrorMsg = jqXHR.responseJSON;
			alert('fail');
			console.log(jqXHR);
        }
    })
})
