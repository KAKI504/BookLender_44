<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <nav class="navigation">
        <div class="container">
            <div class="nav-links">
                <a href="/login">Войти</a>
                <a href="/books">Книги</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h1>Регистрация пользователя</h1>

            <#if error??>
                <div class="notification notification-error">
                    ${error}
                </div>
            </#if>

            <#if success??>
                <div class="notification notification-success">
                    ${success}
                </div>
            </#if>

            <form action="/registration" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required
                           placeholder="Введите ваш email">
                </div>
                <div class="form-group">
                    <label for="name">Имя</label>
                    <input type="text" id="name" name="name" required
                           placeholder="Введите ваше имя">
                </div>
                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input type="password" id="password" name="password" required
                           placeholder="Введите ваш пароль">
                </div>
                <button type="submit" class="button">Зарегистрироваться</button>
            </form>

            <div class="form-footer">
                <p>Уже зарегистрированы? <a href="/login">Войти</a></p>
            </div>
        </div>
    </div>
</body>
</html>