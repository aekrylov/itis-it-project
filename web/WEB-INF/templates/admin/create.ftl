<#include '../base.ftl'>
<#macro body>
<div class="container">
    <div class="row">

        <div class="col-md-8 col-md-offset-2">
            <div class="well bs-component">
                <#if error??>
                        <p class="alert alert-danger">
                            ${error}
                        </p>
                </#if>
                <form class="form-horizontal" method="post" action="/admin/${action}">
                    <input type="hidden" name="table" value="${tablename}">

                    <#list columns as column>
                        <div class="form-group">
                            <label for="input-${column}" class="col-md-2 control-label">${column}</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" id="input-${column}"
                                       name="${column}" value="${values[column]!}">
                            </div>
                        </div>
                    </#list>

<#--TODO photos where needed
                    <div class="row">
                        <div class="col-md-2 col-md-offset-2">
                            <img src="profile_photo\1.jpg" width="100%"/>
                        </div>
                        <div class="col-md-6" style="margin-top:20px;">
                            <div class="form-group">
                                <input type="file">
                            </div>
                        </div>
                    </div>
-->

                    <hr/>
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-2">
                            <input type="submit" class="btn btn-default" value="Сохранить изменения">
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
</#macro>
<@display "${tablename} - new | admin"></@display>