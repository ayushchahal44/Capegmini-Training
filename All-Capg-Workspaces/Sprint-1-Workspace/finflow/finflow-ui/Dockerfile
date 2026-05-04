# Single stage: Serve with Nginx
FROM nginx:alpine
# Copy the pre-built dist folder from the host
COPY dist/finflow-ui/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
