FROM node:22-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install --registry https://registry.npmmirror.com
COPY . .
ENV NEXT_PUBLIC_API_URL=http://localhost:8080
RUN npm run build

FROM node:22-alpine
WORKDIR /app
ENV NODE_ENV=production
ENV PORT=3000
ENV HOSTNAME=0.0.0.0
COPY --from=build /app/public ./public
COPY --from=build /app/.next/standalone ./
COPY --from=build /app/.next/static ./.next/static
EXPOSE 3000
CMD ["node", "server.js"]
