FROM node:20.15.0-alpine as Build

WORKDIR /app

COPY package*.json ./

RUN npm install

RUN npm install -g @angular/cli

COPY . .

RUN ng build

CMD ["ng", "serve", "--host", "0.0.0.0"]
