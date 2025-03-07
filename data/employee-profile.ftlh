<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль сотрудника - ${employee.name}</title>
    <link rel="stylesheet" href="/css/styles.css">
    <style>
        .logout-button {
            display: block;
            margin: 30px auto;
            padding: 10px 20px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 4px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
            width: 200px;
        }

        .logout-button:hover {
            background-color: #d32f2f;
        }

        .footer {
            margin-top: 40px;
            text-align: center;
            padding: 20px;
            border-top: 1px solid #eee;
        }
    </style>
</head>
<body>
    <nav class="navigation">
        <div class="container">
            <div class="nav-links">
                <a href="/profile">Мой профиль</a>
                <a href="/books">Книги</a>
                <a href="/employees">Сотрудники</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="notification success" id="notification"></div>

        <h1>Профиль сотрудника</h1>

        <h2>${employee.name}</h2>
        <p><strong>Email:</strong> ${employee.email}</p>
        <p><strong>Книг на руках:</strong> ${employee.currentBooksCount}/2</p>

        <h2>Текущие книги</h2>

        <#if employee.currentBooks?has_content>
            <div class="books-grid">
                <#list employee.currentBooks as book>
                    <div class="book-card">
                        <img src="${book.image}" alt="${book.title}" class="book-image" onerror="this.src='/images/book${book.id}.jpg';">
                        <div class="book-info">
                            <h3 class="book-title"><a href="/book/${book.id}">${book.title}</a></h3>
                            <p class="book-author">${book.author}</p>
                            <a href="/return-book/${book.id}" class="button">Вернуть</a>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <p style="text-align: center;">У вас нет книг на руках</p>
        </#if>

        <h2>История книг</h2>

        <#if employee.borrowHistory?has_content>
            <div class="books-grid">
                <#list employee.borrowHistory as book>
                    <div class="book-card">
                        <img src="${book.image}" alt="${book.title}" class="book-image" onerror="this.src='/images/book${book.id}.jpg';">
                        <div class="book-info">
                            <h3 class="book-title"><a href="/book/${book.id}">${book.title}</a></h3>
                            <p class="book-author">${book.author}</p>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <p style="text-align: center;">История пуста</p>
        </#if>

        <div class="footer">
            <a href="/logout" class="logout-button">Выйти из системы</a>
        </div>
    </div>

    <script>
        function showNotification(message) {
            const notification = document.getElementById('notification');
            notification.textContent = message;
            notification.style.display = 'block';
            setTimeout(() => {
                notification.style.display = 'none';
            }, 3000);
        }
    </script>
</body>
</html>