<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Тестирование Cookie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #f5f5f5;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        .cookie-info {
            margin-top: 20px;
            background: #fff;
            padding: 15px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .counter {
            font-size: 18px;
            font-weight: bold;
            margin: 15px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Тестирование Cookie</h1>

        <div class="counter">
            Количество посещений: ${times}
        </div>

        <div class="cookie-info">
            <h2>Текущие Cookie:</h2>
            <table>
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

        <p><a href="/">Вернуться на главную</a></p>
    </div>
</body>
</html>