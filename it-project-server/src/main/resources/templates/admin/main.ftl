<#include '../base.ftl'>
<#macro body>
<div class="container">
    <div class="row">

        <div class="col-md-8 col-md-offset-2">
            <h3>Администрирование сайта</h3>

            <div class="well bs-component">

                <table class="table table-striped">
                    <tbody>
                    <#list tables as name, title>
                    <tr>
                        <td><b><a href="/admin/?table=${name}">${title}</a></b></td>
                        <td><a href="/admin/create?table=${name}">
                            <span class="glyphicon glyphicon-plus"> </span> Добавить
                        </a></td>
                    </tr>
                    </#list>
<#--
                    <tr>
                        <td><b><a href="dialog.html">Товары</a></b></td>
                        <td><a href=""><span class="glyphicon glyphicon-plus"> </span> Добавить </a></td>
                        <td><a href=""><span class="glyphicon glyphicon-pencil"> </span> Изменить </a></td>
                    </tr>
-->
                    </tbody>
                </table>

            </div>

        </div>

    </div>
</div>
</#macro>

<@display "Главная админки"></@display>