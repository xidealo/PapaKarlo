FROM nginx:alpine

# Static landing config (replaces the default nginx server).
# Paths are relative to the PapaKarlo monorepo root (same pattern as amvera/Dockerfile).
COPY landingamvera/nginx.conf /etc/nginx/conf.d/default.conf

# Pre-built Vite bundle (contents of landing/dist).
COPY landingamvera/site/ /usr/share/nginx/html/
