<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Тестирование Cookie</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <nav class="navigation">
        <div class="container">
            <div class="nav-links">
                <#if employee??>
                    <a href="/profile">Мой профиль</a>
                <#else>
                    <a href="/login">Войти</a>
                </#if>
                <a href="/books">Книги</a>
                <a href="/employees">Сотрудники</a>
            </div>
        </div>
    </nav>

    <div class="container cookie-container">
        <h1>Тестирование Cookie</h1>

        <div class="cookie-counter">
            Количество посещений: ${times}
        </div>

        <div class="cookie-info">
            <h2>Текущие Cookie:</h2>
            <table class="cookie-table">
                <thead>
                    <tr>
                        <th>Имя</th>
                        <th>Значение</th>
                    </tr>
                </thead>
                <tbody>
                    <#list cookies?keys as cookieName>
                    <tr>
                        <td>${cookieName}</td>
                        <td>${cookies[cookieName]}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>

        <p><a href="/" class="button">Вернуться на главную</a></p>
    </div>
</body>
</html>