<%--
  Created by IntelliJ IDEA.
  User: 何必多此一举
  Date: 2020/10/11
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>增加公开招标项目</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script>
        function addfile(t) {
            $(t).before("<input type=\"file\" name=\"file\">");
        }
        // function delfile() {
        // }
    </script>
</head>
<body>
增加项目招标
<form class="form-horizontal" action="/insertProjectbidding" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label class="col-sm-2 control-label">标题</label>
        <div class="col-sm-4">
            <input type="text" name="title" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">内容</label>
        <div class="col-sm-4">
            <input type="text" name="body" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">截止时间</label>
        <div class="col-sm-4">
            <input type="date" name="endtime" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">开标时间</label>
        <div class="col-sm-4">
            <input type="date" name="ontime" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">招标金额</label>
        <div class="col-sm-4">
            <input type="text" name="money" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">招标文件</label>
        <div class="col-sm-4">
            <input type="file" name="file">
            <a href="javascript:void(0)" onclick="addfile(this)">增加一个招标文件</a>
            <%--<a href="javascript:void(0)" onclick="delfile()">删除一个招标文件</a>--%>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">增加</button>
        </div>
    </div>
</form>
</body>
</html>
