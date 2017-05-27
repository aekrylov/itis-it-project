<#ftl encoding='utf-8'>
<#macro display title="Админка">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">

        <title>Название компании</title>

        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/style.css" rel="stylesheet">
        <link rel="stylesheet" href="/datetimepicker/bootstrap-datetimepicker.min.css" />
 
        <script type="text/javascript" src="/js/jquery/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="/datetimepicker/moment/moment.min.js"></script>
        <script type="text/javascript" src="/datetimepicker/moment/ru.js"></script>
        <script type="text/javascript" src="/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    
    </head>
    
    <body style="margin-top: 70px;">

    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Название компании</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Администрирование<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="">Пользователи</a></li>
                                <li><a href="">Товары</a></li>
                                <li><a href="">Продажи</a></li>
                                <li><a href="">Отзывы</a></li>
                                <li><a href="">Избранные товары</a></li>
                                <li><a href="">Сообщения</a></li>
                            </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/logout">Выход</a></li>
                </ul>
            </div>
        </div>   
    </nav>
            
     <!--content-->
    <div class="container">
        <@body>
                Admin body
        </@body>
    </div>
     
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
  </body>
</html>
</#macro>