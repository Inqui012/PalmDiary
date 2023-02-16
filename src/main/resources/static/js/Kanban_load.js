let testing = [{
			title : 'text title',
			detail : 'detail',
			user : 'img url',
			date : 'date'
}]
let boards = [
	        {
	            'id' : '_todo',
	            'title'  : 'To 22222',
	            'class' : 'info',
	            'dragTo' : ['_working'],
	            'item'  : [
	                {
	                    'title':'My Task Test',
	                },
	                {
	                    'title':'Buy Milk',
	                }
	            ]
	        },
	        {
	            'id' : '_working',
	            'title'  : 'Working',
	            'class' : 'warning',
	            'item'  : [
	                {
	                    'title':'Do Something!',
	                },
	                {
	                    'title':'Run?',
	                }
	            ]
	        },
	        {
	            'id' : '_done',
	            'title'  : 'Done',
	            'class' : 'success',
	            'item'  : [
	                {
	                    'title': makekanban(testing),
	                },
	                {
	                    'title':'Ok!',
	                }
	            ]
	        }
];
        
		function makekanban(data) {
			for(var a in data){
				return '<div>' + data[a].title + '</div>'
					+ '<div>' + data[a].date + '</div>';
			}	
}

		var kanban3 = new jKanban({
		    element : '#kanban',
		    gutter  : '15px',
		    click : function(el){
		        alert(el.innerHTML);
		    },
		    boards  : boards
		});