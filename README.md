[![Build Status](https://app.travis-ci.com/ainz713/job4j_urlshortcut.svg?branch=master)](https://app.travis-ci.com/ainz713/job4j_urlshortcut)
# job4j_urlshortcut

Сервис замены ссылок 

Описание: Сервис заменяет пользовательские ссылки на уникальные ассоциированные ключи и обеспечивает удобный и быстрый доступ к ним. 
Сначала пользователь регистрирует сайт и получает логин и пароль, затем отправляет ссылки для конвертации. Количество переходов по каждому
ключу подсчитывается и отображается в виде статистики.

Регистрация. Чтобы зарегистрировать сайт в систему нужно отправить запрос:
![ScreenShot](images/1.png)
Авторизация. Для получения token'а пользователь вводит полученный логин/пароль:
![ScreenShot](images/2.png)
Регистрация URL и конвертация. Поле того, как пользователь зарегистрировал свой сайт, он может отправлять в систему ссылки и получать ассоциированный ключ:
![ScreenShot](images/3.png)
Переадресация. При отправке запроса с заданным ключом, система возвращает ассоциированную ссылку:
![ScreenShot](images/4.png)
Статистика. Система подсчитывает количество переходов по каждому ключу
![ScreenShot](images/5.png)