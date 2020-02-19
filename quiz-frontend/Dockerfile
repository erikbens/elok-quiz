FROM node:lts-alpine
RUN npm install -g http-server

WORKDIR ./elok-quiz-frontend
COPY package*.json ./

# Copy all files
COPY . .
# Copy only build if build before
#COPY /dist /dist
# Install dependencies
RUN npm install 
# Build
RUN npm run build --prod --aot=false --build-optimizer=false

EXPOSE 4200

#RUN npm run ng build --output-path=dist
CMD ["npm", "run", "start"]