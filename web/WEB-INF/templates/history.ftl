<#include 'base.ftl'>
<#macro body>

    <div class="container">
        <div class="row">  
           <div class="col-md-12">

            <ul class="nav nav-tabs">
                <li class="active"><a href="#buy" data-toggle="tab">Покупки</a></li>
                <li><a href="#sale" data-toggle="tab">Продажи</a></li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="buy">
                    <div class="well bs-component">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Наименование</th>
                                <th>Дата</th>
                                <th>Продавец</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list buys as buy>
                            <tr>
                                <td><a>${buy.product.name}</a></td>
                                <td>${buy.timestamp}</td>
                                <td>${buy.seller.name}</td>
                                <!-- Только до отправления отзыва-->
                                <#if !buy.feedback??>
                                    <td><button class="btn btn-default modal-btn"
                                                data-toggle="modal" data-target="#ModalRecall" data-id="${buy.id}">
                                        Оставить отзыв</button> </td>
                                </#if>
                            </tr>

                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="tab-pane" id="sale">
              <div class="well bs-component">
                  <table class="table">
                    <thead>
                      <tr>
                        <th>Наименование</th>
                        <th>Дата</th>
                        <th>Покупатель</th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list sells as sell>
                        <tr>
                            <td><a>${sell.product.name}</a></td>
                            <td>${sell.timestamp}</td>
                            <td>${sell.buyer.name}</td>
                        </tr>
                    </#list>
                    </tbody>
                  </table> 
            </div> 
            </div>
                        
            <!-- Modal -->
            <div class="modal fade" id="ModalRecall" tabindex="-1" role="dialog" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Оставить отзыв</h4>
                  </div>
                    <form method="post">
                        <input type="hidden" name="id" id="bs-id">
                        <input type="hidden" name="action" value="rating">
                  <div class="modal-body">
                    <div class="form-group">
                        <b>Оценка:</b>
                        <br/>
                        <input type="number" name="rating">
<#--
                        <div id="rateYo"></div>
                        <div class="counter"></div>

                        &lt;#&ndash; TODO points &ndash;&gt;
                        <script type="text/javascript">$(function () {
                            $("#rateYo").rateYo({ 
                                rating: '0',
                                halfStar: true
                            });
                        }); 
                        </script>
-->
                        <br/>
                        <b>Отзыв:</b>
                        <textarea class="form-control" rows="3" name="text"></textarea>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Отправить</button>
                  </div>
                    </form>
                </div>
              </div>
            </div>
           
           </div>

               <script type="application/javascript">
                   var id;
                   $(".modal-btn").click(function(e) {
                       //$("#ModalRecall").modal("show");
                       id = $(this).attr("data-id");
                       var input = $("input#bs-id");
                       input.val(id);
                   })
               </script>
           
        </div>
    </div>
</#macro>
<@display "History"></@display>