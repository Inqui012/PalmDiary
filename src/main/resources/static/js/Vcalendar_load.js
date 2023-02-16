const calendar = new VanillaCalendar('#Vcalendar', {
	type: 'default',
	actions: {
		clickDay(event, dates) {
			// alert(dates);
		},
		clickArrow(event, year, month) {
			console.log(`Current year: ${year}, current month: ${month}`);
		},
	},
	settings: {
		selected: {
			dates: postsDates,
		},
	},
});
calendar.init();