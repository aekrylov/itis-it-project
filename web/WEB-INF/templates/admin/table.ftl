﻿<#include '../base.ftl'>
<#macro body>
<#assign page = (params.page!1)?number>
    <div class="container-fluid">
            
                            
        <div class="row">  
            <div class="col-md-9">
            
                 <div class="row">  
                     <div class="col-md-6">
                        <h3>${tablename}</h3>
                     </div>
                     <div class="col-md-6">
                        <ul class="pager" style="float: right">
                            <#if 1 < page>
                            <#-- TODO freaking links -->
                                <li><a href="?table=${tablename}&page=${page-1}&sort=${params.sort!''}<#if params.sort_desc??>&sort_desc=true</#if>&q=${params.q!''}">&larr; Назад</a></li>
                            </#if>
                            <li><a href="?table=${tablename}&page=${page+1}&sort=${params.sort!''}<#if params.sort_desc??>&sort_desc=true</#if>&q=${params.q!''}">Вперед &rarr;</a></li>
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
                <form class="form-horizontal" method="get">
                    <input type="hidden" name="table" value="${tablename}">
                    <div class="row">  
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="поиск..." name="q" value="${params.q!''}">
                        </div>
                    </div>
                <hr/>
                <p>Отсортировать по:</p>
                    <div class="row">
                        <div class="col-md-12">
                            <select class="form-control" name="sort">
                                <#list columns as column>
                                    <option <#if column=params.sort!''>selected</#if>>${column}</option>
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
                    <#if params.q??>
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