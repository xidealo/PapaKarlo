FROM nginx:alpine

# Reverse-proxy + static config (replaces the default nginx server).
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Pre-built web bundle (contents of webApp/build/dist/js/productionExecutable).
COPY site/ /usr/share/nginx/html/
