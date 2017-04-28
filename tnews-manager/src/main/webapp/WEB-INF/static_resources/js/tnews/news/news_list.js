var news = {};
(function($, news) {

	/**
	 * 开关按钮
	 */
	news.btnSwitches = function(url) {
		var switchApiFilter = $.CurrentNavtab.find('#hidden_switch_api_filter').val(),
			switchApi = $.CurrentNavtab.find('#hidden_switch_api').val(),
			switchAuto = $.CurrentNavtab.find('#hidden_switch_auto').val(),
			disabledSyncBtn = "";
			
			if (switchAuto == 'checked') {
				disabledSyncBtn = 'disabled';
			}
		var btn = $('<div class="btn-group"><input type="checkbox" name="switchApiFilter" ' + switchApiFilter + '>' + 
				'<input type="checkbox" name="switchApi" ' + switchApi + '>' + 
				'<input type="checkbox" name="switchAuto" ' + switchAuto + '></div>' + 
				'<button type="button" class="btn btn-success" id="btn_sync_news" ' + disabledSyncBtn + '><i class="fa fa-refresh fa-1x fa-fw"></i> 更新新闻</button>');
		return btn;
	};
	/**
	 * 预览按钮
	 */
	news.btnView = function(newsId){
        return '<button type="button" class="btn-orange" data-toggle="dialog" data-options="{id:\'test_dialog6\', url:\'{#base}/news/detail/' + newsId + '\', title:\'预览新闻\', width:375, height:667, mask:false, loadingmask:true, fresh:true, resizable:false, minable: false, maxable: false}">预览</button>';
    };

	/**
	 * 新闻接口开关
	 */
	news.switchApi = function(type, switchApi){
		BJUI.ajax('doajax', {
		    url: 'news/switch/' + type,
		    data: {switchApi: switchApi},
		    loadingmask: true,
		    okalert: false,
		    okCallback: function(json, options) {
		        console.log('返回内容：\n'+ JSON.stringify(json))
		    }
		})
	};
	
	news.init = function() {
		$(document).on(BJUI.eventType.afterInitUI, function(event) {
			var nav = $(event.target);
			if (nav.attr('class') != 'datagrid-toolbar') {
				return;
			}
			console.log(nav);
			nav.find("input[name=switchApiFilter]").bootstrapSwitch({
				labelText: '接口筛选导入',
				onText: '已开启',
				offText: '已关闭',
				onColor: 'success',
				offColor: 'danger',
				size: 'mini',
				labelWidth: '100',
				onSwitchChange: function(event, state) {
					news.switchApi(2, state);
				}
			});
			nav.find("input[name=switchApi]").bootstrapSwitch({
				labelText: '接口导入',
				onText: '已开启',
				offText: '已关闭',
				onColor: 'success',
				offColor: 'danger',
				size: 'mini',
				labelWidth: '100',
				onSwitchChange: function(event, state) {
					news.switchApi(1, state);
				}
			});
			nav.find("input[name=switchAuto]").bootstrapSwitch({
				labelText: '更新新闻',
				onText: '自动',
				offText: '手动',
				onColor: 'success',
				offColor: 'warning',
				size: 'mini',
				labelWidth: '100',
				onSwitchChange: function(event, state) {
					if (state) {
						//自动则不允许手动操作
						nav.find("#btn_sync_news").prop('disabled', true);
					} else {
						nav.find("#btn_sync_news").prop('disabled', false);
					}
					news.switchApi(3, state);
				}
			});
			
			nav.find("#btn_sync_news").off().on('click', function (){
				var $this = $(this);
				$this.prop('disabled', true);
				$this.find('i').addClass('fa-spin');
				
				BJUI.ajax('doajax', {
				    url: 'news/sync',
				    //loadingmask: true,
				    okalert: false,
				    okCallback: function(json, options) {
				    	$this.prop('disabled', false);
				    	$this.find('i').removeClass('fa-spin');
				    	//刷新表格
				    	$.CurrentNavtab.find('#datagrid_news_list_filter').datagrid('refresh', true);
				    }
				})
			});
		});
	};
	
	news.init();
})(jQuery, news);