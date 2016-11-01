<#include 'base.ftl'>

<#macro body>
<div class="container">
    <div class="row">

        <div class="col-md-1">
            <a href="/user/chats" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-left"></span></a>
        </div>

        <div class="col-md-10">
            <div class="well bs-component">

                <h3><a href="#">${user2.name}</a></h3>

                <div class="well bs-component" style="background-color:#ffffff;">
                    <div id="dialog">

                    <#list messages as msg>
                        <#if msg.from.id = user2.id>
                            <div class="row">
                                <div class="col-md-7">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-11">
                                                <p>${msg.text}</p>
                                                <p class="text-muted">${msg.date}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <button type="button" class="close">&times;</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </div>

                        <#else>
                        <div class="row">
                            <div class="col-md-7 col-md-offset-5">
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-1">
                                                <button type="button" class="close">&times;</button>
                                            </div>
                                            <div class="col-md-11">
                                                <p align="right">${msg.text}</p>
                                                <p align="right" class="text-muted">${msg.date}</p>
                                            </div>

                                        </div>
                                    </div>
                                </div>
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

<script type="text/javascript">
    $(function(){
        $('#dialog').slimScroll({
            height: '350px'
        });
    });
</script>
</#macro>
<@display "Dialog"></@display>