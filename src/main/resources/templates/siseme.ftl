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
            아파트명 매핑 툴
        </a>
    </div>
</div>
<div class="ui one stackable cards" style="margin-top: 60px;">
    <div class="card">
        <div class="content">
            <div class="description">
                <table class="ui celled table">
                    ${totalElements} 개 남음
                    <thead>
                    <tr>
                        <th>동</th>
                        <th>번지</th>
                        <th>아파트명</th>
                        <th>매핑</th>
                        <th>전송</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list data as item>
                        <tr>
                            <form action="/me/sise/api/admin/apt/add" method="get">
                            <td>${item.dong}</td>
                            <td>${item.lotNumber}</td>
                            <td>${item.aptName}</td>
                            <td>
                        <#list item.mappingList as apt>
                            <label for="kapt">${apt.kaptName}</label> <input type="radio" name="kapt" value="${apt.kaptCode}/${apt.kaptName}"><br/>
                        </#list>
                                <label for="kapt">알수없음</label> <input id="kapt" type="radio" name="kapt" value="" checked><br/>
                            </td>
                            <td>
                                <input type="hidden" name="id" value="${item.id?long?c}">
                                <input type="submit" value="Submit">
                            </td>
                            </form>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
<script type="text/javascript">
</script>
</body>
</html>