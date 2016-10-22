<#include 'base.ftl'>
<#macro body>
        <div class="row">
        <div class="col-md-6 col-md-offset-3">
        <div class="well bs-component">
            <form class="form-horizontal">
            <fieldset>
                <legend>Регистрация</legend>
                <div class="form-group">
                    <label for="inputLogin" class="col-md-2 control-label">Логин</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputLogin" placeholder="Логин">
                    </div>
                </div>
                   
                <div class="form-group">                       
                    <label for="inputPasswod" class="col-md-2 control-label">Пароль</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputPasswod" placeholder="Пароль">
                        <input type="text" class="form-control" placeholder="Повторите пароль">
                    </div>
                </div>
                
                <div class="form-group">                       
                    <label for="inputName" class="col-md-2 control-label">Имя</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputName" placeholder="Имя">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="inputEmail" class="col-md-2 control-label">Почта</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputEmail" placeholder="Почта">
                    </div>
                </div>
                <hr/>
                <div class="form-group">
                    <div class="col-md-6 col-md-offset-2">            
                        <button type="submit" class="btn btn-default">Зарегестрироваться</button>
                    </div>
                </div>     
            </fieldset>
            </form>
        </div>
        </div>
        </div>
</#macro>
<@display "Registration"></@display>