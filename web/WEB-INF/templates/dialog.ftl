<#include 'base.ftl'>

<#macro body>
<div class="container">
    <div class="row">

        <div class="col-md-1">
            <a href="/user/chats" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-left"></span></a>
        </div>

        <div class="col-md-11">
            <div class="well bs-component">

            <div class="row">
                <div class="col-md-4">
                    <h3> ${user2.name} </h3>
                </div>

                <div class="col-md-8">
                    <ul class="pager">
                        <li><a href="#">&larr; Предыдущие</a></li>
                        <li><a href="#">Следующие &rarr;</a></li>
                    </ul>
                </div>
            </div>

                <hr/>

                <div class="well bs-component" style="background-color:#ffffff;">

                    <#list messages as msg>
                        <#-- todo -->
                        <#if msg.from.id = user2.id>
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="well well-sm">
                                        <div class="row">
                                            <div class="col-md-11">
                                                <p>${msg.text}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <button type="button" class="close">&times;</button>
                                            </div>
                                        </div>
                                    </div>
                                    <p>${msg.timestamp}</p>
                                </div>
                            </div>

                        <#else>
                            <div class="row">
                                <div class="col-md-8 col-md-offset-4">
                                    <div class="well well-sm">
                                        <div class="row">
                                            <div class="col-md-1">
                                                <button type="button" class="close" style="float:left;">&times;</button>
                                            </div>
                                            <div class="col-md-11">
                                                <p align="right">${msg.text}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <p align="right">${msg.timestamp}</p>
                                </div>
                            </div>

                        </#if>
                    </#list>
                </div>

                <hr/>

                <div class="row">
                    <form method="post" action="/user/chat">
                        <input type="hidden" name="to" value="${user2.id}">
                        <div class="col-md-10">
                            <textarea class="form-control" rows="3" placeholder="Введите сообщение" name="text"></textarea>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-default" style="float:right;">Отправить</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>

    </div>
</div>
</#macro>
<@display "Dialog"></@display>