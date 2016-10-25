<#include 'base.ftl'>
<#macro body>
    <div class="container">
        <div class="row">
        
            <div class="col-md-12">

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
                <hr/>


                <#list conversations as conv>
                    <div class="well bs-component">
                        <fieldset>

                            <div class="row">
                                <div class="col-md-6">
                                    <h4><a>${conv.user2.name} <span class="badge">${conv.unreadCount}</span> </a></h4>
                                </div>

                                <div class="col-md-6">
                                    <a href="#" class="btn btn-primary btn-xs" style="float:right"><span class="glyphicon glyphicon-remove"></span></a>
                                </div>
                            </div>
                            <hr/>
                            <p>${conv.lastMessage.text}</p>
                            <p align="right">${conv.lastMessage.date}</p>

                        </fieldset>
                    </div>
                </#list>
            </div>
            
        </div>
    </div>
</#macro>
<@display "Messages"></@display>