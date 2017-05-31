<#include 'base.ftl'>
<#macro body>
<div class="container">
    <div class="row">

        <div class="col-md-8 col-md-offset-2">

            <div class="row">
                <div class="col-md-6">
                    <h3> Диалоги: </h3>
                </div>

                <div class="col-md-6">
                    <ul class="pager">
                        <li><a href="#">&larr; Предыдущие</a></li>
                        <li><a href="#">Следующие &rarr;</a></li>
                    </ul>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">

                    <#list conversations as conv>
                    <div class="well bs-component" style="margin: 0px;">
                        <div class="row">
                            <div class="col-md-11">
                                <a href="/user/chat/${conv.user2.id}"><h4 class="list-group-item-heading">${conv.user2.name}
                                <span class="badge">${conv.unreadCount}</span></h4></a>
                                <p class="text-muted">Последнее собщение: ${conv.lastMessage.timestamp}</p>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="close">&times;</button>
                            </div>
                        </div>
                    </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>
<@display "Messages"></@display>