<#include 'base.ftl'>
<#macro body>
<div class="row">
          <div class="col-md-4 col-md-offset-4">
            <div class="well bs-component">
                <form class="form-horizontal" method="post" action="/login">
                <fieldset>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="Логин" name="username" value="${username}">
                        </div>  
                    </div> 
                    <div class="form-group">
                        <div class="col-md-12">            
                            <input type="password" class="form-control" placeholder="Пароль" name="password">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="remember"> Запомнить меня
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">                      
                            <button type="submit" class="btn btn-default btn-block">Войти</button>
                        </div>
                    </div>     
                </fieldset>
                </form>
            </div>
          </div>
      </div>
</#macro>
<@display "Login"></@display>