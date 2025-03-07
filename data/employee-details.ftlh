<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль сотрудника - ${employeeDetails.name}</title>
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

    <div class="container">
        <h1>Профиль сотрудника</h1>

        <div class="employee-details">
            <div class="employee-info">
                <h2>${employeeDetails.name}</h2>
                <p><strong>Email:</strong> ${employeeDetails.email}</p>
                <p><strong>ID:</strong> ${employeeDetails.id}</p>
                <p><strong>Книг на руках:</strong> ${employeeDetails.currentBooks?size}/2</p>
                <p><strong>Всего книг в истории:</strong> ${employeeDetails.borrowHistory?size}</p>
            </div>

            <div class="books-section">
                <h2>Текущие книги</h2>
                <#if employeeDetails.currentBooks?has_content>
                    <div class="books-grid">
                        <#list employeeDetails.currentBooks as book>
                            <div class="book-card">
                                <img src="/images/book${book.id}.jpg" alt="${book.title}" class="book-image"
                                     onerror="this.onerror=null; this.src='/images/book${book.id}.jpeg';">
                                <div class="book-info">
                                    <h3 class="book-title"><a href="/book/${book.id}">${book.title}</a></h3>
                                    <p class="book-author">${book.author}</p>
                                    <span class="book-status status-${book.status}">
                                        ${book.status?capitalize}
                                    </span>
                                </div>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <p>У сотрудника нет книг на руках</p>
                </#if>
            </div>

            <div class="books-section">
                <h2>История книг</h2>
                <#if employeeDetails.borrowHistory?has_content>
                    <div class="books-grid">
                        <#list employeeDetails.borrowHistory as book>
                            <div class="book-card">
                                <img src="/images/book${book.id}.jpg" alt="${book.title}" class="book-image"
                                     onerror="this.onerror=null; this.src='/images/book${book.id}.jpeg';">
                                <div class="book-info">
                                    <h3 class="book-title"><a href="/book/${book.id}">${book.title}</a></h3>
                                    <p class="book-author">${book.author}</p>
                                </div>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <p>История пуста</p>
                </#if>
            </div>
        </div>

        <a href="/employees" class="button">Вернуться к списку сотрудников</a>
    </div>
</body>
</html>