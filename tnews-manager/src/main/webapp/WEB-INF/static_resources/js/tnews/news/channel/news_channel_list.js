var newsChannel = {};
(function($, newsChannel) {

	/*
	 * 设置接口频道
	 */
	newsChannel.btnSetApiChannel= function(url) {
		var btn = $('<button type="button" class="btn-default" '
				+ 'data-icon="fa-gears" >关联接口频道</button>');
		btn.on('click', function() {
			var table = $.CurrentNavtab.find('#datagrid_news_channel_list_filter');
			var selectedDatas = $(table).data('selectedDatas');
			if (selectedDatas && (selectedDatas.length == 1)) {
				var id = selectedDatas[0].id;
				var params = {};
				params.newsChannelId = id;
				BJUI.navtab({
					id : 'news_channel_setApichannel_navTab',
					url : url,
					title : '关联接口频道',
					fresh : true,
					data : params
				})
			} else {
				BJUI.alertmsg('error', '请选择要操作的行！');
			}
		});
		return btn;
	};
	
	/*
	 * 
	 */
	newsChannel.getOldData = function(data) {
		if (data) {
			return data.split(',').map(function(data){  
		        return +data;  
		    });
		}
	};
	
})(jQuery, newsChannel);