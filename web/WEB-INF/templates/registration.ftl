<#include 'base.ftl'>
<#macro body>
        <div class="row">
        <div class="col-md-6 col-md-offset-3">
        <div class="well bs-component">
            <form class="form-horizontal">
            <fieldset>
                <legend>�����������</legend>
                <div class="form-group">
                    <label for="inputLogin" class="col-md-2 control-label">�����</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputLogin" placeholder="�����">
                    </div>
                </div>
                   
                <div class="form-group">                       
                    <label for="inputPasswod" class="col-md-2 control-label">������</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputPasswod" placeholder="������">
                        <input type="text" class="form-control" placeholder="��������� ������">
                    </div>
                </div>
                
                <div class="form-group">                       
                    <label for="inputName" class="col-md-2 control-label">���</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputName" placeholder="���">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="inputEmail" class="col-md-2 control-label">�����</label>
                    <div class="col-md-10">
                        <input type="text" class="form-control" id="inputEmail" placeholder="�����">
                    </div>
                </div>
                <hr/>
                <div class="form-group">
                    <div class="col-md-6 col-md-offset-2">            
                        <button type="submit" class="btn btn-default">������������������</button>
                    </div>
                </div>     
            </fieldset>
            </form>
        </div>
        </div>
        </div>
</#macro>
<@display "Registration"></@display>