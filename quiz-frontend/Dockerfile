# stage1 as builder
FROM node:lts-alpine as builder

# Workdir to frontend
WORKDIR ./elok-quiz-frontend

# copy the package.json to install dependencies
COPY package.json package-lock.json ./

# Install the dependencies and make the folder
RUN npm install && mkdir ./app-ui && mv ./node_modules ./app-ui

WORKDIR ./app-ui

COPY . .

# Build the project and copy the files
RUN npm run ng build -- --prod --aot=false --build-optimizer=false

# stage2 as running
FROM nginx:alpine

#!/bin/sh

COPY nginx.conf /etc/nginx/nginx.conf

## Remove default nginx index page
RUN rm -rf /usr/share/nginx/html/*

# Copy from the stage 1
COPY --from=builder /elok-quiz-frontend/app-ui/dist /usr/share/nginx/html

# Permission on html folder
RUN chmod 755 /usr/share/nginx/html

EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]