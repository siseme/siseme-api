<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title of the document</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
</head>
<body style="background-color: #EFEFEF;">
<div class="ui fixed inverted menu">
    <div class="ui container">
        <a href="#" class="header item">
            카페 자동화 테스트
        </a>
    </div>
</div>
<div class="ui one stackable cards" style="margin-top: 60px;">
<#list data as list>
    <div class="card">
        <div class="content">
            <div class="description">
                <table class="ui celled table">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>카페명</th>
                        <th>메뉴명</th>
                        <th>지역명</th>
                        <th>제목</th>
                        <th>최종작성일</th>
                        <th>전송</th>
                        <th>결과</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list list as item>
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.cafeName}</td>
                            <td>${item.menuName}</td>
                            <td>${item.regionName}</td>
                            <td>
                                <input type="text" id="${item.id}-title" value="">
                            </td>
                            <td>
                            ${item.reportingDate}
                            </td>
                            <td>
                                <input type="hidden" id="token" value="${token}">
                                <input type="hidden" id="id" value="${item.id?long?c}">
                                <input type="submit" value="전송" onclick="send(${item.id?long?c})">
                            </td>
                            <td>
                                <a href="" target="_blank" class="${item.id}-result">-</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</#list>
</div>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
<script type="text/javascript">
    send = (id) => {
        let token = $('#token').val();
        let template = $(":input:radio[name=" + id + "-template]:checked").val();
        let title = $('#' + id + '-title').val();
        $.get("/admin/send", {
            id: id,
            token: token,
            title: title,
            template: template
        }).done(function (data) {
            let result = JSON.parse(data);
            if (result.errorCode) {
                alert(result.errorMessage);
                return;
            }
            if (result.message.status === '200') {
                alert('성공');
                $('.' + id + '-result').attr("href", result.message.result.articleUrl);
                $('.' + id + '-result').text(result.message.result.articleUrl);
            }
            else {
                alert('실패');
                $('.' + id + '-result').text(result.message.error.message);
            }
        }).fail(function (data) {
            alert('실패');
        });
    };
</script>
</body>
</html>