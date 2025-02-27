<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в систему</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <nav class="navigation">
        <div class="container">
            <div class="nav-links">
                <a href="/books">Книги</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h1>Вход в систему</h1>
            <form action="/login" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required autofocus
                           placeholder="Введите ваш email">
                </div>
                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input type="password" id="password" name="password" required
                           placeholder="Введите ваш пароль">
                </div>
                <button type="submit" class="button">Войти</button>
            </form>
        </div>
    </div>
    <div class="form-footer">
        <p>Нет аккаунта? <a href="/registration">Зарегистрироваться</a></p>
    </div>
</body>
</html>