<#include '../base.ftl'>
<#macro body>
<#assign pageNum = (page.page!1)?number>
    <div class="container-fluid">
            
                            
        <div class="row">  
            <div class="col-md-9">
            
                 <div class="row">  
                     <div class="col-md-6">
                        <h3>${tablename}</h3>
                     </div>
                     <div class="col-md-6">
                        <ul class="pager" style="float: right">
                            <#if 1 < pageNum>
                            <#-- TODO freaking links -->
                                <li><a href="?table=${tablename}&page=${pageNum-1}&sort=${page.sort!''}<#if page.sort_desc??>&sort_desc=true</#if>&q=${q!''}">&larr; Назад</a></li>
                            </#if>
                            <#if has_next_page>
                                <li><a href="?table=${tablename}&page=${pageNum+1}&sort=${page.sort!''}<#if page.sort_desc??>&sort_desc=true</#if>&q=${q!''}">Вперед &rarr;</a></li>
                            </#if>
                        </ul>
                     </div>
                 </div> 
                        
                <div class="well bs-component table-container">
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
                        <#list columns as col>
                            <#assign value = (row[col]!'')?string?trim>
                            <td><#if value?length < 20>${value}<#else >${value[0..*20]}...</#if></td>
                        </#list>
                        
                        <td><a href="/admin/edit?table=${tablename}&id=${row.id}" ><span class="glyphicon glyphicon-pencil"></span></a></td>
                        <td>
                            <form action="/admin/delete" method="post">
                                <input type="hidden" name="table" value="${tablename}">
                                <input type="hidden" name="id" value="${row.id}">
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
                <form class="form-horizontal" method="get">
                    <input type="hidden" name="table" value="${tablename}">
                    <div class="row">  
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="поиск..." name="q" value="${q!''}">
                        </div>
                    </div>
                <hr/>
                <p>Отсортировать по:</p>
                    <div class="row">
                        <div class="col-md-12">
                            <select class="form-control" name="sort">
                                <#list columns as column>
                                    <option <#if column=sort!''>selected</#if>>${column}</option>
                                </#list>
                            </select>
                            <div class="checkbox">
                              <label>
                                <input type="checkbox" name="sort_desc"> Обратный порядок
                              </label>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <input type="submit" class="btn btn-default btn-block" value="Поиск">
                        </div>
                    </div>
                    <#if q??>
                        <hr>
                        <div class="row">
                            <div class="col-md-12">
                                <a href="/admin/?table=${tablename}" class="btn btn-default btn-block">Сбросить поиск</a>
                            </div>
                        </div>
                    </#if>


                </form>             
            </div>
        
        </div>
    </div>

</#macro>

<@display "${tablename} | Admin"></@display>