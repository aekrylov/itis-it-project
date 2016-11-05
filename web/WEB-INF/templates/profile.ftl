<#include 'base.ftl'>
<#macro body>
<div class="row">
        
            <div class="col-md-4">

                <h3>${user.name}</h3>
                <img src="${user.photo!}" width="250" height="375" />

                <#if owner??>
                    <button class="btn btn-default" data-toggle="modal" data-target="#PhotoEditModal">Изменить фотографию</button>
                    <a href="#" class="btn btn-default"><span class="glyphicon glyphicon-cog"></span></a>
                    <!-- Modal -->
                    <div class="modal fade" id="PhotoEditModal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="ModalLabel">Изменение фотографии</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <input type="file">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" >Сохранить изменения</button>
                                </div>
                            </div>
                        </div>
                    </div>

                <#else>
                    <a href="#" class="btn btn-default">Написать сообщение</a>
                </#if>
                <hr/>

                <if user.rating??>
                    <p><b>Рейтинг: </b><span id="user-rating">${user.rating?string["0.#"]}</span> от ${feedbacks?size} пользователей </p>

                    <div class="row">
                        <div class="col-md-12">

                            <div id="rateYo"></div>

                            <script type="text/javascript">$(function () {
                                $("#rateYo").rateYo({
                                    rating: '${user.rating}',
                                    readOnly: true
                                });
                            });
                            </script>
                        </div>
                    </div>
                    <br/>
                </if>

                <hr/>

                <#if user.email??><p><b>Почта: </b> ${user.email}</p></#if>
                <p><b>Логин: </b> ${user.username}</p>
                <p><b>Товаров: </b> ${post_count}</p>

                <a href="#" class="btn btn-default" align="right">Товары</a>
                <#if owner??>
                    <a href="#" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></a>
                </#if>

            </div>

            <div class="col-md-8 ">

                <div class="row">
                    <div class="col-md-6">
                        <h3> Отзывы: </h3>
                    </div>

                    <div class="col-md-6">
                        <ul class="pager">
                            <li><a href="#">&larr; Предыдущие</a></li>
                            <li><a href="#">Следующие &rarr;</a></li>
                        </ul>
                    </div>
                </div>


                <#list feedbacks as f >
                    <div class="well bs-component">

                            <legend><a href="/user?id=${f.buy_sell.buyer.id}">${f.buy_sell.buyer.name}</a></legend>
                            <p><b>Оценка: </b> ${f.score}</p>
                            <p>${f.comment}</p>
                            <#-- TODO date format -->
                            <p align="right">${f.date}</p>

                    </div>

                </#list>
            </div>
            
        </div>
</#macro>

<@display "${user.name} - Profile"></@display>