<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
    <link rel="stylesheet" href="css/forms.css">
</head>
<body>
    <main>
        <form action="/login" method="post">
            <fieldset>
                <div class="legend">
                    <p>Вход в систему</p>
                </div>
                <div class="form-element">
                    <input type="email" name="email" placeholder="ваш адрес электронной почты" required autofocus>
                </div>
                <div class="form-element">
                    <input type="password" name="password" placeholder="ваш пароль" required>
                </div>
                <div class="form-element">
                    <button class="register-button" type="submit">ВОЙТИ</button>
                </div>
            </fieldset>
        </form>
    </main>
</body>
</html>