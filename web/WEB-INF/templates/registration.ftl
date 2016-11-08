<#include 'base.ftl'>
    <#macro body>
    <div class="container">
        <div class="row">
        
            <div class="col-md-6 col-md-offset-3">
            <div class="well bs-component">
                <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Регистрация</legend>
                     <div class="form-group has-feedback">
                        <label for="inputLogin" class="col-md-2 control-label">Логин*</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" id="inputLogin" placeholder="Логин" name="username" required>
                            <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                            <p style="color: red; margin: 0px;">Логин должен состоять из латинских букв, цифр и быть не короче 4 символов</p>
                        </div>

                    </div>
                       
                    <div class="form-group has-feedback">                       
                        <label for="inputPasswod1" class="col-md-2 control-label">Пароль*</label>
                        <div class="col-md-10">
                            <input type="password" class="form-control" id="inputPassword1" placeholder="Пароль" name="password" required>
                            <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                            <p style="color: red;">Пароль должен состоять из латинских букв, цифр и быть и быть не короче 6 символов</p>                  
                        </div>

                    </div>
                    
                    <div class="form-group has-feedback">                       
                        <div class="col-md-10 col-md-offset-2">
                            <input type="password" class="form-control"  id="inputPassword2"
                                   name="password_repeat" placeholder="Повторите пароль" required>
                            <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                            <p style="color: red;">Пароли не совпадают</p>
                        </div>
                    </div>
                    
                    <div class="form-group">                       
                        <label for="inputName" class="col-md-2 control-label">Имя</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" id="inputName" placeholder="Имя" name="name">
                        </div>
                    </div>
                    
                    <div class="form-group has-feedback">
                        <label for="inputEmail" class="col-md-2 control-label" id="email" >Почта*</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" id="inputEmail"  placeholder="Почта" name="email" required>
                            <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                             <p style="color: red;">Почта должна быть реальной</p>
                        </div>
                    </div>
                    <hr/>
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-2">            
                            <button type="submit" id="submit" class="btn btn-default">Зарегестрироваться</button>
                        </div>
                    </div>     
                </fieldset>
                </form>
                
 
 
                        <script type="text/javascript">                       
                        $(document).ready(function() {  
                        
                                $("p").hide();
                        
                                $("#inputLogin").isValid(new RegExp(/^[a-z0-9_-]{4,40}$/i));
                   
                                $("#inputPassword1").isValid(RegExp(/^[a-z0-9]{6,40}$/i));
                   
                                $("#inputEmail").isValid(new RegExp(/^[a-z0-9_-]+@[a-z0-9-]+\.[a-z]{2,6}$/i));

                        });    


                        (function($) {                        
                        $.fn.isValid = function (reg) {
                            var pattern = reg;
                            $(this).keyup(function(){                          
                             var value = $(this).val();    
                                if( value != 0)
                                {
                                if( pattern.test(value) ) {
                                $(this).parents(".form-group").addClass("has-success").removeClass('has-error');
                                $(this).next("span").addClass("glyphicon-ok").removeClass('glyphicon-remove').next("p").hide();
                                }
                                else{
                                $(this).parents(".form-group").addClass("has-error").removeClass('has-success');
                                $(this).next("span").addClass("glyphicon-remove").removeClass('glyphicon-ok').next("p").show();
                                }
                                }
                                else{
                                $(this).parents(".form-group").removeClass("has-success").removeClass('has-error');
                                $(this).next("span").removeClass("glyphicon-ok").removeClass('glyphicon-remove').next("p").hide();
                                }
                                
                                return this;
                      
                            });
                        };
                        })(jQuery);                        
                        </script>
                        
                        <script type="text/javascript">
                        $(document).ready(function() { 
                        
                                $("#inputPassword2").keyup(function(){
                                
                                var password1 = $("#inputPassword1").val();
                                var password2 = $("#inputPassword2").val();
  
                                if(password2 != 0)
                                {
                                if( password2 == password1) {
                                $(this).parents(".form-group").addClass("has-success").removeClass('has-error');
                                $(this).next("span").addClass("glyphicon-ok").removeClass('glyphicon-remove').next("p").hide();;
                                }
                                else{
                                $(this).parents(".form-group").addClass("has-error").removeClass('has-success');
                                $(this).next("span").addClass("glyphicon-remove").removeClass('glyphicon-ok').next("p").show();
                                }
                                }
                                else{
                                $(this).parents(".form-group").removeClass("has-success").removeClass('has-error');
                                $(this).next("span").removeClass("glyphicon-ok").removeClass('glyphicon-remove').next("p").hide();;
                                }
                            })
                        })
                        </script>
    
    <script type="text/javascript">
    $(document).ready(function() { 
    
        $("#submit").mouseenter(function(){
            
            if  ($("#inputLogin").parents(".form-group").hasClass("has-success") &&                
            $("#inputPassword1").parents(".form-group").hasClass("has-success") &&                  
            $("#inputEmail").parents(".form-group").hasClass("has-success") &&
            $("#inputPassword2").parents(".form-group").hasClass("has-success")) {
                $(this).removeClass("disabled");
            }
            else{
                $(this).addClass("disabled");
            }
        })
        
        $("#submit").mouseleave(function(){
            $(this).removeClass("disabled");
        })                            

    })
    </script>
                </div>
                </div>
            </div>
        </div>
    </#macro>
    <@display "Регистрация"></@display>