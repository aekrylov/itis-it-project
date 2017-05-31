<#include 'base.ftl'>
<#macro body>
<div class="row">
        
            <div class="col-md-4">

                <h3>${user.name}</h3>
                <div class="col-xs-12">
                    <#-- TODO remove old avatars -->
                    <img src="<#if user.has_avatar>${user.photo!"/files/users/${user.id}"}<#else>/static/img/avatar_default.png</#if>"
                         class="img-responsive" width="100%" />
                </div>

                <#if owner??>
                    <button class="btn btn-default" data-toggle="modal" data-target="#PhotoEditModal">Изменить фотографию</button>
                    <a href="/user/settings" class="btn btn-default"><span class="glyphicon glyphicon-cog"></span></a>
                    <!-- Modal -->
                <form method="post" enctype="multipart/form-data">
                    <div class="modal fade" id="PhotoEditModal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="ModalLabel">Изменение фотографии</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <input type="file" name="image">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary" >Сохранить изменения</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <#else>
                    <a href="/user/chat?uid=${user.id}" class="btn btn-default">Написать сообщение</a>
                </#if>
                <hr/>

                <#if user.rating?? && user.rate_count gt 0 >
                    <p><b>Рейтинг: </b><span id="user-rating">${user.rating?string["0.#"]}</span> от ${user.rate_count} пользователей </p>

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
                </#if>

                <hr/>

                <#if user.email??><p><b>Почта: </b> ${user.email}</p></#if>
                <p><b>Логин: </b> ${user.username}</p>
                <p><b>Товаров: </b> ${post_count}</p>

                <a href="/items?author=${user.id}" class="btn btn-default" align="right">Товары</a>
                <#if owner??>
                    <a href="/item/add" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></a>
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


                <#list buy_sells as bs >
                    <div class="well bs-component">

                            <legend><a href="/user?id=${bs.buyer.id}">${bs.buyer.name}</a></legend>
                            <p><b>Оценка: </b> ${bs.feedback.score}</p>
                            <p>${bs.feedback.comment}</p>
                            <#-- TODO date format -->
                            <p align="right">${bs.timestamp}</p>

                    </div>

                </#list>
            </div>
            
        </div>
</#macro>

<@display "${user.name} - Profile"></@display>