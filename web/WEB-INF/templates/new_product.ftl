<#include 'base.ftl'>

<#macro body>
<div class="container">
        <div class="well bs-component">
            <form class="form-horizontal" method="post">

            <div class="row">


            <div class="col-md-7">
                <div class="form-group">
                    <label for="inputName" class="col-md-4 control-label">Наименование</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="inputName" name="name" placeholder="Введите наименование товара">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="inputCost" class="col-md-4 control-label">Стоимость</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="inputCost" name="price">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="inputBrand" class="col-md-4 control-label">Бренд</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="inputBrand" name="brand" placeholder="Введите бренд">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputModel" class="col-md-4 control-label">Модель</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="inputModel" name="model" placeholder="Введите модель">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputProcessor" class="col-md-4 control-label">Процессор</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="inputProcessor" name="cpu_name" placeholder="Введите модель процессора">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputCoreNumder" class="col-md-4 control-label">Число ядер</label>
                    <div class="col-md-2">
                        <select class="form-control" id="inputCoreNumder" name="cpu_cores">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>6</option>
                            <option>12</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputRAM" class="col-md-4 control-label">Размер ОЗУ</label>
                    <div class="col-md-2">
                        <input type="text" class="form-control" id="inputRAM" name="ram_gb">
                    </div>
                    <div class="col-md-2">
                        <h5>Гб</h5>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputVideoCard" class="col-md-4 control-label">Видеокарта</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="inputVideoCard" name="video_card" placeholder="Введите модель видеокарты">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputHD" class="col-md-4 control-label">Жесткий диск</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="inputHD" name="hdd_name" placeholder="Введите тип жесткого диска">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputHDVolume" class="col-md-4 control-label">Объем жесткого диска</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="inputHDVolume" name="hdd_capacity">
                    </div>
                    <div class="col-md-2">
                        <h5>Гб</h5>
                    </div>
                </div>
   
                 
            </div>

            
            <div class="col-md-4 col-md-offset-1">        
            
                <div class="form-group">
                    <h4>Выберите тип:</h4>
                    <div class="radio">
                        <label>
                        <input type="radio" name="type" id="optionsRadios1" value="desktop" checked>
                        Настольный компьютер
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                        <input type="radio" name="type" id="optionsRadios2" value="laptop">
                        Ноутбук
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                        <input type="radio" name="type" id="optionsRadios3" value="aio">
                        Моноблок
                        </label>
                    </div>
                </div>
                
                <button class="btn btn-default" data-toggle="modal" data-target="#PhotoInputModal">Добавить фотографии</button>


                <!-- Modal -->
                <div class="modal fade" id="PhotoInputModal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="ModalLabel">Загрузка фотографий</h4>
                      </div>
                      <div class="modal-body">
                        <div class="form-group">
                            <input type="file" multiple>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-primary">Сохранить</button>
                      </div>
                    </div>
                  </div>
                </div>
            
            </div>

        </div>
         
         
         <div class="row">
            <div class="col-md-12">
                 <hr/>
                <div class="form-group">
                    <div class="col-md-4">            
                        <button type="submit" class="btn btn-default btn-block">Добавить товар</button>
                    </div>
               </div>             
            </div>   
        </div>
            </form>


        </div>
    </div>
</#macro>
<@display "Добавить товар"></@display>