<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-save">新建</button>
            <button id="btn-cancel">返回</button>
        </div>
        
        <spring:errors path="errors" />
        
        <c:url value='/category/confirmAdd' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="catForm" autocomplete="off" >
        
        <div id="errorArea" class="ui-widget" style="margin-bottom:5px;display:none">
            <div class="ui-state-error ui-corner-all" style="margin-right: 400px; padding: 5px 30px;" >
                <span class="ui-icon ui-icon-alert" style="float: left; margin-top:5px; "></span>
                <span id="errorMsg" style="font-size: 70%;"><spring:errors path="*" /></span>
            </div>
            
            <div style="clear:both;" ></div>
        </div>
        
        <div class="content-header ui-widget-header">
            类别<span style="font-size: 80%;"> - 新建</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">类别描述</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="categoryDesc" class="inputbox" maxlength="10" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">月度预算</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="monthlyBudget" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">父类别</div>
                    
                    <div class="input">
                        <spring:input id="parentCategoryDesc" path="parentCategoryDesc" class="inputbox" readonly="true" onClick="javascript:selectCategory();" />
                        <input type="hidden" id="parentOid" name="parentOid" value="${catForm.parentOid }" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
            </div>
            
        </div>
        
        </spring:form>
        
        <div id="category-select-dialog" title="选择父类别" >
            <div>
                <table id="category-select-grid" ></table> 
            </div>
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/i18n/grid.locale-cn.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.jqGrid.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	if ('<c:out value="${validation}" />' === 'false') {
                    $("#errorArea").css("display", "");
                }
            	
            	$ (".button-area button").button();
                
                $ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/category/summary' />";
                });
                
                $ ("#btn-save").click(function(){
                    //提交表单时，使用使用jquery validation engine进行前端基本验证。
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                        $ ("#form").submit();
                    }
                });
            	
                
                var categoryGridLoaded = false;
                $( "#category-select-dialog" ).dialog({
                	autoOpen: false,
                    maxHeight: 400,
                    minWidth: 300,
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
                        "Close": function(){
                            $( this ).dialog( "close" );
                        }
                    }
                });
                
                chooseCategory = function(categoryOid, categoryDesc) {
                    $( "#parentCategoryDesc").val(categoryDesc);
                    $( "#parentOid").val(categoryOid);
                    $ ( "#category-select-dialog" ).dialog( "close" );
                };
                
                selectCategory = function(seq) {
                    if (!categoryGridLoaded) {
                        categoryGridLoaded = true;
                        
                        $("#category-select-grid").jqGrid({
                            url: "<c:url value='/category/ajaxGetAllCategories' />",
                            jsonReader: {id: "categoryOidDesc"},
                            colNames: ["描术", "级别", ""],
                            colModel: [
                                { name: "categoryDesc", width: 155, align: "left", sortable: false },
                                { name: "categoryLevel", width: 50, align: "center", sortable: false, formatter:function (cellvalue, options, rowObject){
                                    return (cellvalue+1) + "级";
                                }},
                                { sortable: false, width: 50, align: "center", formatter:function (cellvalue, options, rowObject){
                                	return "<a href=\"javascript:chooseCategory(" + rowObject.categoryOid + ", '" + rowObject.categoryDesc + "');\" >" + "选择" + "</a>";
                                }}
                            ],
                            
                            treeGrid: true,
                            treeReader: {
                                level_field: "categoryLevel",
                                parent_id_field: "parentOidDesc",
                                leaf_field: "isLeaf"
                            },
                            ExpandColClick: true,
                            ExpandColumn: "categoryDesc",
                            treeIcons: {leaf:'ui-icon-circle-check'},
                            treeGridModel: "adjacency",
                            rownumbers: false,
                            autowidth: true
                        });
                    }
                    
                    $ ( "#category-select-dialog" ).dialog( "open" );
                };
            });
        </script>
    
    </body>
</html>