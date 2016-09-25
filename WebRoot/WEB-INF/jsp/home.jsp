<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
// System.out.print(path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <base href="<%=basePath%>">
     
	<meta charset="UTF-8">
	<title>Basic Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/demo/demo.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
    <script type="text/javascript" src="js/originalLogFile.js"></script>
    <script type="text/javascript">
       $(document).ready(function(){
          $("div#123").click(function(){
    	     alert('ok');
          });
      });
    </script>
    
    <script type="text/javascript">
      function showcontent(language){
	    $('#123').html('Introduction to ' + language + ' language');
    }
    </script>
    
    <script type="text/javascript">
      function formatOper(val,row,index){
         return '<a href="#" onclick="editUser('+index+')">修改</a>';
      }
    </script>
    
    <script type="text/javascript">
      function editUser(index){
         $('#orilog_id').datagrid('selectRow',index);// 关键在这里  
         var row = $('#orilog_id').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','修改学生信息');
                $('#fm').form('load',row);
                url = '${ctx}updateStudent.do?id='+row.id;
            }
      }
    </script>
    
    <script type="text/javascript">
       function rowformater(value,row,index){
           return '<a href="javacript:;" onclick="delOriFile('+row.id+');">删除</a>' 
           + "  " + '<a href="javacript:;" onclick="downloadOriFile('+row.id+');">下载</a>';
       }
    </script>
    <script type="text/javascript">
	  function delOriFile(id){
		 $.get("/test_ssh/OriFile/delOriFile?id=" + id, function(data){
			if("success" == data.result){
				alert("删除成功!");
				window.location.reload();
			}else{
				alert("删除失败!");
			}
		});
	  }
    </script>
    <script type="text/javascript">
	  function downloadOriFile(id){
		 $.get("/test_ssh/OriFile/getOriFile?id=" + id, function(data){
			if("success" == data.result){
				alert("下载成功!");
				//window.location.reload();
			}else{
				alert("下载失败!");
			}
		});
	  }
    </script>
    
    <script type="text/javascript">
       function uploadFileClick() {
         var path = $("#txt_head").filebox('getValue');
         alert($("#txt_head").filebox('getValue'));
         $.get("/test_ssh/OriFile/addOriFile?path=" + path, function(data){
			if("success" == data.result){
				alert("上传成功!");
				window.location.reload();
			}else{
				alert("上传失败!");
			}
		});
        //document.getElementById('fileName').innerHTML = "";
        //document.getElementById('uploadInfo').innerHTML = "";
    }
    </script>
    
    <script type="text/javascript">
    $(function() {
    // 先通过ajax获取数据，然后再传给datagrid
    	$.get("/ProcessMining/log/getLog", function(data){
//    		if("success".equals(data.success)){
				for(var i=0;i<data.total;i++){
    				//alert(data.rows[file].orifileName);
    				var row_data = {
    					id : data.rows[i].id,
    					name : data.rows[i].name,
    					format : data.rows[i].format,
    					date : data.rows[i].date,
    					user : data.rows[i].user.email,
    					link : data.rows[i].link,
    				};
    				//alert(row_data);
    				$('#orilog_id').datagrid('appendRow', row_data);
    			}
//			}else{
//				alert("查找失败!");
//			}
		});
    })

    </script>
    
    
	
</head>
<body style = "background-image:url(images/head.jpg)">
    <p></p>
	<h2> 欢迎登陆流程挖掘平台 </h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" title="导航栏" style="width:250px;">
		
		   <div class="easyui-accordion" style="width:500px;height:300px;">
		         <div id="logmanagement_id" class="div" title="日志管理" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
			         <h3 style="color:#0099FF;"></h3>
			         <ul>
			             <li>
			                 <a href="javascript:void(0)" onclick="showcontent('java')">Java</a></li>
			             </li>
		             </ul>
		       </div>
		       <div id="123" title="日志融合" data-options="iconCls:'icon-help'" style="padding:10px;">		
		       </div>
		       <div title="流程挖掘" data-options="iconCls:'icon-search'" style="padding:10px;">
		       </div>
		   </div>
		</div>
		
		<div id="123" data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
		    <div id="jquerylogshow_id" class="easyui-tabs" data-options="fit:true";">
	            <div title="原始日志" style="padding:20px;">
	            
	            <table id="orilog_id" class="easyui-datagrid" title="原始日志"
			       toolbar="#ff" iconCls="icon-save" rownumbers="true" pagination="true" >
		              <thead>
			             <tr>
				            <th data-options="field:'name',width:150">日志名称</th>
				            <th data-options="field:'format',width:100">日志格式</th>
				            <th data-options="field:'date',width:150,align:'right'">创建时间</th>
				            <th data-options="field:'user',width:150,align:'right'">创建人</th>
				            <th field="idd" width="100" formatter="rowformater" align="center">操作</th>

			             </tr>
		              </thead>
	               </table>
	               
	         <!--       <table id="orilog_id2" class="easyui-datagrid" title="原始日志"
			       url="/test_ssh/OriFile/searchAllOriFile" toolbar="#ff" iconCls="icon-save" rownumbers="true" pagination="true" >
		              <thead>
			             <tr>
				            <th data-options="field:'orifileName',width:150,align:'center'">日志名称</th>
				            <th data-options="field:'format',width:100,align:'center'">日志格式</th>
				            <th data-options="field:'eventLog',width:150,align:'center'">事件日志</th>
				            <th data-options="field:'creationDate',width:150,align:'center'">创建时间</th>
				            <th data-options="field:'creationBy',width:100,align:'center'">创建人</th>
				            <th field="idd" width="100" formatter="rowformater" align="center">操作</th>
			             </tr>
		              </thead>
	               </table> -->
	            
	            </div>
	            <div title="事件日志" closable="true" style="padding:20px;">
		          
	            </div>
             </div>
		</div>
	</div>
	
	<form id='ff' class=easyui-form >
       <input id="txt_head"  name="name" class="easyui-filebox" data-options="buttonText:'选择文件',prompt:'请选择文件...',editable:false" />
       <a id="uploadFile" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="uploadFileClick()" href="javascript:;">上传</a>
       <a id="cancelUploadfile" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="cancelImportClick()" href="javascript:;">取消上传</a>
       <div id="tb1" style="padding:3px; float:right">
	      <input id="search" style="line-height:15px;border:1px solid #ccc"></input>
	      <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查找</a>
       </div>
     </form>
	
	<div id="tb1" style="padding:3px; float:right">
	   <input class="easyui-filebox" name="source" id="source_sign"/>
	   <input id="search" style="line-height:26px;border:1px solid #ccc">
	   <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查找</a>
    </div>
    <div id="tb2" style="padding:3px; float:right">
	   <input class="easyui-filebox" name="source" id="source_sign"/>
	   <input id="search" style="line-height:26px;border:1px solid #ccc">
	   <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查找</a>
    </div>
</body>
</html>
