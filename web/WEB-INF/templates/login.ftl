<#include 'base.ftl'>
<#macro body>
<div class="row">
          <div class="col-md-4 col-md-offset-4">
            <div class="well bs-component">
                <form class="form-horizontal">
                <fieldset>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="Логин">
                        </div>  
                    </div> 
                    <div class="form-group">
                        <div class="col-md-12">            
                            <input type="password" class="form-control" placeholder="Пароль">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Запомнить меня
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