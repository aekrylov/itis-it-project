<#include 'base.ftl'>
<#macro body>
<div class="row">
        
            <div class="col-md-4">

                <h3>${user.name}</h3>
                <img src="${user.photo!}" width="250" height="375" />

                <#if owner??>
                    <a href="#" class="btn btn-default">Изменить фотографию</a>
                    <a href="#" class="btn btn-default"><span class="glyphicon glyphicon-cog"></span></a>
                <#else>
                    <a href="#" class="btn btn-default">Написать сообщение</a>
                </#if>

                <hr/>

                <#if user.email??><p><b>Почта: </b> ${user.email}</p></#if>
                <p><b>Товаров: </b> ${post_count}</p>
                <p><b>Рейтинг: </b> ${user.rating?string["0.#"]}</p>
                <h3>Тут будут звезды </h3>

                <a href="#" class="btn btn-default" align="right">Товары</a>
                <#if owner??>
                    <a href="#" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></a>
                </#if>

            </div>

            <div class="col-md-8 ">

                <div class="row">
                    <div class="col-md-7">
                        <h3> Отзывы: </h3>
                    </div>
                    
                    <div class="col-md-5">
                        <ul class="pager" style="float:right">
                            <li><a   style="color: black;" href="#">Предыдущие</a></li>
                            <li><a style="color: black;" href="#">Следующие</a></li>
                        </ul>  
                    </div>               
                </div>


                <#list feedbacks as f >
                    <div class="well bs-component">
                        <fieldset>

                            <legend>${f.author.name}</legend>
                            <p><b>Оценка: </b> ${f.score}</p>
                            <p>${f.comment}</p>
                            <#-- TODO date format -->
                            <p align="right">${f.date}</p>

                        </fieldset>
                    </div>

                </#list>
            </div>
            
        </div>
</#macro>

<@display "Profile"></@display>