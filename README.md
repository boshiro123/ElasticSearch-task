<<<<<<< HEAD
# Запуск

### Проверьте порты, чтобы они были свободны


1. Скачать репозиторий
   
Откройте через терминал папку где хотите чтобы располагался проект и вводим команду (git clone https://github.com/boshiro123/ElasticSearch-task.git)
Либо же скачиваем архивом

2. Запуск проекта

Для начала откроем проект через терминал в его корневой папке, затем запустим БД и эластик, в терминал прописываем команды по очереди

	1. make db(и после того как скачались все images ждём секунд 10 чтобы эластик запустился до конца и сервер не крашнулся)
 
	2. mvn clean package (чтобы создать jar файл)

    3. docker-compose build
 
	4. make up
 
Всё, проект запушем, в консоли увидите  http://localhost:4200/, перейдите и откроется страничка

Надеюсь это было полезно, буду благодарен за обратную связь.


	
P.S. чтобы всё отключить пропишим docker-compose down

