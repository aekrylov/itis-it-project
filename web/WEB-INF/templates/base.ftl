<#macro display title="Название компании">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>${title}</title>

        <link href="/static/css/bootstrap.css" rel="stylesheet">
        <link href="/static/css/bootstrap.min.css" rel="stylesheet">
        <link href="/static/css/style.css" rel="stylesheet">

    <script type="text/javascript" src="/static/js/jquery/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery/rateyo/jquery.rateyo.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery/fotorama/fotorama.js"></script>
    <script type="text/javascript" src="/static/js/jquery/slimscroll/jquery.slimscroll.min.js"></script>

    </head>

<body style="margin-top: 70px;">

        <#include 'header.ftl'>
        <div class="container">
        <@body>
                Dummy body
        </@body>
        </div>
        <#include 'footer.ftl'>
        <script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
    </body>
</html>
</#macro>