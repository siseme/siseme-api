<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="https://i.imgur.com/4vj8Kcn.png">
    <meta property="og:title" content="시세미">
    <meta property="og:description" content="아파트 실거래가 조회 서비스"/>
    <meta property="og:image" content="https://i.imgur.com/gDWOt7Z.png">
    <script src="https://cdn.polyfill.io/v2/polyfill.min.js"></script>
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-129702818-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];

        function gtag() {
            dataLayer.push(arguments);
        }

        gtag('js', new Date());

        gtag('config', 'UA-129702818-1');
    </script>
    <meta charset="utf-8">
    <title>시세미 - 아파트 실거래가 조회 서비스</title>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no'/>

    <!-- Demo Dependencies -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet"
          type="text/css"/>

    <!-- keen-dataviz@1.1.3 -->
    <link href="https://d26b395fwzu5fz.cloudfront.net/keen-dataviz-1.1.3.css" rel="stylesheet"/>

    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://maxbeier.github.io/text-spinners/spinners.css">
    <link rel="stylesheet" href="https://naver.github.io/billboard.js/release/latest/dist/billboard.min.css">

    <!-- 웹폰트 -->
    <link href='https://cdn.rawgit.com/openhiun/hangul/14c0f6faa2941116bb53001d6a7dcd5e82300c3f/nanumbarungothic.css'
          rel='stylesheet' type='text/css'>
    <style>
        body {
            font-family: 'Nanum Barun Gothic', sans-serif;
        }

        table {
            border: 1px solid #ccc;
            width: 100%;
            margin: 0;
            padding: 0;
            border-collapse: collapse;
            border-spacing: 0;
        }

        table tr {
            border: 1px solid #ddd;
            padding: 5px;
        }

        table th, table td {
            padding: 4px;
            text-align: center;
        }

        table th {
            text-transform: uppercase;
            font-size: 12px;
            font-weight: 600;
            color: #273234;
        }

        table td {
            font-size: 10px;
            font-weight: 400;
            color: #273234;
        }

        .input-form {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .form-date-input {
            border: none;
            height: 28px;
            font-size: 14px;
            color: #273234;
            width: 93px;
            cursor: pointer;
            background-color: #DCE5E5;
            border-radius: 2px;
            margin-bottom: 5px;
            vertical-align: top;
            text-align-last: center;
            padding-left: 4px;
        }

        /*
                .form-date-input:hover {
                    background-color: #eee;
                }
        */

        .form-select-input {
            border: none;
            -webkit-appearance: none;
            -webkit-border-radius: 0px;
            border-radius: 2px;
            height: 28px;
            width: 93px;
            font-size: 14px;
            color: #273234;
            cursor: pointer;
            background-color: #DCE5E5;
            vertical-align: top;
            text-align-last: center;
            margin-bottom: 5px;
            padding-left: 4px;
            padding-right: 4px;
        }

        /*
                .form-select-input:hover {
                    background-color: #eee;
                }
        */

        .form-search-btn {
            color: #fff;
            font-weight: 200;
            background-color: #486171;
            position: relative;
            border: 1px solid #486171;
            border-radius: 2px;
            outline: none;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            line-height: 1;
            height: 28px;
            width: 93px;
            font-size: 14px;
        }

        .trade-filter-btn {
            color: #fff;
            background-color: #524F77;
            width: 78px;
            height: 25px;
            position: relative;
            border: 1px solid #524F77;
            border-radius: 2px;
            cursor: pointer;
            font-size: 12px;
            font-weight: 200;
            margin-bottom: 4px;
        }

        .trade-sort {
            color: #fff;
            background-color: #82A39E;
            width: 80px;
            position: relative;
            border: 1px solid #82A39E;
            border-radius: 2px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 400;
            margin-bottom: 4px;
        }

        .trade-sort-btn {
            border: none;
            -webkit-appearance: none;
            -webkit-border-radius: 0px;
            border-radius: 2px;
            width: 78px;
            height: 25px;
            font-size: 12px;
            font-weight: 200;
            position: relative;
            color: #fff;
            background-color: #524F77;
            cursor: pointer;
            vertical-align: top;
            text-align-last: center;
        }

        /*
                .form-search-btn:hover {
                    background-color: #6B8299;
                    border-color: #6B8299;
                }
        */

        .share-url-btn {
            font-size: 12px;
            border: none #FAFAFA;
            color: #486171;
            background-color: #FAFAFA;
            outline: none;
            vertical-align: middle;
            cursor: copy;
        }

        /*
                .share-url-btn:hover {
                    background-color: #ddd;
                }
        */

        .btn-primary.btn-ghost {
            border-color: #2196f3;
            color: #2196f3;
        }

        .btn.btn-ghost {
            background-color: transparent;
        }

        .cell > *:last-child {
            margin-bottom: 0;
        }

        .btn-primary {
            color: #fff;
            background-color: #2196f3;
            border-color: #2196f3;
        }

        .btn {
            width: 88px;
            vertical-align: top;
            position: relative;
            border-width: 1px;
            border-style: solid;
            border-radius: 2px;
            outline: none;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            line-height: 1;
            height: 28px;
        }

        .loading {
            display: inline-block;
            overflow: hidden;
            height: 1.3em;
            margin-top: -0.3em;
            line-height: 1.5em;
            vertical-align: text-bottom;
        }

        * {
            box-sizing: border-box;
            text-rendering: geometricPrecision;
        }

        .btn-primary.btn-ghost {
            border-color: #2196f3;
            color: #2196f3;
        }

        .lds-ellipsis {
            display: inline-block;
            position: relative;
            width: 64px;
            height: 64px;
        }

        /*
            spinner
         */
        .lds-ellipsis div {
            position: absolute;
            top: 27px;
            width: 11px;
            height: 11px;
            border-radius: 50%;
            background: #10559A;
            animation-timing-function: cubic-bezier(0, 1, 1, 0);
        }

        .lds-ellipsis div:nth-child(1) {
            left: 6px;
            animation: lds-ellipsis1 0.6s infinite;
        }

        .lds-ellipsis div:nth-child(2) {
            left: 6px;
            animation: lds-ellipsis2 0.6s infinite;
        }

        .lds-ellipsis div:nth-child(3) {
            left: 26px;
            animation: lds-ellipsis2 0.6s infinite;
        }

        .lds-ellipsis div:nth-child(4) {
            left: 45px;
            animation: lds-ellipsis3 0.6s infinite;
        }

        @keyframes lds-ellipsis1 {
            0% {
                transform: scale(0);
            }
            100% {
                transform: scale(1);
            }
        }

        @keyframes lds-ellipsis3 {
            0% {
                transform: scale(1);
            }
            100% {
                transform: scale(0);
            }
        }

        @keyframes lds-ellipsis2 {
            0% {
                transform: translate(0, 0);
            }
            100% {
                transform: translate(19px, 0);
            }
        }

        .table-row-region {
            /*
                        border-left: 1px solid #ccc !important;
            */
            /*
                        border-right: 1px solid #ccc;
            */
        }

        ::-webkit-scrollbar {
            width: 3px;
        }

        ::-webkit-scrollbar-thumb {
            background: #fff;
        }

        .trade-price-high:hover {
            background-color: #f2f2f2;
        }

        .trade-price-low:hover {
            background-color: #f2f2f2;
        }

        .tradeListTableHiddenRow-info {
            height: 40px;
            text-align: right;
            margin-right: 15px;
        }

        .number-text {
            color: #F4806A;
        }
    </style>
    <!-- Dashboard -->
    <style>
        .keen-dashboard {
            background: #f2f2f2;
        }

        /*.keen-dataviz {
            background: #fff;
            border: 1px solid #e7e7e7;
            border-radius: 2px;
            box-sizing: border-box;
        }

        .keen-dataviz-title {
            border-bottom: 1px solid #e7e7e7;
            border-radius: 2px 2px 0 0;
            font-size: 13px;
            padding: 2px 10px 0;
            text-transform: uppercase;
        }

        .keen-dataviz-stage {
            padding: 10px;
        }

        .keen-dataviz-notes {
            background: #fbfbfb;
            border-radius: 0 0 2px 2px;
            border-top: 1px solid #e7e7e7;
            font-family: 'Helvetica Neue', Helvetica, sans-serif;
            font-size: 11px;
            padding: 0 10px;
        }*/

        .keen-dataviz .keen-dataviz-metric {
            border-radius: 2px;
        }

        .keen-dataviz .keen-spinner-indicator {
            border-top-color: rgba(0, 187, 222, .4);
        }

        .keen-dashboard .chart-wrapper {
            background: #fff;
            /*
                        border: 1px solid #e2e2e2;
            */
            border-radius: 3px;
            margin-bottom: 10px;
        }

        .keen-dashboard .chart-wrapper .chart-title {
            border-bottom: 1px solid #d7d7d7;
            color: #666;
            font-size: 14px;
            font-weight: 200;
            padding: 7px 10px 4px;
        }

        .keen-dashboard .chart-wrapper .chart-stage {
            overflow: hidden;
            padding: 5px 10px;
            position: relative;
        }

        .keen-dashboard .chart-wrapper .chart-notes {
            background: #fbfbfb;
            border-top: 1px solid #e2e2e2;
            color: #808080;
            font-size: 12px;
            padding: 8px 10px 5px;
        }

        .keen-dashboard .chart-wrapper .keen-dataviz,
        .keen-dashboard .chart-wrapper .keen-dataviz-title,
        .keen-dashboard .chart-stage .chart-title {
            border: medium none;
        }

    </style>
</head>
<body class="keen-dashboard" style="padding-top: 80px;">

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation"
     style="background-color: #344144 !important; min-height: 20px;">
    <div class="container-fluid">
        <div class="navbar-header">
        <#--
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
        -->
            <a id="shareUrl" data-clipboard-text="sise.me" class="navbar-brand"
               style="cursor: pointer; color: #fff; font-weight: 400; font-size: 14px; height: 30px; padding: 5px 5px 5px 9px;"
            >sise.me</a>
        </div>
    <#--        <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-left">
                    <li><a href="https://open.kakao.com/o/gIaeoV5" target="_blank" style="color: white !important;">오픈채팅방 (기능요청&버그제보)</a></li>
                </ul>
            </div>-->
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12" style="padding-right: 0px !important; padding-left: 0px !important; margin-top: -48px;">
            <div class="chart-wrapper">
            <#--
                            <div class="chart-title">
                                <span style="font-weight:400; color: #273234;">검색조건</span>
                            </div>
            -->
                <div class="chart-stage" style="text-align: center; padding: 0px !important; margin-bottom: -5px;">
                    <div id="grid-1-1" style="margin-bottom: -4px;">
                        <div class="input-form">
                            <div id="inputFields" style="horiz-align: middle;">
                                <input style="display:inline-block;" type="text" id="startDate" class="form-date-input"
                                       value="" onfocus="this.blur()"
                                       readonly>
                                <select class="form-select-input" id="rangeSelect">
                                    <option value="1">최근 1년</option>
                                    <option value="2">최근 2년</option>
                                    <option value="3">최근 3년</option>
                                    <option value="4">최근 4년</option>
                                    <option value="5">최근 5년</option>
                                    <option value="6">최근 6년</option>
                                    <option value="7">최근 7년</option>
                                    <option value="8">최근 8년</option>
                                    <option value="9">최근 9년</option>
                                    <option value="10">최근 10년</option>
                                </select>
                                <select style="display:inline-block;" class="form-select-input" id="sidoSelect">
                                    <option value="42">강원도</option>
                                    <option value="41">경기도</option>
                                    <option value="48">경상남도</option>
                                    <option value="47">경상북도</option>
                                    <option value="29">광주광역시</option>
                                    <option value="27">대구광역시</option>
                                    <option value="30">대전광역시</option>
                                    <option value="26">부산광역시</option>
                                    <option value="11">서울특별시</option>
                                    <option value="36">세종특별시</option>
                                    <option value="31">울산광역시</option>
                                    <option value="28">인천광역시</option>
                                    <option value="46">전라남도</option>
                                    <option value="45">전라북도</option>
                                    <option value="50">제주특별자치도</option>
                                    <option value="44">충청남도</option>
                                    <option value="43">충청북도</option>
                                <#--
                                <#list sidoList as sido>
                                    <option value="${sido.code}">${sido.name}</option>
                                </#list>
-->
                                </select>
                                <select class="form-select-input" id="gunguSelect">
                                    <option value="">전체</option>
                                </select>
                                <select class="form-select-input" id="dongSelect">
                                    <option value="">전체</option>
                                </select>
                                <select class="form-select-input" id="tradeType">
                                    <option value="trades">실거래가</option>
                                    <option value="tickets">분양권</option>
                                </select>
                            <#--
                                                            <select class="form-select-input" id="dongSelect">
                                                                <option value="">실거래가</option>
                                                                <option value="">분양권</option>
                                                            </select>
                            -->
                                <button class="form-search-btn" id="searchButton">검색하기</button>
                                <button class="btn btn-primary btn-ghost" id="loadingButton"
                                        style="display: none;color: #486171;border-color: #486171;width: 93px;">Loading
                                    <span class="loading dots3"></span></button>
                            </div>
                        </div>
                    </div>
                </div>
            <#--                <div class="chart-notes" id="shortUrlForm" style="text-align: center;">
                                <button class="share-url-btn" id="shareUrl" data-clipboard-text="sise.me">sise.me</button>
                            </div>-->
            </div>
        </div>
    </div>
    <p class="small text-muted" style="font-size:12px; margin-top: 10px;">※ 실거래가 정보공유 및 <span style="color: #C70039;">시세미</span> 기능요청 - <a href="https://open.kakao.com/o/gIaeoV5"
                                                                           target="_blank"
                                                                           style="font-weight: 600;font-size: 12px;">오픈채팅방
        바로가기</a></p>
    <hr style="margin-top:14px; border-top: 1px solid #CECECE;"/>

    <#--<div class="row" id="totalRegionCountRow" style="display: '';">
        <div class="col-sm-12">
            <div class="chart-wrapper">
                <div class="chart-title">
                    <span id="totalRegionCountListPeriod" style="font-weight:400;color: #273234;"></span>&nbsp;<span
                        style="font-weight:400;color: #273234;">지역별 거래건수</span><span id="hideRegionTotalCount"
                                                                                     onClick="hideRegionTotalCount()"
                                                                                     style="color:#1D2B1D; font-size:11px; font-weight:600; float: right; cursor: pointer; margin-top:2px;">열기▼</span>
                </div>
                <div id="total-region-count-spinner" style="text-align: center; display:none;">
                    <tr style="display: none; border-bottom: 1px solid #fff; margin:0 auto; /*border-left: 1px solid #fff; *//*border-right: 1px solid #fff;*/">
                        <td colspan="7">
                            <div class="lds-ellipsis">
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                            </div>
                        </td>
                    </tr>
                </div>
                <div id="total-region-count-table" class="chart-stage"
                     style="width: 100%; text-align: center; padding-top: 20px; display: none; overflow-x: auto;">
                    <div>
                        <table id="totalRegionCountListTable">
                            <thead id="totalRegionCountListTableHead">
                            </thead>
                            <tbody id="totalRegionCountListTableBody">
                            </tbody>
                        </table>
                    </div>
                    <div style="float:left;">
                    <span class="small text-muted" style="padding: 2px; background-color:#C8D8A8; font-size:8px; font-weight: 400; float:left; margin-top: 6px;">검색지역</span>
&lt;#&ndash;
                    <span class="small text-muted" style="padding: 2px; background-color:#F2DFDA; font-size:8px; font-weight: 400; float:left; margin-top: 6px; margin-left:4px;">최고건수</span>
                    <span class="small text-muted" style="padding: 2px; background-color:#E0EDE9; font-size:8px; font-weight: 400; float:left; margin-top: 6px; margin-left:4px;">최저건수</span>
&ndash;&gt;
                    <br/>
                        <p class="small text-muted" style="font-size:10px; font-weight: 400; margin-top: 6px;">※모바일에선 좌우 스크롤로 확인하세요</p>
                    </div>
                </div>
                <div class="chart-notes" style="text-align: right;padding-right: 14px;">
                    비교 지역 <span id="totalRegionCount" class="number-text">0</span>개
                </div>
            </div>
        </div>
    </div>-->

    <div class="row" id="tradeLStatsRow" style="display: '';">
        <div class="col-sm-6">
            <div class="chart-wrapper">
                <div class="chart-title">
                    <span id="tradeStatsListPeriod" style="font-weight:400;color: #273234;"></span>&nbsp;<span
                        style="font-weight:400;color: #273234;">평균 가격</span><span id="hideStats" onClick="hideStats()"
                                                                                  style="color:#1D2B1D; font-size:11px; font-weight:600; float: right; cursor: pointer; margin-top:2px;">열기▼</span>
                </div>
                <div id="trade-stats-spinner" style="text-align: center; display:none;">
                    <tr style="display: none; border-bottom: 1px solid #fff; margin:0 auto; /*border-left: 1px solid #fff; *//*border-right: 1px solid #fff;*/">
                        <td colspan="7">
                            <div class="lds-ellipsis">
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                            </div>
                        </td>
                    </tr>
                </div>
                <div id="stats-chart-table" class="chart-stage"
                     style="text-align: center; padding-top: 20px; display: none;">
                <#--
                                    <span style="color: #465B6A;font-weight: 600;">평균실거래가</span>
                -->
                    <div id="chart-trade-stats">
                    </div>
                </div>
                <div class="chart-notes" style="text-align: right;padding-right: 14px;">
                    총 거래 금액 <span id="statsTotalPrice" class="number-text">0</span>원
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="chart-wrapper">
                <div class="chart-title">
                    <span id="tradeAvgListPeriod" style="font-weight:400;color: #273234;"></span>&nbsp;<span
                        style="font-weight:400;color: #273234;">평균 거래건수</span><span id="hideAvg" onClick="hideAvg()"
                                                                                    style="color:#1D2B1D; font-size:11px; font-weight:600; float: right; cursor: pointer; margin-top:2px;">열기▼</span>
                </div>
                <div id="trade-list-spinner" style="text-align: center; display:none;">
                    <tr style="display: none; border-bottom: 1px solid #fff; margin:0 auto; /*border-left: 1px solid #fff; *//*border-right: 1px solid #fff;*/">
                        <td colspan="7">
                            <div class="lds-ellipsis">
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                            </div>
                        </td>
                    </tr>
                </div>
                <div id="avg-chart-table" class="chart-stage"
                     style="text-align: center; padding-top: 20px; display: none;">
                    <div id="chart-trade-stats-count">
                    </div>
                </div>
                <div class="chart-notes" style="text-align: right;padding-right: 14px;">
                    총 거래 <span id="statsTotalCount" class="number-text">0</span>개
                </div>
            </div>
        </div>
    </div>

    <div class="row" id="tradeListRow" style="display:'';">
        <div class="col-sm-12" id="tradeListLayout">
            <div class="chart-wrapper">
                <div class="chart-title">
                    <span id="tradeListPeriod" style="font-weight:400;color: #273234;"></span>&nbsp;<span
                        id="tradeListTableTitle" style="font-weight:400;color: #273234;">실거래가</span><span id="hideChart" onClick="hideChart()" style="color:#1D2B1D; font-size:11px; font-weight:600; float: right; cursor: pointer; margin-top:2px;">접기▲</span>
                </div>
                <div id="trade-chart-table" class="chart-stage" style="display: '';">
                    <div id="tableFilterInputs" style="text-align: center;">
                        <select class="trade-sort-btn" id="tradeSortType">
                            <option value="monthType-asc">일자 ↓</option>
                            <option value="monthType-desc">일자 ↑</option>
                            <option value="regionType-asc">지역 ↓</option>
                            <option value="regionType-desc">지역 ↑</option>
                            <option value="name-asc">아파트명 ↓</option>
                            <option value="name-desc">아파트명 ↑</option>
                            <option value="floor-asc">층 ↓</option>
                            <option value="floor-desc">층 ↑</option>
                            <option value="area-asc">전용면적 ↓</option>
                            <option value="area-desc">전용면적 ↑</option>
                            <option value="mainPrice-asc">실거래가 ↓</option>
                            <option value="mainPrice-desc">실거래가 ↑</option>
                        </select>
                        <select class="trade-sort-btn" id="filterTypeArea">
                            <option value="0">전용면적</option>
                            <option value="1">49m²이하</option>
                            <option value="2">49m²초과~60m²이하</option>
                            <option value="3">60m²초과~85m²이하</option>
                            <option value="4">85m²초과~135m²이하</option>
                            <option value="5">135m²초과</option>
                        </select>
                        <button class="trade-filter-btn" id="filterTypeNew">신규등록</button>
                        <button class="trade-filter-btn" id="filterTypeMaxPrice">신고가</button>
                    </div>
                    <div id="scrollTable">
                        <table id="maxTradeListTable">
                            <thead id="maxTradeListTableHead">
                            <tr>
                                <th style="cursor: default;">일자</th>
                                <th style="cursor: default; /*border-left: 1px solid #ccc;*/" class="table-th-region">지역
                                </th>
                                <th style="cursor: default;">준공</th>
                                <th style="cursor: default;">아파트명</th>
                                <th style="cursor: default;">층</th>
                                <th style="cursor: default;">전용면적</th>
                                <th style="cursor: default; border-right: 1px solid #ddd;">실거래가<br/><span
                                        style="font-size: 10px;color: #2f4f4f; font-weight: 400;">(전고가)</span>
                                </th>
                            </tr>
                            </thead>
                            <tbody id="maxTradeListTableBody">
                            </tbody>
                            <tr id="spinner-row"
                                style="display: none; border-bottom: 1px solid #fff; /*border-left: 1px solid #fff; *//*border-right: 1px solid #fff;*/">
                                <td colspan="7">
                                    <div class="lds-ellipsis">
                                        <div></div>
                                        <div></div>
                                        <div></div>
                                        <div></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="chart-notes" style="text-align: right;">
                    신규등록 <span id="newTradeListSize" class="number-text">0</span>개, 신고가 <span id="maxTradeListSize"
                                                                                              class="number-text">0</span>개,
                    총 <span id="tradeListSize" class="number-text">0</span>개
                </div>
            </div>
        </div>
    </div>

</div>

<input type="hidden" value="${share}" id="isShare">
<input type="hidden" value="${sidoCode}" id="shareSidoCode">
<input type="hidden" value="${gunguCode}" id="shareGunguCode">
<input type="hidden" value="${dongCode}" id="shareDongCode">
<input type="hidden" value="${startDate}" id="shareStartDate">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-ui@1.12.1/ui/widget.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-ui-monthpicker@1.0.3/jquery.ui.monthpicker.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/clipboard@2/dist/clipboard.min.js"></script>
<script src="https://d26b395fwzu5fz.cloudfront.net/keen-dataviz-1.1.3.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/holder/2.3.2/holder.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/js/bootstrap.min.js"
        type="text/javascript"></script>
<!-- Load D3 -->
<script src="https://d3js.org/d3.v5.min.js"></script>
<script src="https://naver.github.io/billboard.js/release/latest/dist/billboard.min.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.22.2/moment.min.js"></script>
<script>
    Holder.add_theme("white", {background: "#fff", foreground: "#a7a7a7", size: 10});
</script>
<script>
    var initSearch = false;
    var sync = false;
    var maxTradeListType = false;
    var newTradeListType = false;
    var pageNumber = 0;
    var totalPages = 0;
    var totalElements = 0;
    var currentElements = 0;
    var size = 30;
    var isMaxTradeView = true;
    var isHideStats = false;
    var isHideAvg = false;
    var isHideChart = true;
    var isHideRegionTotalCount = false;

    $(document).ready(function () {
        $(window).scroll(function () {
            if (($(window).scrollTop() + 1800) >= $(document).height() - $(window).height()) {
                if (pageNumber !== 0 && totalPages !== 0) {
                    if (pageNumber < totalPages) {
                        getTradePage();
                    }
                }
            }
        });
        initSidoSelectComponent();

        /*
            1. input date 세팅
         */
        initDateComponent();

        /*
            2. 지역 세팅
         */
        initRegionComponent();

        /*
            3. 검색 세팅
         */
        initSearchComponent();

        /*
            5. 클립보드
         */
        initClipboardComponent();

        $('#tradeSortType').change(() => {
            pageNumber = 0;
            totalPages = 0;
            totalElements = 0;
            currentElements = 0;
            $('#maxTradeListTableBody').empty();
            $('#tradeListTableBody').empty();
            newTradeListType = false;
            $('#filterTypeNew').css('background-color', '#524F77');
            $('#filterTypeNew').css('border-color', '#524F77');
            $('#filterTypeNew').text('신규등록');
            maxTradeListType = false;
            $('#filterTypeMaxPrice').css('background-color', '#524F77');
            $('#filterTypeMaxPrice').css('border-color', '#524F77');
            $('#filterTypeMaxPrice').text('신고가');
            $('#tradeListTableTitle').text(getTradeTypeName());
            getTradePage();
        })

        $('#filterTypeArea').change(() => {
            pageNumber = 0;
            totalPages = 0;
            totalElements = 0;
            currentElements = 0;
            $('#maxTradeListTableBody').empty();
            $('#tradeListTableBody').empty();
            newTradeListType = false;
            $('#filterTypeNew').css('background-color', '#524F77');
            $('#filterTypeNew').css('border-color', '#524F77');
            $('#filterTypeNew').text('신규등록');
            maxTradeListType = false;
            $('#filterTypeMaxPrice').css('background-color', '#524F77');
            $('#filterTypeMaxPrice').css('border-color', '#524F77');
            $('#filterTypeMaxPrice').text('신고가');
            $('#tradeListTableTitle').text(getTradeTypeName());
            let sidoSelectVal = $('#sidoSelect').val();
            let gunguSelectVal = $('#gunguSelect').val();
            let dongSelectVal = $('#dongSelect').val();
            getTradePage();
        })
    });

    hideStats = () => {
        isHideStats = !isHideStats;
        if (isHideStats) {
            $('#hideStats').text('접기▲');
            $('#stats-chart-table').css('display', '');
            /*
                        $('#chart-trade-stats').css('display', '');
                        $('#chart-trade-stats-count').css('display', '');
            */
        }
        else {
            $('#hideStats').text('열기▼');
            $('#stats-chart-table').css('display', 'none');
            /*
                        $('#chart-trade-stats').css('display', 'none');
                        $('#chart-trade-stats-count').css('display', 'none');
            */
        }
    };

    hideRegionTotalCount = () => {
        isHideRegionTotalCount = !isHideRegionTotalCount;
        if (isHideRegionTotalCount) {
            $('#hideRegionTotalCount').text('접기▲');
            $('#total-region-count-table').css('display', '');
            /*
                        $('#chart-trade-stats').css('display', '');
                        $('#chart-trade-stats-count').css('display', '');
            */
        }
        else {
            $('#hideRegionTotalCount').text('열기▼');
            $('#total-region-count-table').css('display', 'none');
            /*
                        $('#chart-trade-stats').css('display', 'none');
                        $('#chart-trade-stats-count').css('display', 'none');
            */
        }
    };

    hideAvg = () => {
        isHideAvg = !isHideAvg;
        if (isHideAvg) {
            $('#hideAvg').text('접기▲');
            $('#avg-chart-table').css('display', '');
            /*
                        $('#chart-trade-stats').css('display', '');
                        $('#chart-trade-stats-count').css('display', '');
            */
        }
        else {
            $('#hideAvg').text('열기▼');
            $('#avg-chart-table').css('display', 'none');
            /*
                        $('#chart-trade-stats').css('display', 'none');
                        $('#chart-trade-stats-count').css('display', 'none');
            */
        }
    };

    hideChart = () => {
        isHideChart = !isHideChart;
        if (isHideChart) {
            $('#hideChart').text('접기▲');
            $('#trade-chart-table').css('display', '');
            /*
                        $('#chart-trade-stats').css('display', '');
                        $('#chart-trade-stats-count').css('display', '');
            */
        }
        else {
            $('#hideChart').text('열기▼');
            $('#trade-chart-table').css('display', 'none');
            /*
                        $('#chart-trade-stats').css('display', 'none');
                        $('#chart-trade-stats-count').css('display', 'none');
            */
        }
    };

    initDateComponent = () => {
        let options1 = {
            minYear: 2014,
            maxYear: new Date().getFullYear(),
            dateFormat: 'yy년 mm월',
            pattern: 'mm월yyyy년',
            monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
        };
        let date = new Date();
        $('#startDate').val(date.getFullYear() + '년 ' + (date.getMonth() + 1) + '월');

        $('#startDate').monthpicker(options1);
        if (isShareUrl()) {
            let shareStartDate = $('#shareStartDate').val();
            $('#startDate').val(shareStartDate.substr(0, 4) + '년 ' + shareStartDate.substr(4, 2) + '월');
        }
    };

    initRegionComponent = () => {
        $('#sidoSelect').change((evt) => {
            let sidoCode = $('#sidoSelect').val();
            initGunguSelectComponent(sidoCode);
        });

        $('#gunguSelect').change((evt) => {
            let gunguCode = $('#gunguSelect').val();
            initDongSelectComponent(gunguCode);
        });

        $('#dongSelect').change((evt) => {
            let dongCode = $('#dongSelect').val();
        });
    };

    initSidoSelectComponent = () => {
        $('#sidoSelect').val('11');
        let isShare = $('#isShare').val();
        let shareSidoCode = $('#shareSidoCode').val();
        if (isShare === 'true') {
            $('#sidoSelect').val(shareSidoCode);
        }
        initGunguSelectComponent($('#sidoSelect').val());
    };

    initGunguSelectComponent = (sidoCode) => {
        $.get('/dev/api/v1/regions/type/gungu/like/' + sidoCode + '?ts=' + new Date().getTime(), {}).done(function (data) {
            defaultSelectComponent('#gunguSelect');
            if (data.length === 0) {
                return;
            }
            data.map(x => {
                $('#gunguSelect').append(new Option(x.name, x.code));
            });
            // 세종시 예외처리
            if (sidoCode === '36') {
                $('#gunguSelect').val('36110');
            }

            let isShare = $('#isShare').val();
            let shareGunguCode = $('#shareGunguCode').val();
            if (isShare === 'true') {
                $('#gunguSelect').val(shareGunguCode);
            }
            initDongSelectComponent($('#gunguSelect').val());
        }).fail(function (data) {
            alert('구 목록 호출 실패');
        });
    };

    initDongSelectComponent = (gunguCode) => {
        if (gunguCode === '') {
            defaultSelectComponent('#dongSelect');
            let isShare = $('#isShare').val();
            let shareDongCode = $('#shareDongCode').val();
            if (isShare === 'true') {
                $('#dongSelect').val(shareDongCode);
                getTradePage();
                getTotalRegionCount();
                getStats();

            }
            return;
        }
        $.get('/dev/api/v1/regions/type/dong/like/' + gunguCode + '?ts=' + new Date().getTime(), {}).done(function (data) {
            defaultSelectComponent('#dongSelect');
            if (data.length === 0) {
                return;
            }
            data.map(x => {
                $('#dongSelect').append(new Option(x.name, x.code));
            });
            let isShare = $('#isShare').val();
            let shareDongCode = $('#shareDongCode').val();
            if (isShare === 'true') {
                $('#dongSelect').val(shareDongCode);
                getTradePage();
                getTotalRegionCount();
                getStats();
            }
        });
    };

    defaultSelectComponent = (id) => {
        $(id).empty();
        $(id).append(new Option('전체', ''));
    };

    initSearchComponent = () => {
        $('#searchButton').click(() => {
            pageNumber = 0;
            totalPages = 0;
            totalElements = 0;
            currentElements = 0;
            $('#maxTradeListTableBody').empty();
            $('#tradeListTableBody').empty();
            newTradeListType = false;
            $('#filterTypeNew').css('background-color', '#524F77');
            $('#filterTypeNew').css('border-color', '#524F77');
            $('#filterTypeNew').text('신규등록');
            maxTradeListType = false;
            $('#filterTypeMaxPrice').css('background-color', '#524F77');
            $('#filterTypeMaxPrice').css('border-color', '#524F77');
            $('#filterTypeMaxPrice').text('신고가');
            $('#tradeListTableTitle').text(getTradeTypeName());
            getTradePage();
            getTotalRegionCount();
            getStats();
        });
        $('#loadingButton').click(() => {
            alert('검색중입니다');
        });
    };

    getStats = () => {
        $('#trade-stats-spinner').css('display', '');
        $('#trade-list-spinner').css('display', '');

        let startDateVal = getStartDateValue();
        let sidoSelectVal = $('#sidoSelect').val();
        let gunguSelectVal = $('#gunguSelect').val();
        let dongSelectVal = $('#dongSelect').val();
        let rangeSelectVal = $('#rangeSelect').val();

        let selectedRegionCode = sidoSelectVal;
        if (selectedRegionCode === '') {
            alert('검색 조건이 잘못되었습니다');
        }
        else if (dongSelectVal !== '') {
            selectedRegionCode = dongSelectVal;
        }
        else if (gunguSelectVal !== '') {
            selectedRegionCode = gunguSelectVal;
        }

        let tradeType = $('#tradeType').val();

        let url = '/dev/api/v1/' + tradeType + '/max/regions/' + selectedRegionCode + '/stats';
        $.get(url, {
            date: startDateVal,
            rangeType: rangeSelectVal
        }).done(function (data) {
            let type1List = data.type1TradeStatsList;
            let type2List = data.type2TradeStatsList;
            let type3List = data.type3TradeStatsList;
            let type4List = data.type4TradeStatsList;
            let type5List = data.type5TradeStatsList;

            if (type1List.length + type2List.length + type3List.length + type4List.length + type5List.length === 0) {
                $('#statsTotalPrice').text(0);
                $('#statsTotalCount').text(0);
                $('#chart-trade-stats').empty();
                $('#chart-trade-stats-count').empty();
                $('#tradeLStatsRow').css('display', 'none');
                return;
            }

            $('#tradeLStatsRow').css('display', '');
            $('#tradeStatsListPeriod').text('');
            $('#totalCountListPeriod').text('');
            $('#tradeAvgListPeriod').text('');

            let totalList = type1List.concat(type2List).concat(type3List).concat(type4List).concat(type5List);
            let totalPriceList = totalList.map(x => x.sumMainPrice);
            let totalCountList = totalList.map(x => x.count);
            let statsTotalPrice = totalPriceList.reduce((a, b) => a + b);
            let statsTotalCount = totalCountList.reduce((a, b) => a + b);
            $('#statsTotalPrice').text(setWon(statsTotalPrice + '0000'));
            $('#statsTotalCount').text(numberWithCommas(statsTotalCount));

            let totalDateList = type1List.map(x => x.date.slice(0, 4) + '-' + x.date.slice(4, 8) + '-01');
            if (totalDateList.length === 0) {
                totalDateList = type2List.map(x => x.date.slice(0, 4) + '-' + x.date.slice(4, 8) + '-01');
            }
            if (totalDateList.length === 0) {
                totalDateList = type3List.map(x => x.date.slice(0, 4) + '-' + x.date.slice(4, 8) + '-01');
            }
            if (totalDateList.length === 0) {
                totalDateList = type4List.map(x => x.date.slice(0, 4) + '-' + x.date.slice(4, 8) + '-01');
            }
            if (totalDateList.length === 0) {
                totalDateList = type5List.map(x => x.date.slice(0, 4) + '-' + x.date.slice(4, 8) + '-01');
            }
            totalDateList.unshift('x');

            let type1PriceList = type1List.map(x => x.sumMainPrice !== 0 ? (x.sumMainPrice / x.count).toFixed(0) : null);
            type1PriceList.unshift('초소형(49m²이하)');
            let type2PriceList = type2List.map(x => x.sumMainPrice !== 0 ? (x.sumMainPrice / x.count).toFixed(0) : null);
            type2PriceList.unshift('소형(49m²초과~60m²이하)');
            let type3PriceList = type3List.map(x => x.sumMainPrice !== 0 ? (x.sumMainPrice / x.count).toFixed(0) : null);
            type3PriceList.unshift('중형(60m²초과~85m²이하)');
            let type4PriceList = type4List.map(x => x.sumMainPrice !== 0 ? (x.sumMainPrice / x.count).toFixed(0) : null);
            type4PriceList.unshift('중대형(85m²초과~135m²이하)');
            let type5PriceList = type5List.map(x => x.sumMainPrice !== 0 ? (x.sumMainPrice / x.count).toFixed(0) : null);
            type5PriceList.unshift('대형(135m²초과)');

            let type0PriceList = [];
            for (let i = 1; i < totalDateList.length; i++) {
                let sum = 0;
                let sumCount = 0;
                if (type1PriceList[i]) {
                    sum += type1PriceList[i] * 1;
                    sumCount++;
                }
                if (type2PriceList[i]) {
                    sum += type2PriceList[i] * 1;
                    sumCount++;
                }
                if (type3PriceList[i]) {
                    sum += type3PriceList[i] * 1;
                    sumCount++;
                }
                if (type4PriceList[i]) {
                    sum += type4PriceList[i] * 1;
                    sumCount++;
                }
                if (type5PriceList[i]) {
                    sum += type5PriceList[i] * 1;
                    sumCount++;
                }
                sum = sum !== 0 ? sum / sumCount : 0;
                type0PriceList.push(sum.toFixed(0));
            }
            type0PriceList.unshift('전체평균');

            let type1CountList = type1List.map(x => x.count);
            type1CountList.unshift('초소형(49m²이하)');
            let type2CountList = type2List.map(x => x.count);
            type2CountList.unshift('소형(49m²초과~60m²이하)');
            let type3CountList = type3List.map(x => x.count);
            type3CountList.unshift('중형(60m²초과~85m²이하)');
            let type4CountList = type4List.map(x => x.count);
            type4CountList.unshift('중대형(85m²초과~135m²이하)');
            let type5CountList = type5List.map(x => x.count);
            type5CountList.unshift('대형(135m²초과)');

            let type0CountList = [];
            for (let i = 1; i < totalDateList.length; i++) {
                let sum = 0;
                if (type1CountList[i]) {
                    sum += type1CountList[i] * 1;
                }
                if (type2CountList[i]) {
                    sum += type2CountList[i] * 1;
                }
                if (type3CountList[i]) {
                    sum += type3CountList[i] * 1;
                }
                if (type4CountList[i]) {
                    sum += type4CountList[i] * 1;
                }
                if (type5CountList[i]) {
                    sum += type5CountList[i] * 1;
                }
                type0CountList.push(sum);
            }
            type0CountList.unshift('총 거래건수');

            let columns = [];
            columns.push(totalDateList);
            columns.push(type1PriceList);
            columns.push(type2PriceList);
            columns.push(type3PriceList);
            columns.push(type4PriceList);
            columns.push(type5PriceList);
            columns.push(type0PriceList);

            let countColumns = [];
            countColumns.push(totalDateList);
            countColumns.push(type1CountList);
            countColumns.push(type2CountList);
            countColumns.push(type3CountList);
            countColumns.push(type4CountList);
            countColumns.push(type5CountList);
            countColumns.push(type0CountList);

            var chart1 = bb.generate({
                padding: {
                    right: 20,
                    left: 50,
                    bottom: 10
                },
                data: {
                    x: 'x',
                    columns: columns,
                    type: 'line',
                    types: {
                        '전체평균': 'line'
                    },
                    colors: {
                        '초소형(49m²이하)': '#7F0FB1',
                        '소형(49m²초과~60m²이하)': '#0D40B3',
                        '중형(60m²초과~85m²이하)': '#0C88B4',
                        '중대형(85m²초과~135m²이하)': '#20B40C',
                        '대형(135m²초과)': '#85CA08',
                        '전체평균': '#4B4B4B'
                    }
                },
                legend: {
                    show: true,
                },
                tooltip: {
                    format: {
                        value: function (value, ratio, id) {
                            return numberWithCommas(value);
                        }
                    }
                },
                axis: {
                    x: {
                        type: "timeseries",
                        tick: {
                            format: "%Y-%m"
                        }
                    }
                },
                grid: {
                    y: {
                        show: true
                    }
                },
                point: {
                    r: 1,
                    focus: {
                        expand: {
                            enabled: true,
                            r: 2
                        }
                    },
                    select: {
                        r: 2
                    }
                },
                bindto: '#chart-trade-stats'
            });

            var chart2 = bb.generate({
                padding: {
                    top: 20,
                    right: 20,
                    left: 50,
                    bottom: 10
                },
                data: {
                    x: 'x',
                    columns: countColumns,
                    types: {
                        '초소형(49m²이하)': 'bar',
                        '소형(49m²초과~60m²이하)': 'bar',
                        '중형(60m²초과~85m²이하)': 'bar',
                        '중대형(85m²초과~135m²이하)': 'bar',
                        '대형(135m²초과)': 'bar'
                    },
                    colors: {
                        '초소형(49m²이하)': '#7F0FB1',
                        '소형(49m²초과~60m²이하)': '#0D40B3',
                        '중형(60m²초과~85m²이하)': '#0C88B4',
                        '중대형(85m²초과~135m²이하)': '#20B40C',
                        '대형(135m²초과)': '#85CA08',
                        '총 거래건수': '#4B4B4B',
                    },
                    /*                    labels: {
                                            format: {
                                                '총 거래건수': function(x) {
                                                    return numberWithCommas(x) + '건';
                                                }
                                            },
                                            position: {
                                                x: -25,
                                                y: 5
                                            }
                                        }*/
                },
                legend: {
                    show: true
                },
                tooltip: {
                    format: {
                        value: function (value, ratio, id) {
                            return numberWithCommas(value);
                        }
                    }
                },
                axis: {
                    x: {
                        type: "timeseries",
                        tick: {
                            format: "%Y-%m"
                        }
                    }
                },
                grid: {
                    y: {
                        show: true
                    }
                },
                point: {
                    r: 1,
                    focus: {
                        expand: {
                            enabled: true,
                            r: 2
                        }
                    },
                    select: {
                        r: 2
                    }
                },
                bindto: '#chart-trade-stats-count'
            });
            $('#tradeStatsListPeriod').text($('#startDate').val() + '부터 최근 ' + $('#rangeSelect').val() + '년');
            $('#tradeAvgListPeriod').text($('#startDate').val() + '부터 최근 ' + $('#rangeSelect').val() + '년');
            $('#trade-list-spinner').css('display', 'none');
            $('#trade-stats-spinner').css('display', 'none');
        }).fail(function (data) {
            $('#trade-list-spinner').css('display', 'none');
            $('#trade-stats-spinner').css('display', 'none');
        });
    };

    getTotalRegionCount = () => {
        $('#total-region-count-spinner').css('display', '');
        let startDateVal = getStartDateValue();
        let sidoSelectVal = $('#sidoSelect').val();
        let gunguSelectVal = $('#gunguSelect').val();
        let dongSelectVal = $('#dongSelect').val();
        let rangeSelectVal = $('#rangeSelect').val();

        let selectedRegionCode = sidoSelectVal;
        if (selectedRegionCode === '') {
            alert('검색 조건이 잘못되었습니다');
        }
        else if (dongSelectVal !== '') {
            selectedRegionCode = dongSelectVal;
        }
        else if (gunguSelectVal !== '') {
            selectedRegionCode = gunguSelectVal;
        }

        $('#totalRegionCountListTableHead').empty();
        $('#totalRegionCountListTableBody').empty();
        $('#totalRegionCountListPeriod').text($('#startDate').val() + '부터 최근 ' + $('#rangeSelect').val() + '년');
        let tradeType = $('#tradeType').val();

        let url = '/dev/api/v1/' + tradeType + '/max/regions/' + selectedRegionCode + '/count';
        $.get(url, {
            date: startDateVal,
            rangeType: rangeSelectVal
        }).done(function (data) {
            let result = '';
            let regionNameList = data.sort((a, b) => ( a.name < b.name ) ? -1 : ( a.name == b.name ) ? 0 : 1);
            $('#totalRegionCount').text(regionNameList.length);
            regionNameList.forEach((x, idx) => {
                let color = x.code === selectedRegionCode ? '#C8D8A8' : '';
                result += '<tr>';
                result += '<td data-label="지역" style="min-width:80px; background-color:' + color + ';">' + x.name + '</td>';
                let dateList = x.tradeStatsList.sort((a, b) => b.date * 1 - a.date * 1);
                if (idx === 0) {
                    let thead = '<tr><th style="cursor: default; font-size: 10px;color: #1D2627; font-weight: 400;">지역명</th>';
                    dateList.forEach(y => {
                        thead += '<th style="cursor: default; font-size: 10px;color: #1D2627; font-weight: 400;">' + y.date.substring(0, 4) + '.' + y.date.substring(4, 6) + '</th>'
                    });
                    thead += '</tr>';
                    $('#totalRegionCountListTableHead').append(thead);
                }
                dateList.forEach((y) => {
                    result += '<td style="min-width:60px; background-color:' + color + ';">' + numberWithCommas(y.count) + '</td>';
                });
                result += '</tr>';
            });
            $('#totalRegionCountListTableBody').append(result);
            $('#totalRegionCountRow').css('display', '');
            $('#total-region-count-spinner').css('display', 'none');
        }).fail(function (data) {
            $('#total-region-count-spinner').css('display', 'none');
        });
        /*
                $.get(url, {
                    date: startDateVal,
                    rangeType: rangeSelectVal
                }).done(function (data) {
                    console.log(data);
                    $('#tradeAvgListPeriod').text($('#startDate').val() + '부터 최근 ' + $('#rangeSelect').val() + '년');
                }).fail(function (data) {
                });
        */
    };

    getTradePage = () => {
        if (!initSearch) {
            initSearch = true;
        }
        if (sync) {
            return;
        }
        if (pageNumber > 1 && pageNumber === totalPages) {
            return;
        }
        $('#spinner-row').css('display', '');
        $('#filterTypeMaxPrice').off();
        $('#filterTypeNew').off();
        sync = true;
        let startDateVal = getStartDateValue();
        let sidoSelectVal = $('#sidoSelect').val();
        let gunguSelectVal = $('#gunguSelect').val();
        let dongSelectVal = $('#dongSelect').val();
        let tradeSortTypeVal = $('#tradeSortType').val();
        let filterTypeArea = $('#filterTypeArea').val();
        let rangeSelectVal = $('#rangeSelect').val();

        let selectedRegionCode = sidoSelectVal;
        let searchType = 'SIDO';
        if (selectedRegionCode === '') {
            alert('검색 조건이 잘못되었습니다');
        }
        else if (dongSelectVal !== '') {
            selectedRegionCode = dongSelectVal;
            searchType = 'DONG';
        }
        else if (gunguSelectVal !== '') {
            selectedRegionCode = gunguSelectVal;
            searchType = 'GUNGU';
        }

        let tradeType = $('#tradeType').val();

        let url = '/dev/api/v1/' + tradeType + '/max/regions/' + selectedRegionCode + '/pages/' + pageNumber + '/size/' + size;
        $.get(url, {
            date: startDateVal,
            searchType: searchType,
            sortType: tradeSortTypeVal,
            areaType: filterTypeArea,
            rangeType: rangeSelectVal
        }).done(function (data) {
            generateShortUrl(sidoSelectVal, gunguSelectVal, dongSelectVal, getStartDateValue());
            $('#tradeListRow').css('display', '');
            $('#spinner-row').css('display', 'none');
            pageNumber = data.pageNumber + 1;
            totalElements = data.totalElements;
            totalPages = data.totalPages;
//            generateShortUrl(sidoSelectVal, gunguSelectVal, dongSelectVal, getStartDateValue());
            if (data.contents.length === 0) {
                $('#tradeListSize').text(0);
                $('#maxTradeListSize').text(0);
                $('#newTradeListSize').text(0);
                sync = false;
                $('#maxTradeListTableBody').append('<tr><td colspan="7">결과 없음</td></tr>');
                return false;
            }
            $('#isShare').val('false');
            appendData(data.contents);
            if ($("body").height() < $(window).height()) {
                if (pageNumber !== 0 && totalPages !== 0) {
                    if (pageNumber < totalPages) {
                        getTradePage();
                    }
                }
            }
        }).fail(function (data) {
            $('#spinner-row').css('display', 'none');
            alert('서버 요청에 실패하였습니다');
            $('#tradeListSize').text(0);
            $('#maxTradeListSize').text(0);
            $('#newTradeListSize').text(0);
            //offSpinner();
            $('#maxTradeListTableBody').append('<tr><td colspan="7">결과 없음</td></tr>');
        });
    };

    maxTradeTableRow = (data, isMax, idx, type) => {
        let item = data.trade;
        let pastTradeList = data.pastTradeList;
        let result = '<tr class = "' + ((item.price * 1 >= item.pastMaxPrice * 1) ? 'trade-row trade-price-high' : 'trade-row trade-price-low') + (item.isNewData ? ' trade-new-data' : '') + '" style="cursor: pointer;display:' + (isMaxTradeView ? '' : 'none') + ';background-color:' + ((item.isNewData) ? '#FFEAEA' : '') + ';" onclick=showTradeListTableHiddenRow(' + idx + ',[' + pastTradeList.map(x => x.price) + '],[' + pastTradeList.map(x => '\'' + x.date.slice(0, 4) + '-' + x.date.slice(4, 8) + /*'-' + x.dateName.split('~')[0] + */'\'') + '],"' + type + '","' + item.name + '","' + item.floor + '")>' +
                '<td data-label="">' + item.dateName + '<br/>' + ((item.isNewData) ? '<span style="color:#F15F5F;font-size:6px;"> (new)</span>' : '') + '</td>' +
                '<td data-label="지역"">' + item.regionName + '</td>' +
                '<td data-label="준공" style="max-width:80px;">' + item.since + '</td>' +
                '<td data-label="아파트" style="max-width:80px;">' + item.name + '</td>' +
                '<td data-label="층">' + item.floor + '</td>' +
                /*
                                '<td data-label="면적">' + calculateArea(item.area) + '평<br/><span style="font-size: 10px;font-weight:200;color: #2f4f4f;">(' + item.area + 'm²)</span></td>' +
                */
                '<td data-label="전용면적">' + (item.area * 1).toFixed(3) + 'm²</span></td>' +
                '<td data-label="실거래가" style="border-right: 1px solid #ddd;">' + getPrice(item, isMax) + '</td>' +
                '</tr>';

        if (data.pastTradeList.length > 1) {
            result += '<tr class="tradeListTableHiddenRow tradeListTableHiddenRow-' + idx + '" style="display: none;background-color:#fbfbfb;"><td colSpan=7 style="padding:0px !important;"><div id="tradeListTableHiddenRow-lineChart-' + type + '-' + idx + '"></div><div class="tradeListTableHiddenRow-info"><span>총 <span style="color:#486171;font-weight:800;" id="tradeListTableHiddenRow-info-count-' + type + '-' + idx + '"></span>건 / 최고가 <span style="color: #BE0012;font-weight: 800;" id="tradeListTableHiddenRow-info-high-' + type + '-' + idx + '"></span> / 최저가 <span style="color:#1B62A5; font-weight:800;" id="tradeListTableHiddenRow-info-low-' + type + '-' + idx + '"></span></div>';
            result += '</td></tr>';
        }
        else {
            result += '<tr class="tradeListTableHiddenRow tradeListTableHiddenRow-' + idx + '" style="display: none;background-color:#fbfbfb;"><td colSpan=7 style="padding:0px !important; height: 50px;">이전 거래내역이 없습니다</td></tr>'
        }
        return result;
    };

    showTradeListTableHiddenRow = (idx, priceList, dateList, type, name, floor) => {
        let display = $('.tradeListTableHiddenRow-' + idx).css('display');
        $('.tradeListTableHiddenRow').css('display', 'none');
        $('.tradeListTableHiddenRow-' + idx).css('display', display === 'none' ? '' : 'none');

        let tempPriceDateList = [];
        var max = priceList.reduce(function (a, b) {
            return Math.max(a, b);
        });
        var min = priceList.reduce(function (a, b) {
            return Math.min(a, b);
        });
        $('#tradeListTableHiddenRow-info-high-' + type + '-' + idx).text(numberWithCommas(max));
        $('#tradeListTableHiddenRow-info-low-' + type + '-' + idx).text(numberWithCommas(min));

        priceList.forEach((price, idx) => {
            let tmp = {};
            tmp.price = price;
            tmp.date = dateList[idx];
            tempPriceDateList.push(tmp);
        });

        let tempPriceDateMap = groupBy(tempPriceDateList, tmp => tmp.date);

        let maxSize = 0;
        let tempDateList = [];

        /*        // Fixme 날짜 데이터 비는 경우 채워주는 로직
                let endDate = moment(tempDateList[tempDateList.length-1]);
                let startDate = moment(endDate).subtract(1, 'years');
                while(!startDate.isSame(endDate)) {
                    if(!tempPriceDateMap.get(startDate.format("YYYY-MM"))) {
                        tempPriceDateMap.set(startDate.format("YYYY-MM"), []);
                    }
                    startDate = startDate.add(1, 'months');
                }*/

        tempPriceDateMap.forEach((v, k) => {
            maxSize = maxSize > v.length ? maxSize : v.length;
            tempDateList.push(k);
        });

        tempPriceDateMap.forEach((v, k) => {
            for (let i = v.length; i < maxSize; i++) {
                v.push({price: 0, date: k});
            }
        });

        let avgData = [];
        avgData.push('평균가');
        let reducer = (accumulator, currentValue) => accumulator + currentValue;
        tempPriceDateMap.forEach((v, k) => {
            let validCount = v.filter(x => x.price !== 0).length;
            if (validCount !== 0) {
                avgData.push(((v.map(x => x.price).reduce(reducer)) / validCount).toFixed(0));
            }
            else {
                avgData.push('');
            }
        });

        let tempList = [];
        let tempKey = [];
        for (let i = 0; i < maxSize; i++) {
            let tmpResult = [];
            tmpResult.push(name + ' / ' + (i + 1) + '번째');
            tempKey.push(name + ' / '  + (i + 1) + '번째');
            tempDateList.forEach((k) => {
                tmpResult.push(tempPriceDateMap.get(k)[i].price);
            });
            tempList.push(tmpResult);
        }
        console.log(tempList);

        let columns = [];
        let xColumn = [];
        xColumn.push('x');
        tempDateList.forEach(x => xColumn.push(x + '-01'));
        columns.push(xColumn);
        columns.push(avgData);
        tempList.map(x => {
            columns.push(x);
        });

        let groups = [];
        groups.push(tempKey);

        let minValue = 0;
        avgData.forEach((x, idx) => {
            if (idx !== 0) {
                minValue += x * 1;
            }
        });
        minValue = ((minValue / avgData.length) * 0.8).toFixed(0);

        $('#tradeListTableHiddenRow-info-count-' + type + '-' + idx).text(xColumn.length - 1);

        if (priceList.length > 1) {
            let chart = bb.generate({
                padding: {
                    right: 20,
                    left: 50,
                    top: 50,
                    bottom: 10
                },
                data: {
                    x: 'x',
                    columns: columns,
                    type: "scatter",
                    types: {
                        평균가: 'line'
                    }
                    /*
                                        labels: true,
                                        groups: groups
                    */
                },
                legend: {
                    show: false,
                },
                tooltip: {
                    format: {
                        value: function (value, ratio, id) {
                            return numberWithCommas(value);
                        }
                    }
                },
                axis: {
                    x: {
                        type: "timeseries",
                        tick: {
                            count: 12,
                            format: "%Y-%m"
                        }
                    },
                    y: {
                        min: minValue
                    }
                },
                grid: {
                    y: {
                        show: true
                    }
                },
                bindto: '#tradeListTableHiddenRow-lineChart-' + type + '-' + idx
            });
        }
    };

    getPrice = (item, isMax) => {
        return isMax ? '<span style="color:#CB1115;font-weight:800;">' + (item.price * 1 >= item.pastMaxPrice * 1 ? '' : '') + numberWithCommas(item.price) + '</span><br/><span style="font-size:8px!important;color:#2f4f4f;">(' + ((item.pastMaxPrice * 1) === 0 ? '-' : numberWithCommas(item.pastMaxPrice)) + ')</span>' : '<span style="color:' + (item.price * 1 >= item.pastMaxPrice * 1 ? '#CB1115' : '#273234') + ';font-weight:800;">' + (item.price * 1 >= item.pastMaxPrice * 1 ? '' : '') + numberWithCommas(item.price) + '</span><br/><span style="font-size:8px!important;color:#2f4f4f;">(' + ((item.pastMaxPrice * 1) === 0 ? '-' : numberWithCommas(item.pastMaxPrice)) + ')</span>';
    };

    generateShortUrl = (sidoCode, gunguCode, dongCode, startDate) => {
        let url = 'https://sise.me?sidoCode=' + sidoCode + '&gunguCode=' + gunguCode + '&dongCode=' + dongCode + '&date=' + startDate;
        $.ajaxSetup({
            'beforeSend': function (xhr) {
                xhr.overrideMimeType('application/json; charset=utf-8');
            },
        });

        postJSON('/dev/api/v1/urls', {
            webUrl: url,
            sidoCode: sidoCode,
            gunguCode: gunguCode,
            dongCode: dongCode,
            date: startDate
        }).done(function (data) {
            $('#shareUrl').attr('data-clipboard-text', 'sise.me/r/' + data.path);
            $('#shareUrl').text('sise.me/r/' + data.path);
        }).fail(function (response, status) {
        }).always(function () {
        });
    };

    numberWithCommas = (x) => {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    };

    postJSON = (url, data) => {
        return $.ajax({url: url, data: JSON.stringify(data), type: 'POST', contentType: 'application/json'});
    };

    initClipboardComponent = () => {
        let clipboard = new ClipboardJS('#shareUrl');

        clipboard.on('success', function (e) {
            alert('복사되었습니다');
            e.clearSelection();
        });

        clipboard.on('error', function (e) {
            alert('복사 실패 직접 복사해주세요')
        });
    };

    calculateArea = (value) => {
        return (parseFloat(value) / 3.3058).toFixed(3);
    };

    appendData = (data) => {
        data.forEach((x, idx) => {
            $('#maxTradeListTableBody').append(maxTradeTableRow(x, false, idx + currentElements, 'trade'));
            // Script
        });
        currentElements += data.length;

        $('#tradeListSize').text(currentElements + '/' + totalElements);
        $('#maxTradeListSize').text($('.trade-price-high').length);
        $('#newTradeListSize').text($('.trade-new-data').length);
        $('#tradeListPeriod').text($('#startDate').val());


        checkFilterList();
        $('#filterTypeMaxPrice').click((evt) => {
            $('.tradeListTableHiddenRow').css('display', 'none');
            maxTradeListType = !maxTradeListType;
            if (newTradeListType) {
                newTradeListType = !newTradeListType;
                $('#filterTypeNew').css('background-color', '#524F77');
                $('#filterTypeNew').css('border-color', '#524F77');
                $('#filterTypeNew').text('신규등록');
                checkFilterList();
            }
            if (maxTradeListType) {
                $('#filterTypeMaxPrice').css('background-color', '#A6B7BD');
                $('#filterTypeMaxPrice').css('border-color', '#A6B7BD');
                $('#filterTypeMaxPrice').text('해제');
                $('#tradeListTableTitle').text('신고가');
                checkFilterList();
            }
            else {
                $('#filterTypeMaxPrice').css('background-color', '#524F77');
                $('#filterTypeMaxPrice').css('border-color', '#524F77');
                $('#filterTypeMaxPrice').text('신고가');
                $('#tradeListTableTitle').text(getTradeTypeName());
                checkFilterList();
            }
            if ($("body").height() < $(window).height()) {
                if (pageNumber !== 0 && totalPages !== 0) {
                    if (pageNumber < totalPages) {
                        getTradePage();
                    }
                }
            }
        });
        $('#filterTypeNew').click((evt) => {
            $('.tradeListTableHiddenRow').css('display', 'none');
            newTradeListType = !newTradeListType;
            if (maxTradeListType) {
                maxTradeListType = !maxTradeListType;
                $('#filterTypeMaxPrice').css('background-color', '#524F77');
                $('#filterTypeMaxPrice').css('border-color', '#524F77');
                $('#filterTypeMaxPrice').text('신고가');
                $('#tradeListTableTitle').text(getTradeTypeName());
                checkFilterList();
            }
            if (newTradeListType) {
                $('#filterTypeNew').css('background-color', '#A6B7BD');
                $('#filterTypeNew').css('border-color', '#A6B7BD');
                $('#filterTypeNew').text('해제');
                checkFilterList();
            }
            else {
                $('#filterTypeNew').css('background-color', '#524F77');
                $('#filterTypeNew').css('border-color', '#524F77');
                $('#filterTypeNew').text('신규등록');
                checkFilterList();
            }
            if ($("body").height() < $(window).height()) {
                if (pageNumber !== 0 && totalPages !== 0) {
                    if (pageNumber < totalPages) {
                        getTradePage();
                    }
                }
            }
        });
        sync = false;
    };

    getStartDateValue = () => {
        let result = '';
        if($('#startDate').val().length === 8) {
            result = $('#startDate').val().substr(0, 4) + '0' + $('#startDate').val().substr(6, 1);
        }
        else {
            result = $('#startDate').val().substr(0, 4) + $('#startDate').val().substr(6, 2);
        }
        return result;
    };

    isShareUrl = () => {
        return $('#isShare').val() === 'true';
    };

    groupBy = (list, keyGetter) => {
        const map = new Map();
        list.forEach((item) => {
            const key = keyGetter(item);
            const collection = map.get(key);
            if (!collection) {
                map.set(key, [item]);
            } else {
                collection.push(item);
            }
        });
        return map;
    };

    getTradeTypeName = () => {
        let tradeType = $('#tradeType').val();
        if (tradeType === 'trades') {
            return '실거래가';
        }
        else if (tradeType === 'tickets') {
            return '분양권';
        }
        else {
            return '';
        }
    }

    checkFilterList = () => {
        if (maxTradeListType) {
            $('.trade-row').css('display', 'none');
            $('.trade-new-data').css('display', 'none');
            $('.trade-price-low').css('display', 'none');
            $('.trade-price-high').css('display', '');
        }
        else if (newTradeListType) {
            $('.trade-row').css('display', 'none');
            $('.trade-price-high').css('display', 'none');
            $('.trade-price-low').css('display', 'none');
            $('.trade-new-data').css('display', '');
        }
        else {
            $('.trade-row').css('display', '');
            $('.trade-price-high').css('display', '');
            $('.trade-price-low').css('display', '');
            $('.trade-new-data').css('display', '');
        }
    }


    //숫자 4단위 한글로 변환  pWon ='55,000,000,000' 이런 형태로 와야한다.
    setWon = (pWon) => {
        pWon = pWon.replace();
        var won  = (pWon+"").replace(/,/g, "");
        var arrWon  = ["원", "만", "억", "조", "경", "해", "자", "양", "구", "간", "정"];
        var changeWon = "";
        var pattern = /(-?[0-9]+)([0-9]{4})/;
        while(pattern.test(won)) {
            won = won.replace(pattern,"$1,$2");
        }
        var arrCnt = won.split(",").length-1;
        for(var ii=0; ii<won.split(",").length; ii++) {
            if(arrWon[arrCnt] == undefined) {
                console.log("값의 수가 너무 큽니다.");
                break;
            }
            var tmpwon=0;
            for(i=0;i<won.split(",")[ii].length;i++){
                if(arrCnt !== 0) {
                    var num1 = won.split(",")[ii].substring(i,i+1);
                    tmpwon = tmpwon+Number(num1);
                }
            }
            if(tmpwon > 0){
                changeWon += numberWithCommas(won.split(",")[ii])+arrWon[arrCnt]+' '; //55억0000만0000원 이런 형태 방지 0000 다 짤라 버린다
            }
            arrCnt--;
        }
        return changeWon;
    }
</script>
</body>
</html>