ARG NODE_VERSION=16.14.0

FROM node:${NODE_VERSION}-alpine

COPY . .

RUN npm install

EXPOSE 3000

CMD ["npm","start"]