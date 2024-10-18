install:
	sudo apt install docker-compose \
	&& sudo usermod -aG docker $$USER \
	&& sudo service docker restart

jar:
	mvn clean package

stop:
	docker-compose stop backend
	docker-compose stop frontend

up:
	docker-compose up -d backend
	docker-compose up frontend


db:
	docker-compose build
	docker-compose up -d postgres
	docker-compose up -d elasticsearch
	docker-compose up -d kibana


