<#include 'base.ftl'>

<#macro body>
<div class="container">
    <div class="row">

        <div class="col-md-1">
            <a href="/user/chats" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-left"></span></a>
        </div>

        <div class="col-md-11">

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

            <#list messages as msg>
            <#-- todo -->
                <div class="row <#if msg.from.id = user1.id>msg-sent<#else>msg-received</#if>">
                    <div class="col-md-8  <#if msg.from.id = user1.id>col-md-offset-4</#if>">
                        <div class="well well-sm">
                            <div class="row">
                                <#if msg.from.id = user1.id>
                                    <div class="col-md-1">
                                        <button type="button" class="close">&times;</button>
                                    </div>
                                    <div class="col-md-11">
                                        <p>${msg.text}</p>
                                    </div>
                                <#else>
                                    <div class="col-md-11">
                                        <p>${msg.text}</p>
                                    </div>
                                    <div class="col-md-1">
                                        <button type="button" class="close">&times;</button>
                                    </div>
                                </#if>

                                </div>
                        </div>
                        <p>${msg.timestamp?datetime}</p>
                    </div>
                </div>
            </#list>
        </div>

    </div>
</div>
</#macro>
<@display "Dialog"></@display>