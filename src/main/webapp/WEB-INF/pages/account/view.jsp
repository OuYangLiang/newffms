<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-back">返回</button>
            <button id="btn-edit">修改</button>
            
            <c:if test="${acntForm.balance > 0 }" >
                <button id="btn-transfer">转账</button>
            </c:if>
            
            <c:if test="${isAccountSafeToRemove }" >
                <button id="btn-delete">删除</button>
            </c:if>
            
            <button id="btn-viewDetail">查询明细</button>
        </div>
        
        <div class="content-header ui-widget-header">
            账户<span style="font-size: 80%;"> - 查看页面</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">账户所有人</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.ownerUserName }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">账户类型</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.acntType.desc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">描述</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.acntDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">初始可用额度</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.balance }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <c:if test="${acntForm.acntType == \"Creditcard\" }" >
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">限定额度</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.quota }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">初始欠款额度</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.debt }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                </c:if>
            </div>
            
            <div id="detailArea" style="display:none;">
	            <div class="content-title ui-widget-header">
	                账户流水
	            </div>
	            
	            <div class="mainArea">
	                <div>
	                    <table id="gridList" ></table> 
	                    <div id="gridListNav"></div>
	                </div>
	            </div>
            </div>
        </div>
        
        <div id="delete-confirm-dialog" title="警告">
            确定要删除吗?
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/i18n/grid.locale-cn.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.jqGrid.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                $ (".button-area button").button();
                $ ("#btn-back").click(function(){
                    window.location.href = "<c:url value='/account/summary?keepSp=Y' />";
                });
                
                $ ("#btn-edit").click(function(){
                    window.location.href = "<c:url value='/account/initEdit' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                });
                
                var gridLoaded = false;
                $ ("#btn-viewDetail").click(function(){
                	if (gridLoaded) {
                		return;
                	}
                	
                	gridLoaded = true;
                	
                	$("#detailArea").attr("style", "display:''");
                	
                	$("#gridList").jqGrid({
                        url: "<c:url value='/account/listOfItemSummary' />" + "<c:out value='?acntOid=${acntForm.acntOid}' />",
                        
                        page: "1",
                        sortname: "CREATE_TIME",
                        sortorder: "desc",
                        rowNum: "10",
                        
                        jsonReader: {id: "adtOid"},
                        colNames: ["描述", "类型", "变化量", "操作人", "操作时间" , ""],
                        colModel: [
                            {sortable: false, name: "adtDesc", width: 300, align: "left"},
                            {sortable: false, name: "adtType", width: 100, align: "center"},
                            {sortable: false, name: "amount", width: 100, align: "right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥", suffix:"  "}},
                            {sortable: false, name: "baseObject.createBy", width: 100, align: "center"},
                            {sortable: false, name: "baseObject.createTime", width: 100, align: "center", formatter: 'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'}},
                            { sortable: false, align: "center", formatter:function (cellvalue, options, rowObject){
                                var url = '<c:url value='/accountAudit/view' />' + '?adtOid=' + rowObject.adtOid;
                                
                                var href = 'javascript:window.location.href="' + url + '"';
                                
                                return "<a href='" + href + "'>查看</a>";
                            }}
                        ]
                    });
                });
                
                $( "#delete-confirm-dialog" ).dialog( {
                    autoOpen: false,
                    modal: true,
                    show: {
                        effect: "blind",
                        duration: 300
                    },
                    hide: {
                        effect: "explode",
                        duration: 1000
                    },
                    buttons: {
                        "No": function(){
                            $( this ).dialog( "close" );
                        },
                        "Yes": function(){
                            window.location.href = "<c:url value='/account/delete' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                        }
                    }
                });
                
                if ($ ("#btn-transfer").length > 0) {
                	$ ("#btn-transfer").click(function(){
                        window.location.href = "<c:url value='/account/initTransfer' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                    });
                }
                
                if ($ ("#btn-delete").length > 0) {
                	$ ("#btn-delete").click(function(){
                        $ ( "#delete-confirm-dialog" ).dialog( "open" );
                    });
                }
                
            });
        </script>
    </body>
</html>
