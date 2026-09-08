FROM nginx:alpine

# Static landing config (replaces the default nginx server).
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Pre-built Vite bundle (contents of landing/dist).
COPY site/ /usr/share/nginx/html/
