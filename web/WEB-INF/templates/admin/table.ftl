<#include '../base.ftl'>
<#macro body>
    <div class="container-fluid">
            
                            
        <div class="row">  
            <div class="col-md-9">
            
                 <div class="row">  
                     <div class="col-md-6">
                        <h3>${tablename}</h3>
                     </div>
                     <div class="col-md-6">
                        <ul class="pager" style="float: right">
                            <li><a href="#">&larr; Назад</a></li>
                            <li><a href="#">Вперед &rarr;</a></li>
                        </ul>
                     </div>
                 </div> 
                        
                <div class="well bs-component">
                  <table class="table table-striped table-bordered">
                    <thead>
                      <tr>
                          <#list columns as header>
                                  <th>${header}</th>
                          </#list>
                          <th></th>
                          <th></th>
                      </tr>
                    </thead>
                    <tbody>
                    <#list rows as row>
                    <tr>
                        <#list row as field>
                        <#assign value = (field!'')?string?trim>
                        <td><#if value?length < 20>${value}<#else >${value[0..*20]}...</#if></td>
                        </#list>
                        <td><a href="/admin/edit?table=${tablename}&id=${row[0]}" ><span class="glyphicon glyphicon-pencil"></span></a></td>
                        <td>
                            <form action="/admin/delete" method="post">
                                <input type="hidden" name="table" value="${tablename}">
                                <input type="hidden" name="id" value="${row[0]}">
                                <button type="submit">
                                    <span class="glyphicon glyphicon-remove"></span></button>
                            </form>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                  </table> 
                </div>

            </div>  
            
            <div class="col-md-3" style="margin-top:20px;">
                           
                <a href="/admin/create?table=${tablename}" class="btn btn-default btn-block">
                    <span class="glyphicon glyphicon-plus">Добавить</span></a>
                <hr/>
                <form class="form-horizontal">
                    <div class="row">  
                        <div class="col-md-9">
                            <input type="text" class="form-control" placeholder="поиск...">                 
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
                        </div>
                    </div>
                </form>
                
                <hr/>
<#--            TODO sorting
                <p>Отсортировать по:</p>
                <form class="form-horizontal">
                    <div class="row">  
                        <div class="col-md-9">
                            <select class="form-control">
                                <option>Логину</option>
                                <option>Почте</option>
                                <option>Имени</option>
                                <option>Рейтингу</option>
                                <option>Роли</option>
                            </select>
                            <div class="checkbox">
                              <label>
                                <input type="checkbox"> Обратный порядок
                              </label>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-ok"></span></button>
                        </div>
                    </div> 
                </form>             
-->
            </div>
        
        </div>
    </div>

</#macro>

<@display "${tablename} | Admin"></@display>