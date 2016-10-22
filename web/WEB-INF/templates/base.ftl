<#macro display title="Название компании">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>${title}</title>

        <link href="static/css/bootstrap.css" rel="stylesheet">
        <link href="static/css/bootstrap.min.css" rel="stylesheet">
        <link href="static/css/style.css" rel="stylesheet">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body style="margin-top: 70px;">

    <#include 'header.ftl'>
    <div class="container">
    <@body>
            Dummy body
    </@body>
    <#include 'footer.ftl'>
    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="static/js/bootstrap.min.js"></script>
    </body>
</html>
</#macro>