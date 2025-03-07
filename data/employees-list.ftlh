<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список сотрудников - Библиотека</title>
    <link rel="stylesheet" href="/css/styles.css">
    <style>
        .employees-container {
            max-width: 1100px;
            margin: 0 auto;
        }

        .page-title {
            font-size: 36px;
            text-align: center;
            margin: 30px 0;
            color: #333;
        }

        .employees-filter {
            display: flex;
            justify-content: center;
            margin: 20px 0 40px;
        }

        .filter-button {
            background-color: #f5f5f5;
            border: 2px solid transparent;
            border-radius: 30px;
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            color: #555;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .filter-button:hover {
            background-color: #ffe066;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .filter-button.active {
            background-color: #ffca3a;
            color: #333;
            border-color: #ff8c38;
            box-shadow: 0 4px 15px rgba(255, 140, 56, 0.3);
        }

        .employees-grid {
            display: grid;
            grid-template-columns: 1fr;
            gap: 30px;
            margin: 30px 0;
        }

        @media (min-width: 768px) {
            .employees-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (min-width: 1200px) {
            .employees-grid {
                grid-template-columns: repeat(3, 1fr);
            }
        }

        .employee-card {
            background-color: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .employee-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
        }

        .employee-header {
            padding: 25px 25px 15px;
            border-bottom: 1px solid #eee;
            background-color: #f9f9f9;
        }

        .employee-name {
            font-size: 22px;
            margin: 0 0 10px;
            color: #333;
        }

        .employee-email {
            color: #888;
            font-style: italic;
            font-size: 14px;
        }

        .employee-content {
            padding: 20px 25px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .book-counter {
            background-color: #f0f0f0;
            border-radius: 30px;
            padding: 10px 15px;
            display: inline-block;
            margin-bottom: 20px;
            font-weight: bold;
            color: #555;
            text-align: center;
        }

        .current-books {
            margin-bottom: 15px;
            flex-grow: 1;
        }

        .current-books-title {
            font-size: 18px;
            margin-bottom: 15px;
            color: #444;
            border-bottom: 2px solid #ffca3a;
            padding-bottom: 8px;
            display: inline-block;
        }

        .books-list {
            margin-left: 10px;
        }

        .book-item {
            display: flex;
            align-items: center;
            margin-bottom: 12px;
            padding-bottom: 12px;
            border-bottom: 1px solid #eee;
        }

        .book-item:last-child {
            margin-bottom: 0;
            padding-bottom: 0;
            border-bottom: none;
        }

        .book-thumbnail {
            width: 60px;
            min-width: 60px;
            height: 85px;
            border-radius: 5px;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.15);
            overflow: hidden;
            margin-right: 15px;
            background-color: #f0f0f0;
        }

        .book-thumbnail img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .book-info {
            flex-grow: 1;
        }

        .book-title {
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 15px;
        }

        .book-title a {
            color: #333;
            text-decoration: none;
            transition: color 0.3s;
        }

        .book-title a:hover {
            color: #ff8c38;
        }

        .book-author {
            color: #ff8c38;
            font-size: 13px;
        }

        .no-books {
            color: #888;
            font-style: italic;
            text-align: center;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 10px;
        }

        .employee-footer {
            padding: 15px 25px;
            border-top: 1px solid #eee;
            display: flex;
            justify-content: center;
            background-color: #f9f9f9;
        }

        .details-button {
            display: inline-block;
            padding: 10px 25px;
            background-color: #ffca3a;
            color: #333;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.3s;
            text-align: center;
        }

        .details-button:hover {
            background-color: #ffd966;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .notification {
            width: 90%;
            max-width: 800px;
            margin: 20px auto;
            padding: 15px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .notification-success {
            background-color: rgba(46, 204, 113, 0.2);
            border: 1px solid #2ecc71;
            color: #27ae60;
        }

        .notification-error {
            background-color: rgba(231, 76, 60, 0.2);
            border: 1px solid #e74c3c;
            color: #c0392b;
        }
    </style>
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

    <div class="container employees-container">
        <h1 class="page-title">Список сотрудников</h1>

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

        <div class="employees-filter">
            <a href="/employees" class="filter-button active">Все сотрудники</a>
        </div>

        <div class="employees-grid">
            <#list allEmployees as emp>
                <div class="employee-card">
                    <div class="employee-header">
                        <h3 class="employee-name">${emp.name}</h3>
                        <span class="employee-email">${emp.email}</span>
                    </div>

                    <div class="employee-content">
                        <div class="book-counter">
                            Книг на руках: ${emp.currentBooks?size}/2
                        </div>

                        <div class="current-books">
                            <#if emp.currentBooks?has_content>
                                <h4 class="current-books-title">Текущие книги:</h4>
                                <div class="books-list">
                                    <#list emp.currentBooks as book>
                                        <div class="book-item">
                                            <div class="book-thumbnail">
                                                <img src="/images/book${book.id}.jpg" alt="${book.title}"
                                                     onerror="this.onerror=null; this.src='/images/book${book.id}.jpeg';">
                                            </div>
                                            <div class="book-info">
                                                <div class="book-title">
                                                    <a href="/book/${book.id}">${book.title}</a>
                                                </div>
                                                <div class="book-author">${book.author}</div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            <#else>
                                <div class="no-books">Нет книг на руках</div>
                            </#if>
                        </div>
                    </div>

                    <div class="employee-footer">
                        <a href="/employee/${emp.id}" class="details-button">Подробнее</a>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</body>
</html>