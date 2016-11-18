<#include 'base.ftl'>

<#macro body>
<div class="container">

    <div class="row">
        <div class="col-md-12">
            <h3> ${product.name}</h3>
        </div>
    </div>

    <div class="row">

        <div class="col-md-8">

            <#-- TODO -->
            <div class="fotorama">
                <img src="product_photo\1.jpg">
                <img src="product_photo\2.jpg">
                <img src="product_photo\3.jpg">
            </div>

            <h4>Описание:</h4>
            <p>${product.description}</p>

        </div>

        <div class="col-md-3 col-md-offset-1" style="margin-top: 20px;">

            <#assign type>
                <#if product.type == "desktop">ПК<#elseif product.type=="laptop">ноутбук<#else>моноблок</#if>
            </#assign>

            <p><b>Стоимость: </b> ${product.price}</p>
            <p><b>Продавец: </b><a href="/user?id=${post.user.id}">${post.user.name}</a></p>
            <hr/>
            <h4>Характеристики:</h4>
            <p><b>Тип: </b> ${type}</p>
            <p><b>Бренд: </b> ${product.brand}</p>
            <p><b>Модель: </b> ${product.model}</p>
            <p><b>Процессор: </b>  ${product.cpu_name}</p>
            <p><b>Число ядер: </b> ${product.cores}<p>
            <p><b>Размер ОЗУ: </b> ${product.ram_gb} Гб</p>
            <p><b>Видеокарта: </b> ${product.video_card}</p>
            <p><b>Жесткий диск: </b> ${product.hdd_name} </p>
            <p><b>Объем жесткого диска: </b> ${product.hdd_capacity} Гб</p>
            <hr/>

            <#if isSeller>
                <button class="btn btn-default btn-block" data-toggle="modal" data-target="#SellModal">Продать товар</button>
                <form method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="post_id" value="${post.id}">
                    <button class="btn btn-default btn-block">Удалить</button>
                </form>


                <!-- Modal -->
                <div class="modal fade" id="SellModal" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">Выберите покупателя</h4>
                            </div>
                            <form method="post">
                                <input type="hidden" name="action" value="sell">
                                <input type="hidden" name="post_id" value="${post.id}">
                            <div class="modal-body">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Введите логин" name="buyer">
                                </div>
                            </div>
                            <div class="modal-footer">
                                <#-- TODO -->
                                <button type="submit" class="btn btn-primary">Продать</button>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            <#else>
                <button class="btn btn-default btn-block">Добавить избранное</button>
                <button class="btn btn-default btn-block">Связаться с продавцом</button>
            </#if>
        </div>

    </div>
</div>
</#macro>
<@display product.name></@display>