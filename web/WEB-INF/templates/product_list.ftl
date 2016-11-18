<#include 'base.ftl'>
<#macro body>

    <div class="container">
        <div class="row">  
            <div class="col-md-3">
                <#if filtered??>

                </#if>

                <form class="form-horizontal" method="get">
                    <input type="hidden" name="filtered" value="yes">
            
                    <div class="form-group">
                        <h5>Тип:</h5>
                        <div class="checkbox">
                        <label>
                              <input type="checkbox" name="type" value="laptop" <#if (params.type!'') = 'laptop'>checked</#if>>
                              Ноутбуки
                              </label>
                        </div>
                        <div class="checkbox">
                        <label>
                              <input type="checkbox" name="type" value="desktop" <#if (params.type!'') = 'desktop'>checked</#if>>
                              Настольные компьютеры
                              </label>
                        </div>
                        <div class="checkbox">
                        <label>
                              <input type="checkbox" name="type" value="aio" <#if (params.type!'') = 'aio'>checked</#if>>
                              Моноблоки
                              </label>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-10">

                            <div class="form-group">
                                <h5>Стоимость:</h5>
                                <label for="inputFrom" class="col-md-4 control-label">от:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control input-sm" id="inputFrom" name="price_low"
                                    value="${params.price_low!}">
                                </div>

                                <label for="inputTo" class="col-md-4 control-label">до:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control input-sm" id="inputTo" name="price_high"
                                    value="${params.price_high!}">
                                 </div>
                           </div>
                 
                       </div>
                 </div>
                 
                <div class="row">
                    <div class="col-md-10">            
               
                      <div class="form-group">
                         <h5>Бренд:</h5>
                          <input type="text" name="brand" class="form-control" placeholder="leno" value="${params.brand!}">
                         <h5>Модель:</h5>
                          <input type="text" name="model" class="form-control" value="${params.model!}">
<#--TODO
                         <h5>Процессор:</h5>
                          <select class="form-control input-sm">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                         </select> 
                         <h5>Число ядер:</h5>
                         <select class="form-control input-sm">
                            <option>1</option>
                            <option>2</option>
                            <option>4</option>
                            <option>6</option>
                            <option>8</option>
                            <option>4</option>
                            <option>4</option>
                         </select>
                         <h5>Размер ОЗУ:</h5>
                         <select class="form-control input-sm">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                         </select>
                         <h5>Видеокарта:</h5>
                         <select class="form-control input-sm">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                         </select>
                         <h5>Жесткий диск:</h5>
                         <select class="form-control input-sm">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                         </select>
                         <h5>Объем жесткого диска:</h5>
                         <select class="form-control input-sm">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                         </select>
-->
                      </div>

                 
                      <div class="form-group">                      
                          <button type="submit" class="btn btn-default btn-block">Найти</button>
                      </div>
                      
                    </div>
               </div>

       
              </form>
           </div>
        

           <div class="col-md-9">

                <div class="row"> 
                    <div class="col-md-12">
                        <ul class="pager">
                            <li><a href="#">&larr; Предыдущие</a></li>
                            <li><a href="#">Следующие &rarr;</a></li>
                        </ul>  
                    </div>               
                </div>


               <#list posts as p>
                   <div class="well bs-component">

                       <h4><a href="/item?id=${p.id}">${p.product.name}</a></h4>
                       <hr/>

                       <div class="row">
                           <div class="col-md-4">
                               <img src="/files/products/${p.product.id}/1" width="100%" style="margin-top:0px;"/>
                           </div>

                           <div class="col-md-8">
                               <div class="row">
                                   <div class="col-md-6" style="float: left;">
                                       <p><b>Стоимость:</b> ${p.product.price}</p>
                                       <p><b>Тип: </b> ${p.product.type} <#-- TODO --></p>
                                       <p><b>Бренд: </b> ${p.product.brand}</p>
                                       <p><b>Модель: </b> ${p.product.model}</p>
                                       <p><b>Процесоор: </b>  ${p.product.cpu_name}</p>
                                   </div>
                                   <div class="col-md-6" style="float:right;">
                                       <p><b>Число ядер: </b> ${p.product.cores}<p>
                                       <p><b>Размер ОЗУ: </b> ${p.product.ram_gb} ГБ</p>
                                       <p><b>Видеокарта: </b> ${p.product.video_card}</p>
                                       <p><b>Жесткий диск: </b> ${p.product.hdd_name}</p>
                                       <p><b>Объем жесткого диска: </b> ${p.product.hdd_capacity} Гб</p>
                                   </div>
                               </div>

                           </div>
                       </div>
                   </div>
               <#else >
                <div class="jumbotron"><p>Ничего не найдено.</p>
                    <a href="/items" class="btn btn-primary">Сбросить поиск</a>
                </div>
               </#list>

           </div> 
           
        </div>

</#macro>
<@display 'Product list'></@display>