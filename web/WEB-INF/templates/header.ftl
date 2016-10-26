<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Название компании</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <#if current_user??>
                <li class="dropdown">
                    <a  href="#" class="dropdown-toggle" data-toggle="dropdown">Личный кабинет <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/user">Профиль</a></li>
                        <li><a href="#">Мои товары</a></li>
                        <li><a href="/user/chats">Сообщения <span class="badge">${unread_count}</span></a></li>
                        <li><a href="#">История</a></li>
                    </ul>
                </li>
                </#if>
                <li><a href="#">Товары</a></li>
                <li><a href="#">Рейтинг</a></li>
            </ul>
                <ul class="nav navbar-nav navbar-right">
                <#if current_user??>
                    <li><a href="/logout">Выход</a></li>
                <#else>
                    <li><a href="/login">Вход</a></li>
                    <li><a href="/register">Регистрация</a></li>
                </#if>
                </ul>
        </div>
    </div>
</nav>